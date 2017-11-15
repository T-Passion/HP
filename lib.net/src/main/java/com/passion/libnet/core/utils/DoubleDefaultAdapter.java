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
 * on 2017/11/15. 22:41
 * 文件描述：
 */

public class DoubleDefaultAdapter implements JsonSerializer<Double>, JsonDeserializer<Double> {
    public DoubleDefaultAdapter() {
    }

    @Override
    public Double deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return !"".equals(json.getAsString()) && !"null".equals(json.getAsString()) ? Double.valueOf(json.getAsDouble()) : Double.valueOf(0.0D);
    }

    @Override
    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}
