package com.passion.libnet.core.mock;

import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;

/**
 * Created by chaos
 * on 2017/11/16. 17:22
 * 文件描述：
 */

public class HttpMock {
    private static final Map<String, MockResponse> MOCK_MAP = new HashMap();
    private static boolean enableMock;

    public HttpMock() {
    }

    public static void enableMock(boolean enable) {
        enableMock = enable;
    }

    public static boolean isEnableMock() {
        return enableMock;
    }

    static MockResponse get(HttpUrl httpUrl) {
        if(httpUrl == null) {
            return null;
        } else {
            String url = httpUrl.toString();
            MockResponse mockResponse = MOCK_MAP.get(url);
            if(mockResponse == null) {
                int pathStart = url.indexOf(47, httpUrl.scheme().length() + 3);
                String path = url.substring(pathStart);
                mockResponse = MOCK_MAP.get(path);
            }

            return mockResponse;
        }
    }

    public static MockResponse createMockResponse(String url) {
        MockResponse mockResponse = new MockResponse();
        if(url.startsWith("%")) {
            url = url.substring(1);
        }

        MOCK_MAP.put(url, mockResponse);
        return mockResponse;
    }
}