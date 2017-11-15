package com.passion.libnet.core.cookie;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by chaos
 * on 2017/11/15. 23:17
 * 文件描述：
 */

public abstract class CookiePolicy {

    public CookiePolicy() {
    }

    protected abstract boolean matches(Cookie cookie, HttpUrl httpUrl);
}
