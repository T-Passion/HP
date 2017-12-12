package com.passion.libbase;

/**
 * Created by chaos
 * on 2017/12/6. 13:44
 * 文件描述：全局性的变量
 */

public final class AppEnv {

    private static HPApplication sAppContext;
    private static String sBuildType;

    private static String NET_HOST = "";
    private static String NET_HOST_DEBUG = "";
    private static String NET_HOST_RELEASE = "";
    private static String sApiSecret = "dhMda9daJiw5qe1oa3sah9de2";


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
