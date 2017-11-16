package com.passion.libnet.core.factory;

import android.net.Network;

import com.passion.libnet.core.convert.Converter;
import com.passion.libnet.core.cookie.NetCookieJar;
import com.passion.libnet.core.request.RequestInterceptor;

import okhttp3.Dns;

/**
 * Created by chaos
 * on 2017/11/16. 14:06
 * 文件描述：
 */

public class DefaultNetFactory implements NetFactory{
    private Network network;

    public DefaultNetFactory(boolean debugMode, Converter jsonConverter, RequestInterceptor... requestInterceptors) {
        this(debugMode, jsonConverter, (Dns)null, requestInterceptors);
    }

    public DefaultNetFactory(boolean debugMode, Converter jsonConverter, Dns httpdns, RequestInterceptor... requestInterceptors) {
        this(debugMode, jsonConverter, httpdns, (NetCookieJar)null, false, requestInterceptors);
    }

    public DefaultNetFactory(boolean debugMode, Converter jsonConverter, Dns httpdns, NetCookieJar cookieJar, boolean trustAllCerts, RequestInterceptor... requestInterceptors) {
        com.passion.libnet.core.Network.Builder builder = new com.passion.libnet.core.Network.Builder();
        if(requestInterceptors != null && requestInterceptors.length > 0) {
            RequestInterceptor[] var8 = requestInterceptors;
            int var9 = requestInterceptors.length;

            for(int var10 = 0; var10 < var9; ++var10) {
                RequestInterceptor requestInterceptor = var8[var10];
                builder.addInterceptor(requestInterceptor);
            }
        }

        if(jsonConverter != null) {
            builder.converter(jsonConverter);
        }

        if(httpdns != null) {
            builder.dns(httpdns);
        }

        if(cookieJar != null) {
            builder.setCookieJar(cookieJar);
        }

        builder.trustAllCerts(trustAllCerts);
        builder.debugMode(debugMode);
        this.network = builder.build();
    }

    public Network getNetwork(String url) {
        return this.network;
    }
}
