package com.passion.libnet.core;

import com.passion.libnet.core.convert.Converter;
import com.passion.libnet.core.utils.Part;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chaos
 * on 2017/11/15. 15:40
 * 文件描述：
 */

public final class RequestModel<T> {
    public static final String MEDIA_TYPE_PLAIN = "text/plain";
    public static final String MEDIA_TYPE_JSON = "application/json";
    private static final Pattern PATTERN = Pattern.compile("//");
    public final String httpMethod;
    public final String url;
    public final Boolean postBody;
    public final String postContent;
    public final String mediaType;
    public final long connectTimeoutMillis;
    public final long readTimeoutMillis;
    public final long writeTimeoutMillis;
    public final Type responseType;
    public final Object tag;
    public final Converter converter;
    public final Map<String, String> urlParams;
    public final Map<String, Object> params;
    public final Map<String, String> headers;
    public final List<Part> parts;
    public final boolean gzipEncoding;
    private boolean isMultiPart;
    private boolean signable;

    public RequestModel(RequestModel.Builder builder) {
        this.httpMethod = builder.httpMethod;
        this.url = builder.url;
        this.postBody = builder.postBody;
        this.postContent = builder.postContent;
        this.mediaType = builder.mediaType;
        this.connectTimeoutMillis = builder.connectTimeoutMillis;
        this.readTimeoutMillis = builder.readTimeoutMillis;
        this.writeTimeoutMillis = builder.writeTimeoutMillis;
        this.responseType = builder.responseType;
        this.tag = builder.tag;
        this.converter = builder.converter;
        this.urlParams = builder.urlParams;
        this.params = builder.params;
        this.parts = builder.parts;
        this.headers = builder.headers;
        this.signable = builder.signable;
        this.gzipEncoding = builder.gzipEncoding;
    }

    public String url() {
        return this.url;
    }

    public String method() {
        return this.httpMethod;
    }

    public boolean isSignable() {
        return this.signable;
    }

    public Map<String, String> headers() {
        return this.headers == null ? null : Collections.unmodifiableMap(this.headers);
    }

    public Map<String, String> getUrlParameters() {
        return this.urlParams == null ? null : Collections.unmodifiableMap(this.urlParams);
    }

    public Map<String, String> getParameters() {
        if (this.params == null) {
            return null;
        } else {
            Map<String, String> formMap = new HashMap();
            Iterator var2 = this.params.entrySet().iterator();

            while (var2.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry) var2.next();
                String value = entry.getValue() instanceof String ? (String) entry.getValue() : String.valueOf(entry.getValue());
                formMap.put(entry.getKey(), value);
            }

            return Collections.unmodifiableMap(formMap);
        }
    }

    public Map<String, File> getFormDataParts() {
        Map<String, File> partsMap = new HashMap();
        if (this.parts != null) {
            Iterator var2 = this.parts.iterator();

            while (var2.hasNext()) {
                Part part = (Part) var2.next();
                if (part.value != null && part.value instanceof File) {
                    partsMap.put(part.name, (File) part.value);
                }
            }
        }

        return Collections.unmodifiableMap(partsMap);
    }

    public boolean isMultiPart() {
        if (this.parts != null) {
            Iterator var1 = this.parts.iterator();

            Part part;
            do {
                do {
                    if (!var1.hasNext()) {
                        return this.isMultiPart;
                    }

                    part = (Part) var1.next();
                } while (part.value == null);
            } while (!(part.value instanceof File) && !(part.value instanceof byte[]));

            this.isMultiPart = true;
        }

        return this.isMultiPart;
    }

    public RequestModel.Builder newBuilder() {
        return (RequestModel.Builder) ("GET".equals(this.httpMethod) ? new RequestModel.GetBuilder(this) : ("POST".equals(this.httpMethod) ? new RequestModel.PostBuilder(this) : new RequestModel.Builder(this)));
    }

    public static RequestModel.GetBuilder get(String url) {
        return new RequestModel.GetBuilder(url);
    }

    public static RequestModel.PostBuilder post(String url) {
        return new RequestModel.PostBuilder(url);
    }

    public static final class PostBuilder extends RequestModel.Builder<RequestModel.PostBuilder> {
        public PostBuilder(String url) {
            super("POST", url);
        }

        public PostBuilder(String baseUrl, String relativeUrl) {
            super("POST", baseUrl, relativeUrl);
        }

        public PostBuilder(RequestModel requestModel) {
            super(requestModel);
        }

        public RequestModel.PostBuilder addFormDataPart(String name, File value, String fileName) {
            if (this.parts == null) {
                this.parts = new ArrayList();
            }

            this.parts.add(Part.create(name, value, fileName));
            return this;
        }

        public RequestModel.PostBuilder postContent(String content, String mediaType) {
            this.postContent = content;
            this.mediaType = mediaType;
            return this;
        }

        public RequestModel.PostBuilder postContentByBody(Boolean postBody) {
            return (RequestModel.PostBuilder) super.postContentByBody(postBody);
        }

        public RequestModel.PostBuilder gzipEncoding(boolean gzipEncoding) {
            this.gzipEncoding = gzipEncoding;
            return this;
        }
    }

    public static final class GetBuilder extends RequestModel.Builder<RequestModel.GetBuilder> {
        public GetBuilder(String url) {
            super("GET", url);
        }

        public GetBuilder(String baseUrl, String relativeUrl) {
            super("GET", baseUrl, relativeUrl);
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
        private final String httpMethod;
        private String baseUrl;
        private String relativeUrl;
        private String url;
        private Type responseType;
        private Object tag;
        Map<String, String> urlParams = new HashMap();
        Map<String, Object> params = new HashMap();
        Map<String, String> headers;
        List<Part> parts;
        private long connectTimeoutMillis;
        private long readTimeoutMillis;
        private long writeTimeoutMillis;
        String postContent;
        String mediaType;
        private Boolean postBody;
        private Converter converter;
        private boolean signable = true;
        protected boolean gzipEncoding;

        public Builder(String httpMethod, String url) {
            this.httpMethod = httpMethod;
            this.url = url;
        }

        public Builder(String httpMethod, String baseUrl, String relativeUrl) {
            this.httpMethod = httpMethod;
            this.baseUrl = baseUrl;
            this.relativeUrl = relativeUrl;
        }

        Builder(RequestModel requestModel) {
            this.httpMethod = requestModel.httpMethod;
            this.url = requestModel.url;
            this.postBody = requestModel.postBody;
            this.postContent = requestModel.postContent;
            this.mediaType = requestModel.mediaType;
            this.connectTimeoutMillis = requestModel.connectTimeoutMillis;
            this.readTimeoutMillis = requestModel.readTimeoutMillis;
            this.writeTimeoutMillis = requestModel.writeTimeoutMillis;
            this.responseType = requestModel.responseType;
            this.tag = requestModel.tag;
            this.converter = requestModel.converter;
            this.urlParams = requestModel.urlParams;
            this.params = requestModel.params;
            this.parts = requestModel.parts;
            this.headers = requestModel.headers;
            this.signable = requestModel.signable;
            this.gzipEncoding = requestModel.gzipEncoding;
        }

        public RequestModel build() {
            this.url = this.getUrl();
            if (this.url == null) {
                throw new IllegalArgumentException("Request url == null");
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

        public Builder<T> responseType(Type responseType) {
            this.responseType = responseType;
            return this;
        }

        public Builder<T> tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Builder<T> addHeader(String name, String value) {
            if (this.headers == null) {
                this.headers = new HashMap();
            }

            this.headers.put(name, value);
            return this;
        }

        public Builder<T> addHeaders(Map<String, String> headers) {
            if (headers == null) {
                return this;
            } else {
                if (this.headers == null) {
                    this.headers = new HashMap();
                }

                this.headers.putAll(headers);
                return this;
            }
        }

        public Builder<T> addHeaders(List<String> headers) {
            if (headers == null) {
                return this;
            } else {
                if (this.headers == null) {
                    this.headers = new HashMap();
                }

                if (headers.size() % 2 != 0) {
                    throw new IllegalArgumentException("Headers List content must be name-value paired,current headers list size is " + headers.size());
                } else {
                    for (int i = 0; i < headers.size() - 1; i += 2) {
                        this.headers.put(headers.get(i), headers.get(i + 1));
                    }

                    return this;
                }
            }
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
                this.params.putAll(parameters);
            }

            return this;
        }

        public Builder<T> addParameter(String name, Object value) {
            this.params.put(name, value);
            return this;
        }

        public Builder<T> signable(boolean signable) {
            this.signable = signable;
            return this;
        }

        Builder<T> postContentByBody(Boolean postBody) {
            this.postBody = postBody;
            return this;
        }

        Builder<T> converter(Converter converter) {
            this.converter = converter;
            return this;
        }

        private String getUrl() {
            if (this.url != null) {
                return this.url;
            } else {
                if (this.baseUrl != null && this.relativeUrl != null) {
                    if (this.relativeUrl.startsWith("%s")) {
                        this.url = String.format(this.relativeUrl, new Object[]{this.baseUrl});
                    } else {
                        this.url = this.baseUrl + this.relativeUrl;
                        Matcher matcher = RequestModel.PATTERN.matcher(this.url);
                        if (matcher.find(8)) {
                            StringBuilder stringBuffer = new StringBuilder(this.url);
                            stringBuffer.replace(matcher.start(), matcher.end(), "/");
                            this.url = stringBuffer.toString();
                        }
                    }
                }

                return this.url;
            }
        }
    }
}
