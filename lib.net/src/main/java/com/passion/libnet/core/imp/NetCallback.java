package com.passion.libnet.core.imp;

import com.passion.libnet.core.exception.ErrorBody;

import java.lang.reflect.Type;

/**
 * Created by chaos
 * on 2017/11/15. 15:38
 * 文件描述：
 */

public interface NetCallback<T> {

    /**
     * 成功
     * @param result 请求结果实体
     */
    void onSuccess(T result);


    /**
     * 失败
     * @param error 错误
     */
    void onFailure(ErrorBody error);


    Type getResponseType();


}
