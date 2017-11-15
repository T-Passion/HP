package com.passion.libnet.core.cookie;

import okhttp3.CookieJar;

/**
 * Created by chaos
 * on 2017/11/15. 23:08
 * 文件描述：
 */

public interface NetCookieJar extends CookieJar {

    void clear();
}
