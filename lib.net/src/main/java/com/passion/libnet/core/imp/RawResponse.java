package com.passion.libnet.core.imp;

import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Map;

/**
 * Created by chaos
 * on 2017/11/16. 14:28
 * 文件描述：
 */

public interface RawResponse {


    int code();

    String message();

    Map<String, List<String>> headers();

    String header(String var1);

    List<String> headers(String var1);

    boolean isSuccessful();

    byte[] bytes();

    InputStream inputStream();

    Reader charStream();
}
