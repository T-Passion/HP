package com.passion.libnet.core.utils;

/**
 * Created by chaos
 * on 2018/1/9. 15:20
 * 文件描述：
 */

public class SafeCheckUtil {


    /**
     *
     * @param t  参数
     * @param <T> 类型
     * @return 是否null
     */
    public static <T> boolean isNull(T t) {
        if (t == null) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param t  参数
     * @param <T> 类型
     * @return 不为null true
     */
    public static <T> boolean nonNull(T t) {
        if (t != null) {
            return true;
        }
        return false;
    }


}
