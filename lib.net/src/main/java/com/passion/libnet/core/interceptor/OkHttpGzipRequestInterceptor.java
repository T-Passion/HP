package com.passion.libnet.core.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

/**
 * Created by chaos
 * on 2017/11/16. 17:42
 * 文件描述：
 */

public class OkHttpGzipRequestInterceptor implements Interceptor {
    public OkHttpGzipRequestInterceptor() {
    }

    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (originalRequest.body() != null && originalRequest.header("Content-Encoding") == null) {
            Request compressedRequest = originalRequest.newBuilder().header("Content-Encoding", "gzip").header("Transfer-Encoding", "chunked").header("Content-Type", "text/plain; charset=utf-8").method(originalRequest.method(), this.gzip(originalRequest.body())).build();
            return chain.proceed(compressedRequest);
        } else {
            return chain.proceed(originalRequest);
        }
    }

    private RequestBody gzip(final RequestBody body) {
        return new RequestBody() {
            public MediaType contentType() {
                return MediaType.parse("text/plain; charset=utf-8");
            }

            public long contentLength() {
                return -1L;
            }

            public void writeTo(BufferedSink sink) throws IOException {
                BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                body.writeTo(gzipSink);
                gzipSink.close();
            }
        };
    }
}