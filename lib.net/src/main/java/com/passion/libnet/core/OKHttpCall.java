package com.passion.libnet.core;

import android.util.Log;

import com.passion.libnet.core.convert.Converter;
import com.passion.libnet.core.cookie.NetCookieJar;
import com.passion.libnet.core.exception.ConnectNetException;
import com.passion.libnet.core.exception.NetException;
import com.passion.libnet.core.exception.ServerNetException;
import com.passion.libnet.core.exception.TimeoutNetException;
import com.passion.libnet.core.factory.HttpSSLFactory;
import com.passion.libnet.core.imp.Callback;
import com.passion.libnet.core.imp.HttpCall;
import com.passion.libnet.core.imp.RawResponse;
import com.passion.libnet.core.interceptor.OKHttpGzipInterceptor;
import com.passion.libnet.core.interceptor.OKHttpLogInterceptor;
import com.passion.libnet.core.interceptor.OkHttpGzipRequestInterceptor;
import com.passion.libnet.core.interceptor.OkHttpMockServerInterceptor;
import com.passion.libnet.core.mock.HttpMock;
import com.passion.libnet.core.request.RequestParser;
import com.passion.libnet.core.utils.TrustAllHostnameVerifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;

import okhttp3.Call;
import okhttp3.Dns;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by chaos
 * on 2017/11/16. 14:31
 * 文件描述：
 */

public class OKHttpCall<T> implements HttpCall<T> {
    private static final Call.Factory CALL_FACTORY = new OkHttpClient();
    private static final OKHttpGzipInterceptor OKHTTP_GZIP_INTERCEPTOR = new OKHttpGzipInterceptor();
    private Call rawCall;
    private Network netWork;
    private RequestParser requestParser;
    private static Interceptor logInterceptor;

    public OKHttpCall(RequestParser requestParser, Network netWork) {
        this.netWork = netWork;
        this.requestParser = requestParser;
    }

    public ResponseModel<T> execute() throws IOException {
        Call call = this.rawCall = this.createRawCall();
        Response rawResponse = call.execute();
        return this.parseResponse(rawResponse);
    }

    public void execute(final Callback<T> callback) {
        Call call = this.rawCall = this.createRawCall();
        call.enqueue(new okhttp3.Callback() {
            public void onFailure(Call call, IOException e) {
                this.failure(e, (ResponseModel) null);
            }

            public void onResponse(Call call, Response response) throws IOException {
                ResponseModel networkResonse = null;

                try {
                    networkResonse = OKHttpCall.this.parseResponse(response);
                } catch (IOException var6) {
                    this.failure(var6, (ResponseModel) null);
                    return;
                }

                try {
                    callback.onResponse(networkResonse);
                    if (networkResonse.isSuccessful()) {
                        callback.onSuccess((T) networkResonse.data());
                    } else {
                        this.failure(new IOException(), networkResonse);
                    }
                } catch (Throwable var5) {
                    var5.printStackTrace();
                }

            }

            private void failure(Throwable t, ResponseModel responseModel) {
                Throwable exactly = t;
                if (t instanceof ConnectException) {
                    exactly = new ConnectNetException("网络出错，可能的原因是：您的网络不通！", t);
                } else if (t instanceof UnknownHostException) {
                    exactly = new ConnectNetException("网络出错，可能的原因是：您的网络不通！", t);
                } else if (t instanceof InterruptedIOException) {
                    exactly = new TimeoutNetException("服务器访问超时！", t);
                } else if (t instanceof MalformedURLException) {
                    exactly = new RuntimeException("Bad URL ", t);
                } else if (t instanceof IOException) {
                    if (responseModel != null) {
                        int statusCode = responseModel.httpStatusCode();
                        NetResponse networkResponse = new NetResponse(statusCode, responseModel.bytes());
                        if (statusCode != 401 && statusCode != 403) {
                            exactly = new ServerNetException("服务器访问出错，响应码" + statusCode, t, networkResponse);
                        } else {
                            exactly = new NetException("服务器访问受限！", t, networkResponse);
                        }
                    } else {
                        exactly = new NetException(t.getMessage(), t);
                    }

                    t.printStackTrace();
                }

                try {
                    callback.onFailure((Throwable) exactly);
                } catch (Throwable var6) {
                    var6.printStackTrace();
                }

            }
        });
    }

    public ResponseModel download(File file) throws IOException {
        if (file == null) {
            Log.w("Network", "Download file is null");
            return null;
        } else {
            Call call = this.rawCall = this.createRawCall();
            Response rawResponse = call.execute();
            return this.parseResponseToFile(rawResponse, file, (FileCallback) null);
        }
    }

    public void download(final File file, final FileCallback callback) {
        if (file == null) {
            Log.w("Network", "Download file is null");
        } else {
            Call call = this.rawCall = this.createRawCall();
            call.enqueue(new okhttp3.Callback() {
                public void onFailure(Call call, IOException e) {
                    try {
                        callback.onFailure(e);
                    } catch (Throwable var4) {
                        var4.printStackTrace();
                    }

                }

                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        ResponseModel<File> res = OKHttpCall.this.parseResponseToFile(response, file, callback);
                        callback.onResponse(res);
                        if (res.isSuccessful()) {
                            callback.onSuccess(file);
                        } else {
                            this.failure(new NetException("status code: " + res.httpStatusCode()));
                        }
                    } catch (IOException var4) {
                        this.failure(var4);
                    }

                }

                private void failure(Throwable e) {
                    try {
                        callback.onFailure(e);
                    } catch (Throwable var3) {
                        var3.printStackTrace();
                    }

                }
            });
        }
    }

    public void cancel(Object tag) {
        if (tag != null) {
            OkHttpClient client = (OkHttpClient) CALL_FACTORY;
            List<Call> queuedCalls = client.dispatcher().queuedCalls();
            Iterator var4 = queuedCalls.iterator();

            while (var4.hasNext()) {
                Call call = (Call) var4.next();
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }

            List<Call> runningCalls = client.dispatcher().runningCalls();
            Iterator var8 = runningCalls.iterator();

            while (var8.hasNext()) {
                Call call = (Call) var8.next();
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }

        }
    }

    private Call createRawCall() {
        OkHttpClient client = (OkHttpClient) CALL_FACTORY;
        OkHttpClient.Builder builder = client.newBuilder();
        boolean trustAllCerts = this.netWork.trustAllCerts();
        if (trustAllCerts) {
            builder.sslSocketFactory(HttpSSLFactory.createTrustAllSSLSocketFactory());
            builder.hostnameVerifier((HostnameVerifier) new TrustAllHostnameVerifier());
        }

        Dns dns = this.netWork.dns();
        if (dns != null) {
            builder.dns(new OKHttpCall.HttpDns(dns));
        }

        NetCookieJar cookieJar = this.netWork.getCookieJar();
        if (cookieJar != null) {
            builder.cookieJar(cookieJar);
        }

        boolean debug = this.netWork.isDebugMode();
        boolean gzipEncoding = this.requestParser.isGzipEncoding();
        if (gzipEncoding) {
            builder.addInterceptor(new OkHttpGzipRequestInterceptor());
        }

        if (debug) {
            builder.addInterceptor(this.getLogInterceptor());
        }

        long connectTimeout = this.requestParser.getConnectTimeoutMillis();
        if (connectTimeout > 0L) {
            builder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        }

        long readTimeout = this.requestParser.getReadTimeoutMillis();
        if (readTimeout > 0L && readTimeout != (long) client.readTimeoutMillis()) {
            builder.readTimeout(readTimeout, TimeUnit.MILLISECONDS);
        }

        long writeTimeout = this.requestParser.getWriteTimeoutMillis();
        if (writeTimeout > 0L && writeTimeout != (long) client.writeTimeoutMillis()) {
            builder.writeTimeout(writeTimeout, TimeUnit.MILLISECONDS);
        }

        builder.addNetworkInterceptor(OKHTTP_GZIP_INTERCEPTOR);
        if (HttpMock.isEnableMock() && debug) {
            builder.addInterceptor(new OkHttpMockServerInterceptor(this.requestParser.getConverter()));
        }

        client = builder.build();
        OKHttpRequestBuilder requestBuilder = new OKHttpRequestBuilder();
        this.requestParser.parseTo(requestBuilder);
        Call call = client.newCall(requestBuilder.build());
        if (call == null) {
            throw new NullPointerException("OkHttp Call.Factory returned null.");
        } else {
            return call;
        }
    }

    private ResponseModel parseResponse(Response rawResponse) throws IOException {
        ResponseBody rawBody = rawResponse.body();
        rawResponse = rawResponse.newBuilder().body((ResponseBody) null).build();
        int code = rawResponse.code();
        ResponseModel var5;
        if (code >= 200 && code < 300) {
            if (code != 204 && code != 205) {
                Type responseType = this.requestParser.getResponseType();

                try {
                    if (responseType == null || responseType == String.class || responseType == Object.class) {
                        var5 = ResponseModel.success(new String(rawBody.bytes()), new OKHttpCall.OKHttpRawResponse(rawResponse));
                        return var5;
                    }

                    if (responseType != byte[].class) {
                        ResponseModel var7;
                        if (responseType == InputStream.class) {
                            Buffer buffer = new Buffer();
                            rawBody.source().readAll(buffer);
                            ResponseBody bufferingBody = ResponseBody.create(rawBody.contentType(), rawBody.contentLength(), buffer);
                            var7 = ResponseModel.success(bufferingBody.byteStream(), new OKHttpCall.OKHttpRawResponse(rawResponse));
                            return var7;
                        }

                        if (responseType == Void.class) {
                            var5 = ResponseModel.success(new OKHttpCall.OKHttpRawResponse(rawResponse));
                            return var5;
                        }

                        Converter converter = this.requestParser.getConverter();
                        if (converter == null) {
                            throw new IllegalArgumentException("Could not find a converter for type " + responseType);
                        }

                        T body = converter.fromBody(rawBody.bytes(), responseType);
                        var7 = ResponseModel.success(body, new OKHttpCall.OKHttpRawResponse(rawResponse));
                        return var7;
                    }

                    var5 = ResponseModel.success(rawBody.bytes(), new OKHttpCall.OKHttpRawResponse(rawResponse));
                } catch (IllegalStateException var17) {
                    throw new IOException("Request interrupted!");
                } finally {
                    rawBody.close();
                }

                return var5;
            } else {
                return ResponseModel.success(new OKHttpCall.OKHttpRawResponse(rawResponse));
            }
        } else {
            try {
                RawResponse rawRes = new OKHttpCall.OKHttpRawResponse(rawResponse, rawBody.bytes());
                var5 = ResponseModel.error(rawRes);
                return var5;
            } catch (IllegalStateException var19) {
                var5 = ResponseModel.error(new OKHttpCall.OKHttpRawResponse(rawResponse, "Request interrupted!".getBytes()));
            } finally {
                rawBody.close();
            }

            return var5;
        }
    }

    private ResponseModel parseResponseToFile(Response rawResponse, File file, FileCallback callback) throws IOException {
        ResponseBody responseBody = rawResponse.body();
        rawResponse = rawResponse.newBuilder().body((ResponseBody) null).build();
        int code = rawResponse.code();
        if (code >= 200 && code < 300) {
            InputStream is = null;
            FileOutputStream fos = null;
            byte[] buf = new byte[2048];
            boolean var9 = false;

            try {
                fos = new FileOutputStream(file);
                is = responseBody.byteStream();
                long total = responseBody.contentLength();
                long sum = 0L;

                int len;
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                    sum += (long) len;
                    if (callback != null) {
                        int progress = (int) ((float) sum * 1.0F / (float) total * 100.0F);
                        callback.onProgress(sum, total, progress);
                    }
                }

                fos.flush();
                return ResponseModel.success(file, new OKHttpCall.OKHttpRawResponse(rawResponse));
            } catch (IllegalStateException var35) {
                throw new IOException("Request interrupted!");
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException var34) {
                    ;
                }

                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException var33) {
                    ;
                }

                if (responseBody != null) {
                    responseBody.close();
                }

            }
        } else {
            ResponseModel var7;
            try {
                RawResponse rawRes = new OKHttpCall.OKHttpRawResponse(rawResponse, responseBody.bytes());
                var7 = ResponseModel.error(rawRes);
                return var7;
            } catch (IllegalStateException var37) {
                var7 = ResponseModel.error(new OKHttpCall.OKHttpRawResponse(rawResponse, "Request interrupted!".getBytes()));
            } finally {
                responseBody.close();
            }

            return var7;
        }
    }

    private Interceptor getLogInterceptor() {
        if (logInterceptor == null) {
            Class var1 = OKHttpCall.class;
            synchronized (OKHttpCall.class) {
                if (logInterceptor == null) {
                    logInterceptor = new OKHttpLogInterceptor();
                }
            }
        }

        return logInterceptor;
    }

    public static void main(String[] args) {
        long cur = 121L;
        long total = 1233L;
        float progress = (float) cur * 1.0F / (float) total;
        System.out.println(progress);
    }

    static class OKHttpRawResponse implements RawResponse {
        final Response response;
        final byte[] bytes;

        OKHttpRawResponse(Response response) {
            this(response, (byte[]) null);
        }

        OKHttpRawResponse(Response response, byte[] bytes) {
            this.response = response;
            if (!response.isSuccessful()) {
                this.bytes = bytes;
            } else {
                this.bytes = null;
            }

        }

        public int code() {
            return this.response.code();
        }

        public String message() {
            return this.response.message();
        }

        public Map<String, List<String>> headers() {
            return this.response.headers().toMultimap();
        }

        public String header(String name) {
            return this.response.header(name);
        }

        public List<String> headers(String name) {
            return this.response.headers(name);
        }

        public boolean isSuccessful() {
            return this.response.isSuccessful();
        }

        public byte[] bytes() {
            if (this.isSuccessful()) {
                throw new IllegalStateException("bytes() is only usable in error response status.");
            } else {
                return this.bytes;
            }
        }

        public InputStream inputStream() {
            throw new UnsupportedOperationException("InputStream from inputStream has not yet be supported!");
        }

        public Reader charStream() {
            throw new UnsupportedOperationException("Reader from charStream has not yet be supported!");
        }
    }

    private static class HttpDns implements okhttp3.Dns {
        final Dns dns;

        HttpDns(Dns dns) {
            this.dns = dns;
        }

        public List<InetAddress> lookup(String hostname) throws UnknownHostException {
            return this.dns.lookup(hostname);
        }
    }
}
