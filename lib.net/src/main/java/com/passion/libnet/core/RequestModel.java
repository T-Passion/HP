package com.passion.libnet.core;

import com.orhanobut.logger.Logger;
import com.passion.libnet.core.convert.Converter;
import com.passion.libnet.core.exception.BizApiException;
import com.passion.libnet.core.request.RequestVar;
import com.passion.libnet.core.utils.SafeCheckUtil;
import com.passion.libnet.core.utils.StringUtil;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaos
 * on 2017/11/15. 15:40
 * 文件描述：包含了请求接口的所有必要业务信息
 */

public final class RequestModel {

    private final long connectTimeoutMillis;
    private final long readTimeoutMillis;
    private final long writeTimeoutMillis;
    private final boolean gzipEncoding;

    private final String mediaType;
    private final String postContent;
    private final Type responseType;

    private final String fullUrl;
    private final String pathUrl;
    private final String requestMethod;
    private final Map<String, String> headers;
    private final Map<String, String> urlParams;
    private final Map<String, Object> bodyParams;
    private String urlVersion = RequestVar.VERSION_V1;

    private final Converter converter;
    private boolean isMultiPart;


    public RequestModel(RequestModel.Builder builder) {
        this.requestMethod = builder.requestMethod;
        this.fullUrl = builder.fullUrl;
        this.pathUrl = builder.pathUrl;
        this.postContent = builder.postContent;
        this.mediaType = builder.mediaType;
        this.connectTimeoutMillis = builder.connectTimeoutMillis;
        this.readTimeoutMillis = builder.readTimeoutMillis;
        this.writeTimeoutMillis = builder.writeTimeoutMillis;
        this.responseType = builder.responseType;
        this.converter = builder.converter;
        this.urlParams = builder.urlParams;
        this.urlVersion = builder.pathVersion;
        this.bodyParams = builder.bodyParams;
        this.headers = builder.headers;
        this.gzipEncoding = builder.gzipEncoding;
    }

    public String url() {
        return this.fullUrl;
    }

    public String method() {
        return this.requestMethod;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public Map<String, String> headers() {
        return this.headers == null ? new HashMap<String, String>() : Collections.unmodifiableMap(this.headers);
    }

    public Map<String, String> getUrlParameters() {
        return this.urlParams == null ? new HashMap<String, String>()  : Collections.unmodifiableMap(this.urlParams);
    }

    public Type getResponseType() {
        return responseType;
    }

    public Map<String, String> getParameters() {
        if (this.bodyParams == null) {
            return null;
        } else {
            Map<String, String> formMap = new HashMap();

            for (Object o : this.bodyParams.entrySet()) {
                Map.Entry<String, Object> entry = (Map.Entry) o;
                String value = entry.getValue() instanceof String ? (String) entry.getValue() : String.valueOf(entry.getValue());
                formMap.put(entry.getKey(), value);
            }

            return Collections.unmodifiableMap(formMap);
        }
    }


    public RequestModel.Builder newBuilder() {
        return RequestVar.REQUEST_GET.equals(this.requestMethod)
                ? new GetBuilder(this)
                : (RequestVar.REQUEST_POST.equals(this.requestMethod)
                ? new PostBuilder(this)
                : new Builder(this));
    }

    public static RequestModel.GetBuilder get(String url) {
        return new RequestModel.GetBuilder(url);
    }

    public static RequestModel.PostBuilder post(String url) {
        return new RequestModel.PostBuilder(url);
    }

    public static final class PostBuilder extends RequestModel.Builder<RequestModel.PostBuilder> {
        public PostBuilder(String pathUrl) {
            super(RequestVar.REQUEST_POST, pathUrl);
        }

        public PostBuilder(RequestModel requestModel) {
            super(requestModel);
        }

        public RequestModel.PostBuilder postContent(String content, String mediaType) {
            this.postContent = content;
            this.mediaType = mediaType;
            return this;
        }

        public RequestModel.PostBuilder gzipEncoding(boolean gzipEncoding) {
            this.gzipEncoding = gzipEncoding;
            return this;
        }
    }

    public static final class GetBuilder extends RequestModel.Builder<RequestModel.GetBuilder> {
        public GetBuilder(String pathUrl) {
            super(RequestVar.REQUEST_GET, pathUrl);
        }


        public GetBuilder(RequestModel requestModel) {
            super(requestModel);
        }

        public RequestModel.GetBuilder addUrlParameters(Map<String, String> urlParamsMap) {
            if (urlParamsMap != null) {
                this.urlParams.putAll(urlParamsMap);
            }
            return this;
        }

        public RequestModel.GetBuilder addUrlParameter(String name, String value) {
            this.urlParams.put(name, value);
            return this;
        }
    }

    public static class Builder<T extends RequestModel.Builder> {
        private long connectTimeoutMillis;
        private long readTimeoutMillis;
        private long writeTimeoutMillis;
        boolean gzipEncoding;

        String mediaType;
        String postContent;
        private Type responseType;

        private String fullUrl;
        private String pathUrl;
        private String requestMethod;
        private String pathVersion ;
        Map<String, String> headers = new HashMap<>();
        Map<String, String> urlParams = new HashMap<>();
        Map<String, Object> bodyParams = new HashMap<>();

        private Converter converter;

        /**
         * 只提供请求方法，以及相对路径
         *
         * @param requestMethod 请求方法
         * @param pathUrl       相对路径
         */
        public Builder(String requestMethod, String pathUrl) {
            this.requestMethod = requestMethod;
            this.pathUrl = pathUrl;
        }

        Builder(RequestModel requestModel) {
            this.requestMethod = requestModel.requestMethod;
            this.fullUrl = requestModel.fullUrl;

            this.postContent = requestModel.postContent;
            this.mediaType = requestModel.mediaType;
            this.connectTimeoutMillis = requestModel.connectTimeoutMillis;
            this.readTimeoutMillis = requestModel.readTimeoutMillis;
            this.writeTimeoutMillis = requestModel.writeTimeoutMillis;
            this.responseType = requestModel.responseType;
            this.converter = requestModel.converter;
            this.urlParams = requestModel.urlParams;
            this.pathVersion = requestModel.urlVersion;
            this.bodyParams = requestModel.bodyParams;
            this.headers = requestModel.headers;
            this.gzipEncoding = requestModel.gzipEncoding;
        }

        public RequestModel build() {
            this.fullUrl = this.getFullUrl();
            if (this.fullUrl == null) {
                throw new IllegalArgumentException("请检查 ！ Request fullUrl == null");
            } else {
                return new RequestModel(this);
            }
        }

        public Builder<T> connectTimeout(long connectTimeoutMillis) {
            this.connectTimeoutMillis = connectTimeoutMillis;
            return this;
        }

        public Builder<T> readTimeout(long readTimeoutMillis) {
            this.readTimeoutMillis = readTimeoutMillis;
            return this;
        }

        public Builder<T> writeTimeout(long writeTimeoutMillis) {
            this.writeTimeoutMillis = writeTimeoutMillis;
            return this;
        }

        public Builder<T> gzipEncoding(boolean gzipEncoding) {
            this.gzipEncoding = gzipEncoding;
            return this;
        }

        public Builder<T> responseType(Type responseType) {
            this.responseType = responseType;
            return this;
        }


        public Builder<T> addHeader(String name, String value) {
            this.headers.put(name, value);
            return this;
        }

        public Builder<T> addHeaders(Map<String, String> headers) {
            if (headers == null) {
                return this;
            }
            this.headers.putAll(headers);
            return this;
        }

        public Builder<T> addUrlParameters(Map<String, String> urlParamsMap) {
            if (urlParamsMap != null) {
                if (this.urlParams == null) {
                    this.urlParams = new HashMap();
                }

                this.urlParams.putAll(urlParamsMap);
            }

            return this;
        }

        public Builder<T> addUrlParameter(String name, String value) {
            if (this.urlParams == null) {
                this.urlParams = new HashMap();
            }

            this.urlParams.put(name, value);
            return this;
        }

        public Builder<T> addParameters(Map<String, Object> parameters) {
            if (parameters != null) {
                this.bodyParams.putAll(parameters);
            }
            return this;
        }

        public Builder<T> addParameter(String name, Object value) {
            this.bodyParams.put(name, value);
            return this;
        }

        public Builder<T> version(String urlVersion) {
            this.pathVersion = urlVersion;
            return this;
        }


        Builder<T> converter(Converter converter) {
            this.converter = converter;
            return this;
        }


        private String getFullUrl() {
            if (this.fullUrl != null) {
                return this.fullUrl;
            } else {
                String hostUrl = NetWrapper.getConfig().getHostUrl();
                if (!SafeCheckUtil.isNull(pathUrl) && !SafeCheckUtil.isNull(pathVersion)) {
                    String realPathUrl = StringUtil.replace(pathUrl, RequestVar.VERSION_CONST, pathVersion);
                    this.fullUrl = hostUrl + realPathUrl;
                } else if(!SafeCheckUtil.isNull(pathUrl)){
                    this.fullUrl = hostUrl + pathUrl;
                }else {
                    Logger.e(new BizApiException("请检查是否已经设置请求URL"));

                }
                return this.fullUrl;
            }
        }
    }
}
