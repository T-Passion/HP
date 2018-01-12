package com.passion.libbase;

/**
 * Created by chaos
 * on 2017/12/6. 13:44
 * 文件描述：全局性的变量
 */

public final class AppEnv {

    private static final String sApiSecret = "dhMda9daJiw5qe1oa3sah9de2";
    private static String sBuildType;
    private static HPApplication sAppContext;

    private static String NET_HOST = "https://games.mobileapi.hupu.com/1/7.1.13";
    private static String NET_HOST_DEBUG = "https://games.mobileapi.hupu.com/1/7.1.13";
    private static String NET_HOST_RELEASE = "https://games.mobileapi.hupu.com/1/7.1.13";


    private AppEnv() {
    }

    public static void init(HPApplication app, String buildType) {
        sAppContext = app;
        sBuildType = buildType;
    }

    public static HPApplication app() {
        return sAppContext;
    }

    public static String netHost() {
        if (inDebug()) {
            NET_HOST = NET_HOST_DEBUG;
        } else if (inRelease()) {
            NET_HOST = NET_HOST_RELEASE;
        } else {
            NET_HOST = NET_HOST_DEBUG;
        }
        return NET_HOST;
    }

    private static boolean inDebug() {
        return (sBuildType != null && sBuildType.equals("debug"));
    }

    private static boolean inRelease() {
        return (sBuildType != null && sBuildType.equals("release"));
    }


    public static String getApiSecret() {
        return sApiSecret;
    }
}
