package com.passion.libnet.core;

import com.passion.libnet.core.imp.RequestBuilder;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by chaos
 * on 2017/11/16. 17:19
 * 文件描述：
 */

public class OKHttpRequestBuilder implements RequestBuilder<Request> {
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream; charset=utf-8");
    private final Request.Builder builder = new Request.Builder();
    private okhttp3.HttpUrl.Builder urlBuilder;
    private okhttp3.MultipartBody.Builder multipartBuilder;
    private okhttp3.FormBody.Builder formBuilder;
    private RequestBody body;
    private MediaType contentType;
    private String method;
    private boolean hasBody;
    private boolean isFormEncoded;
    private boolean isMultipart;
    private boolean inited;
    private Object tag;

    public OKHttpRequestBuilder() {
    }

    public Request build() {
        if (!this.inited) {
            throw new IllegalArgumentException("RequestBuilder has not been initialized,call method RequestBuilder.init() first.");
        } else {
            HttpUrl httpUrl = this.urlBuilder.build();
            RequestBody body = this.body;
            if (body == null) {
                if (this.formBuilder != null) {
                    body = this.formBuilder.build();
                } else if (this.multipartBuilder != null) {
                    body = this.multipartBuilder.setType(MultipartBody.FORM).build();
                } else if (this.hasBody) {
                    body = RequestBody.create((MediaType) null, new byte[0]);
                }
            }

            MediaType contentType = this.contentType;
            if (contentType != null) {
                if (body != null) {
                    body = new OKHttpRequestBuilder.ContentTypeRequestBody((RequestBody) body, contentType);
                } else {
                    this.builder.addHeader("Content-Type", contentType.toString());
                }
            }

            return this.builder.url(httpUrl).method(this.method, (RequestBody) body).tag(this.tag).build();
        }
    }

    public void init(String method, String baseUrl, String relativeUrl, boolean hasBody, boolean isFormEncoded, boolean isMultipart, Object tag) {
        if (method == null) {
            throw new NullPointerException("Network receives method == null");
        } else if (baseUrl == null) {
            throw new NullPointerException("Network receives url == null");
        } else {
            HttpUrl httpUrl = HttpUrl.parse(baseUrl);
            if (httpUrl == null) {
                throw new IllegalArgumentException("Malformed URL : " + baseUrl);
            } else {
                if (relativeUrl != null) {
                    this.urlBuilder = httpUrl.newBuilder(relativeUrl);
                    if (this.urlBuilder == null) {
                        throw new IllegalArgumentException("Malformed URL. Base: " + baseUrl + ", Relative: " + relativeUrl);
                    }
                } else {
                    this.urlBuilder = httpUrl.newBuilder();
                }

                this.method = method;
                this.hasBody = hasBody;
                this.isFormEncoded = isFormEncoded;
                this.isMultipart = isMultipart;
                if (isFormEncoded) {
                    this.formBuilder = new okhttp3.FormBody.Builder();
                } else if (isMultipart) {
                    this.multipartBuilder = new okhttp3.MultipartBody.Builder();
                }

                this.tag = tag;
                this.inited = true;
            }
        }
    }

    public void addHeader(String name, String value) {
        if ("Content-Type".equalsIgnoreCase(name)) {
            MediaType type = MediaType.parse(value);
            if (type == null) {
                throw new IllegalArgumentException("Malformed content type: " + value);
            }

            this.contentType = type;
        } else {
            this.builder.addHeader(name, value);
        }

    }

    public void addQueryParam(String name, String value) {
        this.urlBuilder.addQueryParameter(name, value);
    }

    public void addFormField(String name, String value) {
        if (this.isMultipart) {
            this.addPart(name, value, (String) null);
        } else {
            this.formBuilder.add(name, value);
        }

    }

    public void addPart(String name, Object value, String fileName) {
        if (value != null) {
            if (value instanceof File) {
                File file = (File) value;
                if (!file.exists()) {
                    return;
                }

                String rawFileName = file.getName();
                MediaType mimeType = guessMimeType(rawFileName);
                RequestBody fileBody = RequestBody.create(mimeType, file);
                if (fileName == null) {
                    fileName = rawFileName;
                }

                this.multipartBuilder.addFormDataPart(name, fileName, fileBody);
            } else if (value instanceof byte[]) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), (byte[]) ((byte[]) value));
                if (fileName == null) {
                    fileName = "file";
                }

                this.multipartBuilder.addFormDataPart(name, fileName, fileBody);
            } else {
                this.multipartBuilder.addFormDataPart(name, value.toString());
            }

        }
    }

    public void setBodyData(byte[] bodyData, String mediaType) {
        RequestBody requestBody = null;
        if (mediaType == null) {
            requestBody = RequestBody.create(MEDIA_TYPE_JSON, bodyData);
        } else {
            requestBody = RequestBody.create(MediaType.parse(mediaType), bodyData);
        }

        this.body = requestBody;
    }

    private static MediaType guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        return contentTypeFor == null ? MEDIA_TYPE_STREAM : MediaType.parse(contentTypeFor);
    }

    private static class ContentTypeRequestBody extends RequestBody {
        private final RequestBody requestBody;
        private final MediaType contentType;

        ContentTypeRequestBody(RequestBody requestBody, MediaType contentType) {
            this.requestBody = requestBody;
            this.contentType = contentType;
        }

        public MediaType contentType() {
            return this.contentType;
        }

        public long contentLength() throws IOException {
            return this.requestBody.contentLength();
        }

        public void writeTo(BufferedSink sink) throws IOException {
            this.requestBody.writeTo(sink);
        }
    }
}
