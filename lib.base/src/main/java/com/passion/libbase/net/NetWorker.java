package com.passion.libbase.net;


import com.passion.libnet.core.FileCallback;
import com.passion.libnet.core.NetConfig;
import com.passion.libnet.core.NetResponse;
import com.passion.libnet.core.Network;
import com.passion.libnet.core.RequestModel;
import com.passion.libnet.core.ResponseModel;
import com.passion.libnet.core.convert.Converter;
import com.passion.libnet.core.cookie.MemoryCookieCache;
import com.passion.libnet.core.cookie.NetCookieJar;
import com.passion.libnet.core.exception.ConnectNetException;
import com.passion.libnet.core.exception.NetException;
import com.passion.libnet.core.exception.ServerNetException;
import com.passion.libnet.core.exception.TimeoutNetException;
import com.passion.libnet.core.factory.DefaultNetFactory;
import com.passion.libnet.core.factory.NetFactory;
import com.passion.libnet.core.imp.Callback;
import com.passion.libnet.core.request.RequestInterceptor;
import com.passion.libnet.core.utils.DefaultNetCookieJar;
import com.passion.libnet.core.utils.DefaultSignInterceptor;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created by chaos
 * on 2017/11/15. 15:48
 * 文件描述：
 */

public class NetWorker {

    private static NetWorker sInstance;
    private static NetConfig mNetworkConfig;
    private final boolean isDefaultFactory;
    private NetFactory mNetFactory;

    public NetWorker() {
        if (mNetworkConfig == null) {
            throw new IllegalStateException("NetService未初始化配置，请先调用NetService.init()进行初始化或者使用含参数的构造方法。");
        } else if (mNetworkConfig.enableDefaultSign && mNetworkConfig.appSecret == null) {
            throw new IllegalStateException("使用默认签名方式[enableDefaultSign==true]，请设置NetworkConfig中的AppSecret或者设置[enableDefaultSign=false]。");
        } else {

            Converter jsonConverter = mNetworkConfig.jsonConverter;
            int defaultSize = mNetworkConfig.enableDefaultSign ? 1 : 0;
            int interceptorSize = mNetworkConfig.requestInterceptors == null ? defaultSize : mNetworkConfig.requestInterceptors.length + defaultSize;
            RequestInterceptor[] interceptors = new RequestInterceptor[interceptorSize];
            if (mNetworkConfig.requestInterceptors != null && mNetworkConfig.requestInterceptors.length > 0) {
                System.arraycopy(mNetworkConfig.requestInterceptors, 0, interceptors, 0, mNetworkConfig.requestInterceptors.length);
            }

            if (defaultSize > 0) {
                interceptors[interceptorSize - 1] = new DefaultSignInterceptor(mNetworkConfig.appSecret);
            }

            NetCookieJar cookieJar = mNetworkConfig.cookieJar;
            if (cookieJar == null && mNetworkConfig.enableCookie) {
                cookieJar = new DefaultNetCookieJar(new MemoryCookieCache());
            }

            this.mNetFactory = new DefaultNetFactory(mNetworkConfig.debugMode, jsonConverter, null, (NetCookieJar) cookieJar, mNetworkConfig.trustAllCerts, interceptors);
            this.isDefaultFactory = true;
        }
    }

    public NetWorker(NetFactory NetFactory) {
        this.mNetFactory = NetFactory;
        this.isDefaultFactory = false;
    }

    public static void init(NetConfig netConfig) {
        if (netConfig == null) {
            throw new IllegalArgumentException("NetConfig == null");
        } else {
            mNetworkConfig = netConfig;
        }
    }

    public static NetWorker getDefault() {
        if (sInstance == null) {
            synchronized (NetWorker.class) {
                if (sInstance == null) {
                    sInstance = new NetWorker();
                }
            }
        }
        return sInstance;
    }


    public NetFactory getNetworkFactory() {
        return this.mNetFactory;
    }

    public void request(RequestModel requestModel, Callback callback) {
        Network network = this.getNetwork(requestModel.url());
        network.execute(requestModel, callback);
    }

    public <T> ResponseModel<T> request(RequestModel requestModel) throws IOException {
        Network network = this.getNetwork(requestModel.url());
        return network.execute(requestModel);
    }

    public void get(String url, Callback callback) {
        this.get(url, null, callback);
    }

    public void get(String url, Map<String, String> params, Callback callback) {
        RequestModel requestModel = RequestModel.get(url).addUrlParameters(params).build();
        this.getNetwork(url).execute(requestModel, callback);
    }

    public <T> T get(String url, Map<String, String> params, Type type) throws NetException {
        RequestModel requestModel = RequestModel.get(url).addUrlParameters(params).responseType(type).build();
        return this.execute(requestModel);
    }

    public void post(String url, Map<String, Object> params, Callback callback) {
        RequestModel requestModel = RequestModel.post(url).addParameters(params).build();
        this.getNetwork(url).execute(requestModel, callback);
    }

    public <T> T post(String url, Map<String, Object> params, Type type) throws NetException {
        RequestModel requestModel = RequestModel.post(url).addParameters(params).responseType(type).build();
        return this.execute(requestModel);
    }

    public <T> T post(String url, String content, Type type) throws NetException {
        RequestModel.PostBuilder builder = RequestModel.post(url);
        builder.postContent(content, "text/plain");
        builder.responseType(type);
        return this.execute(builder.build());
    }

    public void download(String url, File target) throws NetException {
        RequestModel requestModel = RequestModel.get(url).build();
        this.execute(requestModel, target);
    }

    public void download(RequestModel requestModel, File target) throws NetException {
        this.execute(requestModel, target);
    }

    public void download(String url, File target, FileCallback callback) {
        this.getNetwork(url).download(url, target, callback);
    }

    private Network getNetwork(String url) {
        Network network = this.mNetFactory.getNetwork(url);
        if (network == null) {
            throw new IllegalArgumentException("network == null");
        } else if (this.isDefaultFactory) {
            return network;
        } else {
            Network.Builder builder = null;

            if (mNetworkConfig != null && network.converter() == null) {
                builder = network.newBuilder();

                if (mNetworkConfig.jsonConverter != null) {
                    builder.converter(mNetworkConfig.jsonConverter);
                }
            }

            return builder != null ? builder.build() : network;
        }
    }

    private <T> T execute(RequestModel request) throws NetException {
        return this.execute(request, null);
    }

    private <T> T execute(RequestModel request, File target) throws NetException {
        Network network = this.getNetwork(request.url());
        if (network == null) {
            throw new NullPointerException("NetFactory receives a null network.");
        } else {
            ResponseModel response = null;

            try {
                if (target != null) {
                    response = network.download(request, target);
                } else {
                    response = network.execute(request);
                }

                if (response == null) {
                    throw new NetException("response == null");
                } else if (!response.isSuccessful()) {
                    throw new IOException();
                } else {
                    return (T) response.data();
                }
            } catch (UnknownHostException | ConnectException var8) {
                throw new ConnectNetException("网络出错，可能的原因是：您的网络不通！", var8);
            } catch (InterruptedIOException var9) {
                throw new TimeoutNetException("服务器访问超时！", var9);
            } catch (MalformedURLException var10) {
                throw new RuntimeException("Bad URL " + request.url(), var10);
            } catch (IOException var11) {
                if (response != null) {
                    int statusCode = response.httpStatusCode();
                    var11.printStackTrace();
                    NetResponse networkResponse = new NetResponse(statusCode, response.bytes());
                    if (statusCode != 401 && statusCode != 403) {
                        throw new ServerNetException("服务器访问出错，响应码" + statusCode, var11, networkResponse);
                    } else {
                        throw new NetException("服务器访问受限！", var11, networkResponse);
                    }
                } else {
                    throw new NetException(var11.getMessage(), var11);
                }
            }
        }
    }
}

