package com.passion.libnet.core.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

/**
 * Created by chaos
 * on 2018/1/12. 15:18
 * 文件描述：
 */

public final class ReflectUtil {

    /**
     * 获取类文件上范型的具体类型
     *
     * @param cls 类文件
     * @return
     */
    public static Type getResponseTypeClosely(Class cls) {
        Class realClass = cls;

        for (Class superClass = cls.getSuperclass(); superClass != null && superClass.getGenericSuperclass() instanceof ParameterizedType; superClass = superClass.getSuperclass()) {
            realClass = realClass.getSuperclass();
        }

        Type type = realClass.getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalArgumentException("GenericSuperclass must be parameterized as Callback<Foo> or Callback<? extends Foo>");
        } else {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] types = parameterizedType.getActualTypeArguments();
            Type paramType = types[0];
            return paramType instanceof TypeVariable ? getResponseType(cls.getGenericSuperclass()) : (paramType instanceof WildcardType ? ((WildcardType) paramType).getUpperBounds()[0] : paramType);
        }
    }


    static Type getResponseType(Type returnType) {
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Parameter returnType must be parameterized as Callback<Foo> or Callback<? extends Foo>");
        } else {
            return getParameterUpperBound(0, (ParameterizedType) returnType);
        }
    }

    static Type getParameterUpperBound(int index, ParameterizedType type) {
        Type[] types = type.getActualTypeArguments();
        if (index >= 0 && index < types.length) {
            Type paramType = types[index];
            return paramType instanceof WildcardType ? ((WildcardType) paramType).getUpperBounds()[0] : paramType;
        } else {
            throw new IllegalArgumentException("Index " + index + " out of range [0," + types.length + ") for " + type);
        }
    }


}
