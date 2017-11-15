package com.passion.libnet.core.utils;

import java.io.File;

/**
 * Created by chaos
 * on 2017/11/15. 22:55
 * 文件描述：
 */

public class Part {
    public final String name;
    public final Object value;
    public final String fileName;

    private Part(String name, Object value, String fileName) {
        this.name = name;
        this.value = value;
        this.fileName = fileName;
    }

    public static Part create(String name, String value) {
        return new Part(name, value, (String)null);
    }

    public static Part create(String name, File file, String fileName) {
        return new Part(name, file, fileName);
    }

    public static Part create(String name, byte[] bytes, String fileName) {
        return new Part(name, bytes, fileName);
    }
}
