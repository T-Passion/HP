package com.passion.libnet.core.request;

import com.passion.libnet.core.RequestModel;
import com.passion.libnet.core.convert.Converter;
import com.passion.libnet.core.imp.RequestBuilder;
import com.passion.libnet.core.utils.Part;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by chaos
 * on 2017/11/16. 16:39
 * 文件描述：
 */

public class RequestParser {
    private String httpMethod;
    private String url;
    private String relativeUrl;
    private String postContent;
    private String mediaType;
    private boolean hasBody;
    private boolean isFormEncoded;
    private boolean isMultipart;
    private boolean postBody;
    private boolean gzipEncoding;
    private long connectTimeoutMillis;
    private long readTimeoutMillis;
    private long writeTimeoutMillis;
    private Type responseType;
    private Object tag;
    private Converter converter;
    private Map<String, String> queryParamsMap;
    private Map<String, Object> formFieldsMap;
    private Map<String, String> headers;
    private List<Part> parts;

    RequestParser(RequestModel requestModel) {
        this.httpMethod = requestModel.httpMethod;
        this.url = requestModel.url;
        this.postContent = requestModel.postContent;
        this.mediaType = requestModel.mediaType;
        this.postBody = this.postContent != null | (requestModel.postBody == null ? false : requestModel.postBody.booleanValue());
        this.connectTimeoutMillis = requestModel.connectTimeoutMillis;
        this.readTimeoutMillis = requestModel.readTimeoutMillis;
        this.writeTimeoutMillis = requestModel.writeTimeoutMillis;
        this.responseType = requestModel.responseType;
        this.tag = requestModel.tag;
        this.converter = requestModel.converter;
        this.headers = requestModel.headers;
        this.queryParamsMap = requestModel.urlParams;
        this.formFieldsMap = requestModel.params;
        this.parts = requestModel.parts;
        this.gzipEncoding = requestModel.gzipEncoding;
        this.isMultipart = requestModel.isMultiPart();
        if (!this.postBody && !this.isMultipart && "POST".equals(this.httpMethod)) {
            this.isFormEncoded = true;
        }

    }

    static RequestParser create(RequestModel requestModel) {
        return create(requestModel, (List) null);
    }

    static RequestParser create(RequestModel requestModel, List<RequestInterceptor> requestInterceptors) {
        RequestModel expected = requestModel;
        if (requestInterceptors != null) {
            Iterator var3 = requestInterceptors.iterator();

            while (var3.hasNext()) {
                RequestInterceptor requestInterceptor = (RequestInterceptor) var3.next();
                expected = requestInterceptor.intercept(expected);
                if (expected == null) {
                    throw new NullPointerException("Receives null request from request interceptor: " + requestInterceptor.getClass().getName());
                }
            }
        }

        return new RequestParser(expected);
    }

    public void parseTo(RequestBuilder requestBuilder) {
        requestBuilder.init(this.httpMethod, this.url, this.relativeUrl, this.hasBody, this.isFormEncoded, this.isMultipart, this.tag);
        Iterator var2;
        Map.Entry entry;
        String name;
        String value;
        if (this.headers != null && !this.headers.isEmpty()) {
            var2 = this.headers.entrySet().iterator();

            while (var2.hasNext()) {
                entry = (Map.Entry) var2.next();
                name = (String) entry.getKey();
                value = (String) entry.getValue();
                if (name != null && value != null) {
                    requestBuilder.addHeader(name, value);
                }
            }
        }

        if ("GET".equals(this.httpMethod)) {
            if (this.queryParamsMap != null && !this.queryParamsMap.isEmpty()) {
                var2 = this.queryParamsMap.entrySet().iterator();

                while (var2.hasNext()) {
                    entry = (Map.Entry) var2.next();
                    name = (String) entry.getKey();
                    value = (String) entry.getValue();
                    if (name != null && value != null) {
                        requestBuilder.addQueryParam(name, value);
                    }
                }
            }

            if (this.formFieldsMap != null && !this.formFieldsMap.isEmpty()) {
                var2 = this.formFieldsMap.entrySet().iterator();

                while (var2.hasNext()) {
                    entry = (Map.Entry) var2.next();
                    name = (String) entry.getKey();
                    Object value1 = entry.getValue();
                    if (name != null && value1 != null) {
                        String paramValue = value1 instanceof String ? (String) value1 : String.valueOf(value1);
                        requestBuilder.addQueryParam(name, paramValue);
                    }
                }
            }
        } else {
            if (!"POST".equals(this.httpMethod)) {
                throw new IllegalArgumentException("Network only receives GET or POST method yet.");
            }

            this.parsePost(requestBuilder);
        }

    }

    private void parsePost(RequestBuilder requestBuilder) {
        Iterator var2;
        Part part;
        if (this.postBody) {
            if (this.isMultipart) {
                throw new IllegalArgumentException("Post body can not be used in a multipart form.");
            } else {
                byte[] bodyData;
                if (this.postContent != null) {
                    bodyData = this.postContent.getBytes(Charset.forName("UTF-8"));
                    requestBuilder.setBodyData(bodyData, this.mediaType);
                } else if (this.converter == null) {
                    throw new IllegalArgumentException("Post body must be assigned a converter.");
                } else {
                    if (this.formFieldsMap != null) {
                        if (this.parts != null) {
                            var2 = this.parts.iterator();

                            while (var2.hasNext()) {
                                part = (Part) var2.next();
                                if (part.name != null && part.value != null) {
                                    this.formFieldsMap.put(part.name, part.value.toString());
                                }
                            }
                        }

                        var2 = null;

                        try {
                            bodyData = this.converter.toBody(this.formFieldsMap);
                        } catch (IOException var11) {
                            throw new RuntimeException("Unable to convert " + this.formFieldsMap + " to RequestBody", var11);
                        }

                        requestBuilder.setBodyData(bodyData, this.mediaType);
                    } else if (this.parts != null) {
                        var2 = null;

                        try {
                            bodyData = this.converter.toBody(this.parts);
                        } catch (IOException var10) {
                            throw new RuntimeException("Unable to convert " + this.parts + " to RequestBody", var10);
                        }

                        requestBuilder.setBodyData(bodyData, this.mediaType);
                    }

                }
            }
        } else {
            Map.Entry entry;
            String name;
            if (this.queryParamsMap != null && !this.queryParamsMap.isEmpty()) {
                var2 = this.queryParamsMap.entrySet().iterator();

                while (var2.hasNext()) {
                    entry = (Map.Entry) var2.next();
                    name = (String) entry.getKey();
                    String value = (String) entry.getValue();
                    if (name != null && value != null) {
                        requestBuilder.addQueryParam(name, value);
                    }
                }
            }

            if (this.formFieldsMap != null) {
                var2 = this.formFieldsMap.entrySet().iterator();

                label143:
                while (true) {
                    while (true) {
                        Object value;
                        do {
                            do {
                                if (!var2.hasNext()) {
                                    break label143;
                                }

                                entry = (Map.Entry) var2.next();
                                name = (String) entry.getKey();
                                value = entry.getValue();
                            } while (name == null);
                        } while (value == null);

                        if (this.isMultipart) {
                            requestBuilder.addPart(name, value, (String) null);
                        } else if (value instanceof String) {
                            requestBuilder.addFormField(name, (String) value);
                        } else if (value instanceof String[]) {
                            String[] var15 = (String[]) ((String[]) value);
                            int var16 = var15.length;

                            for (int var8 = 0; var8 < var16; ++var8) {
                                String val = var15[var8];
                                requestBuilder.addFormField(name, val);
                            }
                        } else if (value instanceof Collection) {
                            Iterator var6 = ((Collection) value).iterator();

                            while (var6.hasNext()) {
                                Object val = var6.next();
                                requestBuilder.addFormField(name, String.valueOf(val));
                            }
                        } else {
                            requestBuilder.addFormField(name, value.toString());
                        }
                    }
                }
            }

            if (this.parts != null) {
                var2 = this.parts.iterator();

                while (var2.hasNext()) {
                    part = (Part) var2.next();
                    if (part.name != null && part.value != null) {
                        if (this.isMultipart) {
                            requestBuilder.addPart(part.name, part.value, part.fileName);
                        } else {
                            requestBuilder.addFormField(part.name, part.value.toString());
                        }
                    }
                }
            }

        }
    }

    long getConnectTimeoutMillis() {
        return this.connectTimeoutMillis;
    }

    long getReadTimeoutMillis() {
        return this.readTimeoutMillis;
    }

    long getWriteTimeoutMillis() {
        return this.writeTimeoutMillis;
    }

    boolean isGzipEncoding() {
        return this.gzipEncoding;
    }

    Type getResponseType() {
        return this.responseType;
    }

    Converter getConverter() {
        return this.converter;
    }
}

