package com.passion.libbase.utils;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by huangdou
 * on 2017/10/10.
 * <p>
 * 自动以日志工具,方便以后Log包的替换.
 * Logger is a wrapper of {@link Logger}
 * But more pretty, simple and powerful
 */

public final class LogUtils {
    /**
     * It is used to change the tag
     *
     * @param tag is the given string which will be used in Logger
     */
    public static void init(String tag, boolean showLog) {
        Logger.init(tag).setMethodCount(5).setLogLevel(showLog ? LogLevel.FULL : LogLevel.NONE);
    }


    public static void d(String message) {
        Logger.d(message);
    }

    public static void d(String tag, String message) {
        Logger.d(tag, message);
    }


    public static void e(String message) {
        Logger.e(message);
    }

    public static void e(String tag, String message) {
        Logger.e(tag, message);
    }

    public static void e(Exception e) {
        Logger.e(e);
    }

    public static void e(String tag, Exception e) {
        Logger.e(tag, e);
    }


    public static void e(String tag, String message, Exception e) {
        Logger.e(tag, message, e);
    }


    public static void w(String message) {
        Logger.w(message);
    }

    public static void w(String tag, String message) {
        Logger.w(tag, message);
    }

    public static void w(String message, int methodCount) {
        Logger.w(message, methodCount);
    }

    public static void w(String tag, String message, int methodCount) {
        Logger.w(tag, message, methodCount);
    }

    public static void i(String message) {
        Logger.i(message);
    }

    public static void i(String tag, String message) {
        Logger.i(tag, message);
    }

    public static void i(String message, int methodCount) {
        Logger.i(message, methodCount);
    }

    public static void i(String tag, String message, int methodCount) {
        Logger.i(tag, message, methodCount);
    }

    public static void v(String message) {
        Logger.v(message);
    }

    public static void v(String tag, String message) {
        Logger.v(tag, message);
    }

    public static void v(String message, int methodCount) {
        Logger.v(message, methodCount);
    }

    public static void v(String tag, String message, int methodCount) {
        Logger.v(tag, message, methodCount);
    }

    public static void wtf(String message) {
        Logger.wtf(message);
    }

    public static void wtf(String tag, String message) {
        Logger.wtf(tag, message);
    }

    public static void wtf(String message, int methodCount) {
        Logger.wtf(message, methodCount);
    }

    public static void wtf(String tag, String message, int methodCount) {
        Logger.wtf(tag, message, methodCount);
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        Logger.json(json);
    }

    public static void json(String tag, String json) {
        Logger.json(tag, json);
    }


}
