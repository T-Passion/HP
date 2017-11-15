package com.passion.libnet.core.convert;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by chaos
 * on 2017/11/15. 22:09
 * 文件描述：
 */

public interface Converter {
    /**
     *
     * @param data 数据
     * @param type 数据类型
     * @param <T> 数据范型
     * @return 数据
     * @throws IOException 异常
     */
    <T> T fromBody(byte[] data, Type type) throws IOException;

    /**
     *
     * @param value 数据对象
     * @return 字节数据
     * @throws IOException 异常
     */
    byte[] toBody(Object value) throws IOException;

}
