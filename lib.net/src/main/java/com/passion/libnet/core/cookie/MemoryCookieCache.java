package com.passion.libnet.core.cookie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by chaos
 * on 2017/11/15. 23:13
 * 文件描述：
 */

public class MemoryCookieCache implements CookieCache {

    private Set<IdentifiableCookie> cookies = Collections.newSetFromMap(new ConcurrentHashMap());

    public MemoryCookieCache() {
    }
    @Override
    public List<Cookie> get(HttpUrl httpUrl, CookiePolicy cookiePolicy) {
        List<Cookie> validCookies = new ArrayList();
        Iterator it = this.cookies.iterator();

        while (true) {
            while (it.hasNext()) {
                Cookie cookie = ((IdentifiableCookie) it.next()).getCookie();
                if (isExpired(cookie)) {
                    it.remove();
                } else if (cookiePolicy.matches(cookie, httpUrl) || cookie.matches(httpUrl)) {
                    validCookies.add(cookie);
                }
            }

            return validCookies;
        }
    }
    @Override
    public List<Cookie> getAll() {
        List<Cookie> validCookies = new ArrayList();
        Iterator it = this.cookies.iterator();

        while (it.hasNext()) {
            Cookie cookie = ((IdentifiableCookie) it.next()).getCookie();
            if (isExpired(cookie)) {
                it.remove();
            } else {
                validCookies.add(cookie);
            }
        }

        return validCookies;
    }
    @Override
    public void add(HttpUrl httpUrl, List<Cookie> cookies) {
        if (cookies != null) {
            Set<IdentifiableCookie> identifiableCookies = new HashSet(cookies.size());
            Iterator var4 = cookies.iterator();

            while (var4.hasNext()) {
                Cookie cookie = (Cookie) var4.next();
                identifiableCookies.add(new IdentifiableCookie(cookie));
            }

            this.cookies.removeAll(identifiableCookies);
            this.cookies.addAll(identifiableCookies);
        }
    }
    @Override
    public void remove(Collection<Cookie> cookies) {
        Set<IdentifiableCookie> identifiableCookies = new HashSet(cookies.size());
        Iterator var3 = cookies.iterator();

        while (var3.hasNext()) {
            Cookie cookie = (Cookie) var3.next();
            identifiableCookies.add(new IdentifiableCookie(cookie));
        }

        this.cookies.removeAll(identifiableCookies);
    }
    @Override
    public void clear() {
        this.cookies.clear();
    }

    private static boolean isExpired(Cookie cookie) {
        return cookie.expiresAt() < System.currentTimeMillis();
    }
}
