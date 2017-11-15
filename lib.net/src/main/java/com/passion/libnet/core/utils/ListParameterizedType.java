package com.passion.libnet.core.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by chaos
 * on 2017/11/15. 22:25
 * 文件描述：
 */

public class ListParameterizedType implements ParameterizedType {

    private Type type;

    public ListParameterizedType(Type type) {
        this.type = type;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[]{this.type};
    }

    @Override
    public Type getRawType() {
        return List.class;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}
