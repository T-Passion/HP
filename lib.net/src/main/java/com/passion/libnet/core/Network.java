package com.passion.libnet.core;

import com.passion.libnet.core.convert.Converter;
import com.passion.libnet.core.cookie.NetCookieJar;
import com.passion.libnet.core.imp.Callback;
import com.passion.libnet.core.imp.HttpCall;
import com.passion.libnet.core.request.RequestInterceptor;
import com.passion.libnet.core.request.RequestParser;
import com.passion.libnet.core.utils.Platform;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Dns;

/**
 * Created by chaos
 * on 2017/11/16. 14:24
 * 文件描述：
 */

public class Network {
    private static final long DEFAULT_CONNECT_TIMEOUT = 5000L;
    private static Network instance;
    private final Converter converter;
    private final Dns dns;
    private final long connectTimeoutMillis;
    private final long readTimeoutMillis;
    private final long writeTimeoutMillis;
    private final boolean postBody;
    private final boolean debugMode;
    private final boolean trustAllCerts;
    private List<RequestInterceptor> requestInterceptors;
    private NetCookieJar cookieJar;

    public Network(Network.Builder builder) {
        this.converter = builder.converter;
        this.dns = builder.dns;
        this.connectTimeoutMillis = builder.connectTimeoutMillis <= 0L ? 5000L : builder.connectTimeoutMillis;
        this.readTimeoutMillis = builder.readTimeoutMillis;
        this.writeTimeoutMillis = builder.writeTimeoutMillis;
        this.postBody = builder.postBody;
        this.debugMode = builder.debugMode;
        this.trustAllCerts = builder.trustAllCerts;
        this.requestInterceptors = builder.requestInterceptors;
        this.cookieJar = builder.cookieJar;
    }

    public static Network getInstance() {
        if (instance == null) {
            Class var0 = Network.class;
            synchronized (Network.class) {
                if (instance == null) {
                    instance = (new Network.Builder()).build();
                }
            }
        }

        return instance;
    }

    public <T> ResponseModel<T> get(String url) throws IOException {
        return this.get(url, (Map) null, (Type) String.class);
    }

    public <T> ResponseModel<T> get(String url, Map<String, String> queryParams) throws IOException {
        return this.get(url, queryParams, (Type) String.class);
    }

    public <T> ResponseModel<T> get(String url, Map<String, String> queryParams, Type type) throws IOException {
        RequestModel requestModel = this.buildGetRequest(url, queryParams, type);
        HttpCall<T> httpCall = new OKHttpCall(RequestParser.create(requestModel, this.requestInterceptors), this);
        return httpCall.execute();
    }

    public <T> void get(String url, Map<String, String> queryParams, Callback<T> callback) {
        RequestModel requestModel = this.buildGetRequest(url, queryParams, callback.getResponseType());
        HttpCall<T> httpCall = new OKHttpCall(RequestParser.create(requestModel, this.requestInterceptors), this);
        HttpCall<T> executeCall = new HttpExecutorCallback(Platform.getInstance().getCallbackExecutor(), httpCall);
        executeCall.execute(callback);
    }

    public <T> ResponseModel<T> post(String url, Map<String, Object> formFields) throws IOException {
        return this.post(url, formFields, (Type) String.class);
    }

    public <T> ResponseModel<T> post(String url, Map<String, Object> formFields, Type type) throws IOException {
        RequestModel requestModel = this.buildPostRequest(url, formFields, type, (File) null, (String) null, (String) null);
        HttpCall<T> httpCall = new OKHttpCall(RequestParser.create(requestModel, this.requestInterceptors), this);
        return httpCall.execute();
    }

    public <T> ResponseModel<T> post(String url, File file, String fileKey, String fileName) throws IOException {
        RequestModel requestModel = this.buildPostRequest(url, (Map) null, (Type) null, file, fileKey, fileName);
        HttpCall<T> httpCall = new OKHttpCall(RequestParser.create(requestModel, this.requestInterceptors), this);
        return httpCall.execute();
    }

    public <T> void post(String url, Map<String, Object> formFields, Callback<T> callback) {
        RequestModel requestModel = this.buildPostRequest(url, formFields, callback.getResponseType(), (File) null, (String) null, (String) null);
        HttpCall<T> httpCall = new OKHttpCall(RequestParser.create(requestModel, this.requestInterceptors), this);
        HttpCall<T> executeCall = new HttpExecutorCallback(Platform.getInstance().getCallbackExecutor(), httpCall);
        executeCall.execute(callback);
    }

    public <T> ResponseModel<T> execute(RequestModel requestModel) throws IOException {
        RequestModel executeRequestModel = this.buildRequestWithCandidate(requestModel);
        HttpCall<T> httpCall = new OKHttpCall(RequestParser.create(executeRequestModel, this.requestInterceptors), this);
        return httpCall.execute();
    }

    public <T> void execute(RequestModel requestModel, Callback<T> callback) {
        RequestModel executeRequestModel = this.buildRequestWithCandidate(requestModel, callback.getResponseType());
        HttpCall<T> httpCall = new OKHttpCall(RequestParser.create(executeRequestModel, this.requestInterceptors), this);
        HttpCall<T> executeCall = new HttpExecutorCallback(Platform.getInstance().getCallbackExecutor(), httpCall);
        executeCall.execute(callback);
    }

    public ResponseModel download(String url, File target) throws IOException {
        RequestModel executeRequestModel = this.buildGetRequest(url, (Map) null, (Type) null);
        HttpCall httpCall = new OKHttpCall(RequestParser.create(executeRequestModel, this.requestInterceptors), this);
        return httpCall.download(target);
    }

    public ResponseModel download(RequestModel requestModel, File target) throws IOException {
        RequestModel executeRequestModel = this.buildRequestWithCandidate(requestModel);
        HttpCall httpCall = new OKHttpCall(RequestParser.create(executeRequestModel, this.requestInterceptors), this);
        return httpCall.download(target);
    }

    public void download(String url, File target, FileCallback callback) {
        RequestModel executeRequestModel = this.buildGetRequest(url, (Map) null, (Type) null);
        HttpCall<File> httpCall = new OKHttpCall(RequestParser.create(executeRequestModel, this.requestInterceptors), this);
        HttpCall<File> executeCall = new HttpExecutorCallback(Platform.getInstance().getCallbackExecutor(), httpCall);
        executeCall.download(target, callback);
    }

    private RequestModel buildGetRequest(String url, Map<String, String> queryParamsMap, Type type) {
        RequestModel.GetBuilder requestBuilder = (RequestModel.GetBuilder) RequestModel.get(url).addUrlParameters(queryParamsMap).responseType(type);
        this.buildRequestWithCandidate(requestBuilder);
        return requestBuilder.build();
    }

    private RequestModel buildPostRequest(String url, Map<String, Object> fieldsMap, Type type, File file, String fileKey, String fileName) {
        RequestModel.PostBuilder requestBuilder = (RequestModel.PostBuilder) RequestModel.post(url).addParameters(fieldsMap).responseType(type);
        if (file != null) {
            if (fileKey == null) {
                throw new NullPointerException("fileKey == null");
            }

            requestBuilder.addFormDataPart(fileKey, file, fileName);
        }

        this.buildRequestWithCandidate((RequestModel.Builder) requestBuilder);
        return requestBuilder.build();
    }

    private void buildRequestWithCandidate(RequestModel.Builder requestBuilder) {
        requestBuilder.connectTimeout(this.connectTimeoutMillis);
        if (this.readTimeoutMillis > 0L) {
            requestBuilder.readTimeout(this.readTimeoutMillis);
        }

        if (this.writeTimeoutMillis > 0L) {
            requestBuilder.writeTimeout(this.writeTimeoutMillis);
        }

        if (this.converter != null) {
            requestBuilder.converter(this.converter);
        }

        requestBuilder.postContentByBody(Boolean.valueOf(this.postBody));
    }

    private RequestModel buildRequestWithCandidate(RequestModel rawRequestModel) {
        return this.buildRequestWithCandidate(rawRequestModel, (Type) null);
    }

    private RequestModel buildRequestWithCandidate(RequestModel rawRequestModel, Type responseType) {
        RequestModel.Builder requestBuilder = rawRequestModel.newBuilder();
        if (rawRequestModel.connectTimeoutMillis <= 0L) {
            requestBuilder.connectTimeout(this.connectTimeoutMillis);
        }

        if (this.readTimeoutMillis > 0L && rawRequestModel.readTimeoutMillis <= 0L) {
            requestBuilder.readTimeout(this.readTimeoutMillis);
        }

        if (this.writeTimeoutMillis > 0L && rawRequestModel.writeTimeoutMillis <= 0L) {
            requestBuilder.writeTimeout(this.writeTimeoutMillis);
        }

        if (this.converter != null && rawRequestModel.converter == null) {
            requestBuilder.converter(this.converter);
        }

        if (rawRequestModel.postBody == null) {
            requestBuilder.postContentByBody(Boolean.valueOf(this.postBody));
        }

        if (responseType != null) {
            requestBuilder.responseType(responseType);
        }

        return requestBuilder.build();
    }

    public Converter converter() {
        return this.converter;
    }

    public Dns dns() {
        return this.dns;
    }

    NetCookieJar getCookieJar() {
        return this.cookieJar;
    }

    boolean isDebugMode() {
        return this.debugMode;
    }

    boolean trustAllCerts() {
        return this.trustAllCerts;
    }

    public void clearCookie() {
        if (this.cookieJar != null) {
            this.cookieJar.clear();
        }

    }

    public static void cancel(Object tag) {
        HttpCall httpCall = new OKHttpCall(null, null);
        httpCall.cancel(tag);
    }

    public Network.Builder newBuilder() {
        return new Network.Builder(this);
    }

    public static final class Builder {
        private Converter converter;
        private Dns dns;
        private long connectTimeoutMillis;
        private long readTimeoutMillis;
        private long writeTimeoutMillis;
        private boolean postBody;
        private boolean debugMode;
        private List<RequestInterceptor> requestInterceptors;
        private NetCookieJar cookieJar;
        private boolean trustAllCerts;

        public Builder() {
        }

        public Builder(Network network) {
            this.converter = network.converter;
            this.dns = network.dns;
            this.readTimeoutMillis = network.readTimeoutMillis;
            this.writeTimeoutMillis = network.writeTimeoutMillis;
            this.postBody = network.postBody;
            this.trustAllCerts = network.trustAllCerts;
            this.requestInterceptors = network.requestInterceptors;
            this.cookieJar = network.cookieJar;
        }

        public Network.Builder converter(Converter converter) {
            this.converter = converter;
            return this;
        }

        public Network.Builder addInterceptor(RequestInterceptor requestInterceptor) {
            if (requestInterceptor == null) {
                return this;
            } else {
                if (this.requestInterceptors == null) {
                    this.requestInterceptors = new ArrayList();
                }

                this.requestInterceptors.add(requestInterceptor);
                return this;
            }
        }

        public Network.Builder addInterceptors(int pos, RequestInterceptor... requestInterceptors) {
            if (requestInterceptors != null && requestInterceptors.length != 0) {
                if (this.requestInterceptors == null) {
                    this.requestInterceptors = new ArrayList();
                }

                RequestInterceptor[] var3 = requestInterceptors;
                int var4 = requestInterceptors.length;

                for (int var5 = 0; var5 < var4; ++var5) {
                    RequestInterceptor requestInterceptor = var3[var5];
                    this.requestInterceptors.add(pos, requestInterceptor);
                }

                return this;
            } else {
                return this;
            }
        }

        public Network.Builder dns(Dns dns) {
            this.dns = dns;
            return this;
        }

        public Network.Builder setCookieJar(NetCookieJar cookieJar) {
            this.cookieJar = cookieJar;
            return this;
        }

        public Network.Builder connectTimeout(long connectTimeoutMillis) {
            this.connectTimeoutMillis = connectTimeoutMillis;
            return this;
        }

        public Network.Builder readTimeout(long readTimeoutMillis) {
            this.readTimeoutMillis = readTimeoutMillis;
            return this;
        }

        public Network.Builder writeTimeout(long writeTimeoutMillis) {
            this.writeTimeoutMillis = writeTimeoutMillis;
            return this;
        }

        public Network.Builder postContentByBody(boolean postBody) {
            this.postBody = postBody;
            return this;
        }

        public Network.Builder debugMode(boolean debugMode) {
            this.debugMode = debugMode;
            return this;
        }

        public Network.Builder trustAllCerts(boolean trustAllCerts) {
            this.trustAllCerts = trustAllCerts;
            return this;
        }

        public Network build() {
            return new Network(this);
        }
    }
}
