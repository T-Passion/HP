package com.passion.libnet.api;


import com.passion.libnet.core.imp.NetCallback;
import com.passion.libnet.core.utils.ReflectUtil;

import java.lang.reflect.Type;

/**
 * Created by chaos
 * on 2018/1/9. 17:05
 * 文件描述：
 */

public abstract class HPRestCallback<T> implements NetCallback<T> {

    @Override
    public Type getResponseType() {
        return ReflectUtil.getResponseTypeClosely(this.getClass());
    }
}
