package com.passion.libnet.core;

import com.passion.libnet.core.imp.RawResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by chaos
 * on 2017/11/16. 13:57
 * 文件描述：
 */

public class ResponseModel<T> {
    private final T data;
    private final RawResponse rawResponse;

    private ResponseModel(T data, RawResponse rawResponse) {
        if (rawResponse == null) {
            throw new NullPointerException("rawResponse == null");
        } else {
            this.data = data;
            this.rawResponse = rawResponse;
        }
    }

    public static <T> ResponseModel success(T data, RawResponse rawResponse) {
        return new ResponseModel(data, rawResponse);
    }

    public static ResponseModel success(RawResponse rawResponse) {
        return new ResponseModel(null, rawResponse);
    }

    public static <T> ResponseModel error(RawResponse rawResponse) {
        if (rawResponse != null && rawResponse.isSuccessful()) {
            throw new IllegalArgumentException("rawResponse should not be successful data when calling error.");
        } else {
            return new ResponseModel(null, rawResponse);
        }
    }

    public int httpStatusCode() {
        return this.rawResponse.code();
    }

    public String httpStatusMessage() {
        return this.rawResponse.message();
    }

    public Map<String, List<String>> headers() {
        return this.rawResponse.headers();
    }

    public String header(String name) {
        return this.rawResponse.header(name);
    }

    public List<String> headers(String name) {
        return this.rawResponse.headers(name);
    }

    public boolean isSuccessful() {
        return this.rawResponse.isSuccessful();
    }

    public byte[] bytes() {
        return this.rawResponse.bytes();
    }

    public T data() {
        return this.data;
    }
}
