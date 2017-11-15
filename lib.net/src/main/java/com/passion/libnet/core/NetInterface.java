package com.passion.libnet.core;

import java.lang.reflect.Type;

/**
 * Created by chaos
 * on 2017/11/15. 15:39
 * 文件描述：
 */

public interface NetInterface<T> {

    void onResponse(RequestModel<T> requestModel);

    void onSuccess(T result);

    void onFail(Throwable throwable);

    Type getResponseType();

}
