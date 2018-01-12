package com.passion.libnet.core.interceptor;

import com.passion.libnet.core.convert.Converter;
import com.passion.libnet.core.imp.Dispatcher;
import com.passion.libnet.core.mock.DefaultDispatcher;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chaos
 * on 2017/11/16. 14:40
 * 文件描述：
 */

public class OkHttpMockServerInterceptor implements Interceptor {
    private Dispatcher dispatcher;

    public OkHttpMockServerInterceptor(Converter converter) {
        this.dispatcher = new DefaultDispatcher(converter);
    }

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = this.dispatcher.dispatch(request);
        return response != null ? response : chain.proceed(request);
    }
}
