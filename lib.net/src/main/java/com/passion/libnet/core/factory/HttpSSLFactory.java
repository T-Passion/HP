package com.passion.libnet.core.factory;

import com.passion.libnet.core.utils.TrustAllCerts;

import java.security.SecureRandom;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * Created by chaos
 * on 2017/11/16. 17:38
 * 文件描述：
 */

public class HttpSSLFactory {

    public static SSLSocketFactory createTrustAllSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init((KeyManager[])null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return ssfFactory;
    }
}
