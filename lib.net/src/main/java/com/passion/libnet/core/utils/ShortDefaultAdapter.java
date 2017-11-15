package com.passion.libnet.core.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by chaos
 * on 2017/11/15. 22:37
 * 文件描述：
 */

public class ShortDefaultAdapter implements JsonSerializer<Short>, JsonDeserializer<Short> {
    public ShortDefaultAdapter() {
    }

    @Override
    public Short deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return !"".equals(json.getAsString()) && !"null".equals(json.getAsString()) ? Short.valueOf(json.getAsShort()) : Short.valueOf((short) 0);
    }

    @Override
    public JsonElement serialize(Short src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}
