package com.passion.libnet.core.utils;

import javax.net.ssl.SSLSession;

/**
 * Created by chaos
 * on 2017/11/16. 17:38
 * 文件描述：
 */

public class TrustAllHostnameVerifier {
    public TrustAllHostnameVerifier() {
    }

    public boolean verify(String s, SSLSession sslSession) {
        return true;
    }
}
