package com.passion.libnet.core.interceptor;

import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by chaos
 * on 2017/11/16. 18:18
 * 文件描述：
 */

public class OKHttpLogInterceptor implements Interceptor {
    private static final String TAG = "Network";
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private OKHttpLogInterceptor() {
    }

    private static final class Holder{
        static final OKHttpLogInterceptor LOG_INTERCEPTOR = new OKHttpLogInterceptor();
    }

    public static OKHttpLogInterceptor get(){
        return Holder.LOG_INTERCEPTOR;
    }

    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Connection connection = chain.connection();
        Protocol protocol = connection != null?connection.protocol(): Protocol.HTTP_1_1;
        Log.d("Network", String.format("--> %s %s on %s%n%s", new Object[]{request.method(), request.url(), protocol, request.headers()}));
        this.logRequest(request);
        long t1 = System.nanoTime();
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        Log.d("Network", String.format("<-- %s %s %s in %.1fms%n%s", new Object[]{Integer.valueOf(response.code()), response.message(), response.request().url(), Double.valueOf((double)(t2 - t1) / 1000000.0D), response.headers()}));
        return this.logResponse(response);
    }

    private void logRequest(Request request) {
        try {
            RequestBody requestBody = request.body();
            if(requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if(mediaType != null) {
                    Log.d("Network", "Content-Type: " + mediaType.toString());
                }

                String contentType = request.header("Content-Type");
                if(mediaType != null && mediaType.toString().contains("multipart") || contentType != null && contentType.contains("multipart")) {
                    Log.d("Network", "--> END " + request.method() + " (binary " + requestBody.contentLength() + "-bytes body omitted)");
                } else if(this.bodyEncoded(request.headers())) {
                    Log.d("Network", "--> END " + request.method() + " (encoded body omitted)");
                } else {
                    Buffer buffer = new Buffer();
                    request.body().writeTo(buffer);
                    Log.d("Network", buffer.readUtf8());
                    Log.d("Network", "--> END " + request.method() + " (" + requestBody.contentLength() + "-bytes body)");
                }
            }
        } catch (Exception var6) {
            ;
        }

    }

    private Response logResponse(Response response) {
        try {
            ResponseBody responseBody = response.body();
            if(responseBody == null) {
                Log.d("Network", "<-- END HTTP");
            } else if(this.bodyEncoded(response.headers())) {
                Log.d("Network", "<-- END HTTP (encoded body omitted)");
            } else {
                MediaType mediaType = responseBody.contentType();
                BufferedSource bufferedSource = responseBody.source();
                bufferedSource.request(9223372036854775807L);
                Buffer buffer = bufferedSource.buffer();
                boolean printable = this.printable(mediaType);
                if(!printable) {
                    String contentLength = response.header("Content-Length");
                    if(contentLength != null && Long.parseLong(contentLength) < 1024L) {
                        printable = true;
                    }
                }

                if(!printable) {
                    printable = isPlaintext(buffer);
                }

                if(printable) {
                    Charset charset = UTF8;
                    if(mediaType != null) {
                        charset = mediaType.charset(UTF8);
                    }

                    Log.d("Network", buffer.clone().readString(charset));
                    Log.d("Network", "<-- END HTTP (" + buffer.size() + "-bytes body)");
                } else {
                    Log.d("Network", "<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                }
            }
        } catch (Exception var8) {
            ;
        }

        return response;
    }

    private boolean printable(MediaType mediaType) {
        if("text".equals(mediaType.type())) {
            return true;
        } else {
            String subtype = mediaType.subtype();
            return subtype != null && (subtype.equals("json") || subtype.equals("xml") || subtype.equals("html"));
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64L?buffer.size():64L;
            buffer.copyTo(prefix, 0L, byteCount);

            for(int i = 0; i < 16 && !prefix.exhausted(); ++i) {
                int codePoint = prefix.readUtf8CodePoint();
                if(Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }

            return true;
        } catch (EOFException var6) {
            return false;
        }
    }
}
