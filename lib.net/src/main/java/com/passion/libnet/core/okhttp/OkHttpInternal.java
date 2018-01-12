package com.passion.libnet.core.okhttp;

import com.orhanobut.logger.Logger;
import com.passion.libnet.core.NetConfig;
import com.passion.libnet.core.NetWrapper;
import com.passion.libnet.core.RequestModel;
import com.passion.libnet.core.ResponseModel;
import com.passion.libnet.core.exception.ConnectNetException;
import com.passion.libnet.core.exception.ErrorBody;
import com.passion.libnet.core.exception.ErrorResponse;
import com.passion.libnet.core.exception.NetException;
import com.passion.libnet.core.exception.ServerNetException;
import com.passion.libnet.core.exception.TimeoutNetException;
import com.passion.libnet.core.imp.NetCallback;
import com.passion.libnet.core.mapper.JavaTypeToken;
import com.passion.libnet.core.mapper.JsonMapper;
import com.passion.libnet.core.request.RequestFactory;
import com.passion.libnet.core.response.HttpResult;
import com.passion.libnet.core.utils.SafeCheckUtil;
import com.passion.libutils.NetworkUtil;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by chaos
 * on 2018/1/10. 14:50
 * 文件描述：
 */
        /*
         * 1。请求之前的状态检查（网络状态，当前页面是否处于前台）
         * 2。封装底层的请求参数（）
         * 3。触发请求
         * 4。请求之后的失败或成功的分流
         */
public final class OkHttpInternal<T> {
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();
    private static final NetConfig NET_CONFIG = NetWrapper.getConfig();
    final RequestModel requestModel;
    final NetCallback<T> netCallback;

    public OkHttpInternal(RequestModel requestModel, NetCallback<T> netCallback) {
        requestModel = requestModel.newBuilder()
                .responseType(netCallback.getResponseType())
                .build();
        this.requestModel = requestModel;
        this.netCallback = netCallback;
    }

    /**
     * 异步
     */
    public void execute() {
        if (checked(requestModel, netCallback)) {
            final OkHttpClient httpClient = HTTP_CLIENT;
            final Request request = createRequest(requestModel);
            httpClient.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    this.dealFailure(e, null);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (call.isCanceled()) {
                        netCallback.onFailure(new ErrorBody());
                    } else if (response.isSuccessful()) {
                        dealResponse(response);
                    } else {
                        this.dealFailure(null, null);
                    }

                }

                private void dealResponse(Response response) throws IOException {
                    ResponseBody responseBody = response.body();
                    int responseCode = response.code();
                    if (responseCode == 200) {
                        String rStr = responseBody.string();
                        Type typeOuter = JavaTypeToken.getParameterizedType(HttpResult.class, netCallback.getResponseType());
                        HttpResult<T> httpResult = JsonMapper.fromJson(rStr, typeOuter);
                        if (!SafeCheckUtil.isNull(httpResult)) {
                            netCallback.onSuccess(httpResult.getData());
                            return;
                        }
                    }
                    netCallback.onFailure(new ErrorBody());

                }

                private void dealFailure(Throwable t, ResponseModel responseModel) {
                    Throwable error;
                    if (t instanceof ConnectException) {
                        error = new ConnectNetException("网络出错，可能的原因是：您的网络不通！", t);
                    } else if (t instanceof UnknownHostException) {
                        error = new ConnectNetException("网络出错，可能的原因是：您的网络不通！", t);
                    } else if (t instanceof InterruptedIOException) {
                        error = new TimeoutNetException("服务器访问超时！", t);
                    } else if (t instanceof MalformedURLException) {
                        error = new ServerNetException("Bad URL ", t);
                    } else {
                        if (responseModel != null) {
                            int statusCode = responseModel.httpStatusCode();
                            ErrorResponse networkResponse = new ErrorResponse(statusCode, responseModel.bytes());
                            if (statusCode != 401 && statusCode != 403) {
                                error = new ServerNetException("服务器访问出错，响应码" + statusCode, t, networkResponse);
                            } else {
                                error = new NetException("服务器访问受限！", t, networkResponse);
                            }
                        } else {
                            error = new NetException(t.getMessage(), t);
                        }

                    }
                    error.printStackTrace();
                    netCallback.onFailure(new ErrorBody(error));
                }

            });

        }

    }

    private boolean checked(RequestModel requestModel, NetCallback<T> netCallback) {
        if (!NetworkUtil.isConnected(NET_CONFIG.getAppContext())) {
            Logger.e("当前没有网络可以访问");
            NetException bizException = new NetException("当前没有网络可以访问");
            netCallback.onFailure(new ErrorBody(bizException));
            return false;
        } else if (requestModel == null || requestModel.url() == null) {
            Logger.e("requestModel 不能为null 并且其请求方法不能为 null");
            NetException bizException = new NetException("requestModel 不能为null 并且其请求方法不能为 null");
            netCallback.onFailure(new ErrorBody(bizException));
            return false;
        } else if (NET_CONFIG.getApiService().get(requestModel.getPathUrl()) == null) {
            Logger.e("方法 :" + requestModel.url() + "未注册,请联系软件供应商");
            NetException bizException = new NetException("方法 :" + requestModel.url() + "未注册,请联系软件供应商");
            netCallback.onFailure(new ErrorBody(bizException));
            return false;
        } else {
            return true;
        }
    }

    private Request createRequest(RequestModel requestModel) {
        Request request = RequestFactory.create(requestModel);
        return request;
    }



}
