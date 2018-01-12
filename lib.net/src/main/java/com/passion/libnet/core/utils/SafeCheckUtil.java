package com.passion.libnet.core.utils;

/**
 * Created by chaos
 * on 2018/1/9. 15:20
 * 文件描述：
 */

public class SafeCheckUtil {


    public static <T> boolean isNull(T t) {
        if (t == null) {
            return true;
        }
        return false;
    }

}
