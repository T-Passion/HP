package com.passion.libnet.core.convert;

import com.passion.libnet.core.mapper.JsonMapper;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by chaos
 * on 2017/11/15. 22:13
 * 文件描述：
 */

public class GsonConverter implements Converter {

    public GsonConverter() {
    }

    @Override
    public <T> T fromBody(byte[] data, Type type) throws IOException {
        return JsonMapper.fromJson(data,type);
    }

    @Override
    public byte[] toBody(Object value) throws IOException {
        return JsonMapper.toJsonBytes(value);
    }
}
