package com.passion.libnet.core;

import android.content.Context;

import com.passion.libnet.core.convert.Converter;
import com.passion.libnet.core.cookie.NetCookieJar;
import com.passion.libnet.core.imp.ApiService;
import com.passion.libnet.core.imp.INetWorker;
import com.passion.libnet.core.imp.RequestInterceptor;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Dns;

/**
 * Created by chaos
 * on 2017/11/15. 22:07
 * 文件描述：
 */

public final class NetConfig {
    private final Context appContext;
    private final boolean debugMode;
    private final boolean enableCookie;
    private final boolean enableDefaultSign;
    private final boolean trustAllCerts;
    private final int dnsMode;
    private final Dns dns;
    private final String hostUrl;
    private final String appSecret;
    private final RequestInterceptor[] requestInterceptors;
    private final Converter jsonConverter;
    private final NetCookieJar cookieJar;
    public Map<String, INetWorker> networkMap = new LinkedHashMap<>();
    private final ApiService apiService;

    private NetConfig(NetConfig.Builder builder) {
        this.debugMode = builder.mDebugMode;
        this.appContext = builder.appContext;
        this.enableCookie = builder.mEnableCookie;
        this.enableDefaultSign = builder.mEnableDefaultSign;
        this.trustAllCerts = builder.trustAllCerts;
        this.dnsMode = builder.dnsMode;
        this.dns = builder.dns;
        this.hostUrl = builder.hostUrl;
        this.appSecret = builder.mAppSecret;
        this.requestInterceptors = builder.mRequestInterceptors;
        this.jsonConverter = builder.mJsonConverter;
        this.cookieJar = builder.cookieJar;
        this.apiService = builder.apiService;
    }

    public Context getAppContext() {
        return appContext;
    }

    public ApiService getApiService() {
        return apiService;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public boolean isEnableCookie() {
        return enableCookie;
    }

    public boolean isEnableDefaultSign() {
        return enableDefaultSign;
    }

    public boolean isTrustAllCerts() {
        return trustAllCerts;
    }

    public int getDnsMode() {
        return dnsMode;
    }

    public Dns getDns() {
        return dns;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public RequestInterceptor[] getRequestInterceptors() {
        return requestInterceptors;
    }

    public Converter getJsonConverter() {
        return jsonConverter;
    }

    public NetCookieJar getCookieJar() {
        return cookieJar;
    }

    public Map<String, INetWorker> getNetworkMap() {
        return networkMap;
    }

    public static class Builder {
        private Context appContext;
        private boolean mDebugMode;
        private boolean mEnableCookie;
        private boolean mEnableDefaultSign = true;
        private int dnsMode;
        private Dns dns;
        private String hostUrl;
        private String mAppSecret;
        private RequestInterceptor[] mRequestInterceptors;
        private Converter mJsonConverter;
        private NetCookieJar cookieJar;
        private boolean trustAllCerts;
        private Map<String, INetWorker> networkMap = new LinkedHashMap<>();
        private ApiService apiService;


        public Builder() {
        }

        public NetConfig.Builder setAppContext(Context appContext) {
            this.appContext = appContext;
            return this;
        }

        public NetConfig.Builder setDebugMode(boolean debugMode) {
            this.mDebugMode = debugMode;
            return this;
        }

        public NetConfig.Builder setCookieEnable(boolean enableCookie) {
            this.mEnableCookie = enableCookie;
            return this;
        }

        public NetConfig.Builder trustAllCerts(boolean trustAllCerts) {
            this.trustAllCerts = trustAllCerts;
            return this;
        }

        public NetConfig.Builder setSignAutomatic(boolean signAutomatic) {
            this.mEnableDefaultSign = signAutomatic;
            return this;
        }

        public NetConfig.Builder setAppSecret(String appSecret) {
            this.mAppSecret = appSecret;
            return this;
        }

        public NetConfig.Builder setDnsMode(int dnsMode) {
            this.dnsMode = dnsMode;
            return this;
        }

        public NetConfig.Builder setDns(Dns dns) {
            this.dns = dns;
            return this;
        }

        public NetConfig.Builder setHostUrl(String hostUrl) {
            this.hostUrl = hostUrl;
            return this;
        }


        public NetConfig.Builder setRequestInterceptors(RequestInterceptor[] requestInterceptors) {
            this.mRequestInterceptors = requestInterceptors;
            return this;
        }

        public NetConfig.Builder setJsonConverter(Converter jsonConverter) {
            this.mJsonConverter = jsonConverter;
            return this;
        }

        public NetConfig.Builder setCookieJar(NetCookieJar cookieJar) {
            this.cookieJar = cookieJar;
            return this;
        }

        public NetConfig.Builder setApiService(ApiService apiService) {
            this.apiService = apiService;
            return this;
        }

        public NetConfig.Builder addNetWorker(String tag, INetWorker worker) {
            if (networkMap.containsKey(tag)) {
                return this;
            }
            networkMap.put(tag, worker);
            return this;
        }


        public NetConfig build() {
            return new NetConfig(this);
        }
    }
}
