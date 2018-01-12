package com.passion.libnet.core.mapper;

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
 * on 2017/11/15. 22:29
 * 文件描述：
 */

public class ByteDefaultAdapter implements JsonSerializer<Byte>, JsonDeserializer<Byte> {

    public ByteDefaultAdapter() {
    }

    @Override
    public Byte deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return !"".equals(json.getAsString()) && !"null".equals(json.getAsString()) ? Byte.valueOf(json.getAsByte()) : Byte.valueOf((byte) 0);
    }

    @Override
    public JsonElement serialize(Byte src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}
