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
 * on 2017/11/15. 22:42
 * 文件描述：
 */

public class IntegerDefaultAdapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {
    public IntegerDefaultAdapter() {
    }

    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return !"".equals(json.getAsString()) && !"null".equals(json.getAsString()) ? Integer.valueOf(json.getAsInt()) : Integer.valueOf(0);
    }

    @Override
    public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}

