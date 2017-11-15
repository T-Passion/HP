package com.passion.libnet.api;

import com.passion.libnet.core.cookie.CookieCache;
import com.passion.libnet.core.cookie.CookiePolicy;
import com.passion.libnet.core.cookie.DefaultCookiePolicy;
import com.passion.libnet.core.cookie.NetCookieJar;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by chaos
 * on 2017/11/15. 23:10
 * 文件描述：
 */

public class DefaultNetCookieJar implements NetCookieJar {
    private CookieCache cookieCache;
    private CookiePolicy cookiePolicy;



    public DefaultNetCookieJar(CookieCache cookieCache) {
        this(cookieCache, new DefaultCookiePolicy());
    }

    public DefaultNetCookieJar(CookieCache cookieCache, CookiePolicy cookiePolicy) {
        if(cookieCache == null) {
            throw new IllegalArgumentException("cookieCache is null");
        } else {
            this.cookieCache = cookieCache;
            if(cookiePolicy == null) {
                this.cookiePolicy = new DefaultCookiePolicy();
            } else {
                this.cookiePolicy = cookiePolicy;
            }

        }
    }

    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        this.cookieCache.add(url, cookies);
    }

    public List<Cookie> loadForRequest(HttpUrl url) {
        return this.cookieCache.get(url, this.cookiePolicy);
    }

    @Override
    public void clear() {

    }

}
