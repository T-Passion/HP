package com.passion.libnet.core.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by chaos
 * on 2017/11/16. 14:35
 * 文件描述：
 */

public class OKHttpGzipInterceptor implements Interceptor {


    private OKHttpGzipInterceptor() {
    }

    public static final class Holder{
        static final OKHttpGzipInterceptor GZIP_INTERCEPTOR = new OKHttpGzipInterceptor();
    }

    public static OKHttpGzipInterceptor get(){
        return Holder.GZIP_INTERCEPTOR;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return "gzip".equalsIgnoreCase(response.header("Content-Encoding"))
                && response.body().contentLength() == 0L ? response.newBuilder().removeHeader("Content-Encoding").build() : response;
    }
}
