package com.passion.libnet.core.mapper;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

/**
 * Created by chaos
 * on 2017/11/15. 22:22
 * 文件描述：
 */

public abstract class JavaTypeToken<T> {
    public JavaTypeToken() {
    }

    public Type getType() {
        ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
        Type[] types = type.getActualTypeArguments();
        Type paramType = types[0];
        return paramType instanceof WildcardType ?((WildcardType)paramType).getUpperBounds()[0]:paramType;
    }

    public static Type getParameterizedType(Type rawType, Type... typeArguments) {
        TypeToken<?> typeToken = TypeToken.getParameterized(rawType, typeArguments);
        return typeToken.getType();
    }
}
