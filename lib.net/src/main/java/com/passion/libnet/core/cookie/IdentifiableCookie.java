package com.passion.libnet.core.cookie;

import okhttp3.Cookie;

/**
 * Created by chaos
 * on 2017/11/15. 23:14
 * 文件描述：
 */

final class IdentifiableCookie {

    private final Cookie cookie;

    IdentifiableCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    Cookie getCookie() {
        return this.cookie;
    }

    public boolean equals(Object other) {
        if (!(other instanceof IdentifiableCookie)) {
            return false;
        } else {
            IdentifiableCookie that = (IdentifiableCookie) other;
            return that.cookie.name().equals(this.cookie.name())
                    && that.cookie.domain().equals(this.cookie.domain())
                    && that.cookie.path().equals(this.cookie.path())
                    && that.cookie.secure() == this.cookie.secure()
                    && that.cookie.hostOnly() == this.cookie.hostOnly();
        }
    }

    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + this.cookie.name().hashCode();
        hash = 31 * hash + this.cookie.domain().hashCode();
        hash = 31 * hash + this.cookie.path().hashCode();
        hash = 31 * hash + (this.cookie.secure() ? 1 : 0);
        hash = 31 * hash + (this.cookie.hostOnly() ? 1 : 0);
        return hash;
    }
}
