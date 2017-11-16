package com.passion.libnet.core.mock;

import com.passion.libnet.core.convert.Converter;
import com.passion.libnet.core.imp.Dispatcher;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by chaos
 * on 2017/11/16. 17:47
 * 文件描述：
 */

public class DefaultDispatcher implements Dispatcher {
    private MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
    private Converter converter;

    public DefaultDispatcher(Converter converter) {
        this.converter = converter;
    }

    public Response dispatch(Request request) throws IOException {
        HttpUrl httpUrl = request.url();
        MockResponse mockResponse = HttpMock.get(httpUrl);
        if(mockResponse == null) {
            return null;
        } else {
            Response.Builder builder = new Response.Builder();
            builder.request(request);
            builder.protocol(Protocol.HTTP_1_1);
            builder.code(mockResponse.getCode());
            builder.message(mockResponse.getMessage());
            builder.headers(mockResponse.getHeaders());
            if(mockResponse.getBody() != null) {
                builder.body(ResponseBody.create(this.mediaType, mockResponse.getBody().size(), mockResponse.getBody()));
            } else {
                Object bean = mockResponse.getBean();
                if(bean != null) {
                    if(this.converter == null) {
                        throw new IllegalArgumentException("converter == null");
                    }

                    byte[] bytes = this.converter.toBody(bean);
                    builder.header("Content-Length", String.valueOf(bytes.length));
                    builder.body(ResponseBody.create(this.mediaType, bytes));
                }
            }

            return builder.build();
        }
    }
}