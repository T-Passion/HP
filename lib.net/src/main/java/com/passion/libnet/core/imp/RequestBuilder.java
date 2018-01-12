package com.passion.libnet.core.imp;

/**
 * Created by chaos
 * on 2017/11/16. 16:40
 * 文件描述：
 */

public interface RequestBuilder<T> {

    void addHeader(String var1, String var2);

    void addQueryParam(String var1, String var2);

    void addFormField(String var1, String var2);

    void addPart(String var1, Object var2, String var3);

    void setBodyData(String contentType,byte[] body);

    T build();
}
