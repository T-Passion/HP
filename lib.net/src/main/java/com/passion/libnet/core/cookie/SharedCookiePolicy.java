package com.passion.libnet.core.cookie;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by chaos
 * on 2017/11/15. 23:19
 * 文件描述：
 */

public class SharedCookiePolicy extends CookiePolicy {


    private Set<String> sharedHosts;

    public SharedCookiePolicy(String... hosts) {
        if (hosts != null) {
            this.sharedHosts = new HashSet<>();
            String[] var2 = hosts;
            int var3 = hosts.length;

            for (int i = 0; i < var3; ++i) {
                String host = var2[i];
                HttpUrl httpUrl = HttpUrl.parse(host);
                if (httpUrl != null) {
                    this.sharedHosts.add(httpUrl.host());
                } else {
                    this.sharedHosts.add(host);
                }
            }
        }

    }

    protected boolean matches(Cookie cookie, HttpUrl httpUrl) {
        if (this.sharedHosts == null) {
            return false;
        } else {
            String urlHost = httpUrl.host();
            if (!this.sharedHosts.contains(urlHost)) {
                return false;
            } else {
                String cookieDomain = cookie.domain();
                Iterator var5 = this.sharedHosts.iterator();

                String sharedHost;
                do {
                    if (!var5.hasNext()) {
                        return false;
                    }

                    sharedHost = (String) var5.next();
                } while (!sharedHost.endsWith(cookieDomain));

                return true;
            }
        }
    }
}
