package com.passion.libnet.core.cookie;

import java.util.Collection;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by chaos
 * on 2017/11/15. 23:12
 * 文件描述：
 */

public interface CookieCache {

    List<Cookie> get(HttpUrl httpUrl, CookiePolicy policy);

    List<Cookie> getAll();

    void add(HttpUrl httpUrl, List<Cookie> cookies);

    void remove(Collection<Cookie> cookies);

    void clear();

}
