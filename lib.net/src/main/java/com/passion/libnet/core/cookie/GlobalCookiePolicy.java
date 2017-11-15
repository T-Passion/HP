package com.passion.libnet.core.cookie;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by chaos
 * on 2017/11/15. 23:18
 * 文件描述：
 */

public class GlobalCookiePolicy extends CookiePolicy {


    public GlobalCookiePolicy() {
    }

    protected boolean matches(Cookie cookie, HttpUrl httpUrl) {
        return true;
    }
}