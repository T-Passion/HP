package com.passion.libnet.api;

import com.passion.libnet.core.convert.Converter;
import com.passion.libnet.core.cookie.NetCookieJar;
import com.passion.libnet.core.request.RequestInterceptor;

/**
 * Created by chaos
 * on 2017/11/15. 22:07
 * 文件描述：
 */

public final class NetConfig {
    final boolean debugMode;
    final boolean enableDns;
    final boolean enableCookie;
    final boolean enableDefaultSign;
    final boolean trustAllCerts;
    final int dnsMode;
    final String dnsKey;
    final String appSecret;
    final String[] dnsPreloadHosts;
    final RequestInterceptor[] requestInterceptors;
    final Converter jsonConverter;
    final NetCookieJar cookieJar;

    private NetConfig(NetConfig.Builder builder) {
        this.debugMode = builder.mDebugMode;
        this.enableDns = builder.mEnableDns;
        this.enableCookie = builder.mEnableCookie;
        this.enableDefaultSign = builder.mEnableDefaultSign;
        this.trustAllCerts = builder.trustAllCerts;
        this.dnsMode = builder.dnsMode;
        this.dnsKey = builder.mDnsKey;
        this.appSecret = builder.mAppSecret;
        this.dnsPreloadHosts = builder.mDnsPreloadHosts;
        this.requestInterceptors = builder.mRequestInterceptors;
        this.jsonConverter = builder.mJsonConverter;
        this.cookieJar = builder.cookieJar;
    }

    NetConfig.Builder newBuilder() {
        NetConfig.Builder builder = new NetConfig.Builder();
        builder.mDebugMode = this.debugMode;
        builder.mEnableDns = this.enableDns;
        builder.mEnableCookie = this.enableCookie;
        builder.mEnableDefaultSign = this.enableDefaultSign;
        builder.trustAllCerts = this.trustAllCerts;
        builder.dnsMode = this.dnsMode;
        builder.mDnsKey = this.dnsKey;
        builder.mAppSecret = this.appSecret;
        builder.mDnsPreloadHosts = this.dnsPreloadHosts;
        builder.mRequestInterceptors = this.requestInterceptors;
        builder.mJsonConverter = this.jsonConverter;
        builder.cookieJar = this.cookieJar;
        return builder;
    }

    public static NetConfig.Builder builder() {
        return new NetConfig.Builder();
    }

    public static class Builder {
        private boolean mDebugMode;
        private boolean mEnableDns;
        private boolean mEnableCookie;
        private boolean mEnableDefaultSign = true;
        private int dnsMode;
        private String mDnsKey;
        private String mAppSecret;
        private String[] mDnsPreloadHosts;
        private RequestInterceptor[] mRequestInterceptors;
        private Converter mJsonConverter;
        private NetCookieJar cookieJar;
        private boolean trustAllCerts;

        public Builder() {
        }

        public NetConfig.Builder setDebugMode(boolean debugMode) {
            this.mDebugMode = debugMode;
            return this;
        }

        public NetConfig.Builder setDnsEnable(boolean enableDns) {
            this.mEnableDns = enableDns;
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

        public NetConfig.Builder setDnsKey(String dnsKey) {
            this.mDnsKey = dnsKey;
            return this;
        }

        public NetConfig.Builder setDnsPreloadHosts(String... dnsPreloadHosts) {
            this.mDnsPreloadHosts = dnsPreloadHosts;
            return this;
        }

        public NetConfig.Builder setRequestInterceptors(RequestInterceptor... requestInterceptors) {
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

        public NetConfig build() {
            if(this.mEnableDns && this.mDnsKey == null) {
                throw new IllegalArgumentException("开始DNS，必须设置dns key。");
            } else {
                return new NetConfig(this);
            }
        }
    }
}
