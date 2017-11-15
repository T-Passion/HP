package com.passion.libnet.core;

import java.lang.reflect.Type;

/**
 * Created by chaos
 * on 2017/11/15. 15:38
 * 文件描述：
 */

public abstract class Callback<T> {

    /**
     * 请求响应
     *
     * @param requestModel 请求model
     */
    public abstract void onResponse(RequestModel<T> requestModel);

    /**
     * 成功
     *
     * @param result 请求结果实体
     */
    public abstract void onSuccess(T result);

    /**
     * 失败
     *
     * @param throwable 异常信息
     */
    public abstract void onFail(Throwable throwable);

    protected Type getResponseType() {
        return null;
    }

}
