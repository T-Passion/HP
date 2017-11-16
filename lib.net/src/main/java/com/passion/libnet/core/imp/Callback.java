package com.passion.libnet.core.imp;

import com.passion.libnet.core.ResponseModel;

import java.lang.reflect.Type;

/**
 * Created by chaos
 * on 2017/11/15. 15:38
 * 文件描述：
 */

public abstract class Callback<T> {

    /**
     * 响应
     *
     * @param responseModel 响应model
     */
    public abstract void onResponse(ResponseModel<T> responseModel);

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
    public abstract void onFailure(Throwable throwable);

    public Type getResponseType() {
        return null;
    }

}
