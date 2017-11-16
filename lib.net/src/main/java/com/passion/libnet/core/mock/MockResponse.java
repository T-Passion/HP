package com.passion.libnet.core.mock;

import okhttp3.Headers;
import okio.Buffer;

/**
 * Created by chaos
 * on 2017/11/16. 17:22
 * 文件描述：
 */

public class MockResponse {
    private Headers.Builder headers = new Headers.Builder();
    private Buffer body;
    private int code;
    private String message;
    private String status;
    private Object bean;

    public MockResponse() {
        this.setResponseCode(200);
        this.setHeader("Content-Length", Integer.valueOf(0));
        this.setBody("");
    }

    public MockResponse setBody(Buffer body) {
        this.setHeader("Content-Length", Long.valueOf(body.size()));
        this.body = body.clone();
        return this;
    }

    public MockResponse setHeader(String name, Object value) {
        this.removeHeader(name);
        return this.addHeader(name, value);
    }

    public MockResponse addHeader(String name, Object value) {
        this.headers.add(name, String.valueOf(value));
        return this;
    }

    public MockResponse removeHeader(String name) {
        this.headers.removeAll(name);
        return this;
    }

    public MockResponse setBody(String body) {
        return this.setBody((new Buffer()).writeUtf8(body));
    }

    public MockResponse setBody(Object bean) {
        if(bean != null) {
            this.body = null;
            this.bean = bean;
        }

        return this;
    }

    public MockResponse setResponseCode(int code) {
        this.code = code;
        this.message = "Mock Response";
        if(code >= 100 && code < 200) {
            this.message = "Informational(Mock)";
        } else if(code >= 200 && code < 300) {
            this.message = "OK(Mock)";
        } else if(code >= 300 && code < 400) {
            this.message = "Redirection(Mock)";
        } else if(code >= 400 && code < 500) {
            this.message = "Client Error(Mock)";
        } else if(code >= 500 && code < 600) {
            this.message = "Server Error(Mock)";
        }

        this.status = "HTTP/1.1 " + code + " " + this.message;
        return this;
    }

    int getCode() {
        return this.code;
    }

    String getMessage() {
        return this.message;
    }

    Buffer getBody() {
        return this.body;
    }

    Object getBean() {
        return this.bean;
    }

    public Headers getHeaders() {
        return this.headers.build();
    }

    public String toString() {
        return this.status;
    }
}

