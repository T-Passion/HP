package com.passion.libnet.core.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.passion.libnet.core.utils.ByteDefaultAdapter;
import com.passion.libnet.core.utils.DoubleDefaultAdapter;
import com.passion.libnet.core.utils.IntegerDefaultAdapter;
import com.passion.libnet.core.utils.ListParameterizedType;
import com.passion.libnet.core.utils.LongDefaultAdapter;
import com.passion.libnet.core.utils.ShortDefaultAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by chaos
 * on 2017/11/15. 22:16
 * 文件描述：
 */

public class JsonMapper {
    private static final Gson GSON;


    static {
        GSON = (new GsonBuilder()).registerTypeAdapter(Byte.TYPE, new ByteDefaultAdapter()).registerTypeAdapter(Byte.class, new ByteDefaultAdapter()).registerTypeAdapter(Short.TYPE, new ShortDefaultAdapter()).registerTypeAdapter(Short.class, new ShortDefaultAdapter()).registerTypeAdapter(Integer.TYPE, new IntegerDefaultAdapter()).registerTypeAdapter(Integer.class, new IntegerDefaultAdapter()).registerTypeAdapter(Long.TYPE, new LongDefaultAdapter()).registerTypeAdapter(Long.class, new LongDefaultAdapter()).registerTypeAdapter(Double.TYPE, new DoubleDefaultAdapter()).registerTypeAdapter(Double.class, new DoubleDefaultAdapter()).create();
    }


    public JsonMapper() {
    }

    public static String toJsonString(Object object) throws JsonParseException {
        StringWriter writer = new StringWriter();

        try {
            JsonWriter jsonWriter = GSON.newJsonWriter(Streams.writerForAppendable(writer));
            GSON.toJson(object, object.getClass(), jsonWriter);
        } catch (IOException | JsonIOException var3) {
            throw new JsonParseException(var3);
        }

        return writer.toString();
    }

    public static byte[] toJsonBytes(Object object) throws JsonParseException {
        String jsonStr = toJsonString(object);

        try {
            return jsonStr.getBytes("utf-8");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
            return jsonStr.getBytes();
        }
    }

    public static <T> T fromJson(String jsonString, Type type) throws JsonParseException {
        if (jsonString == null) {
            return null;
        } else {
            JsonReader jsonReader = new JsonReader(new StringReader(jsonString));

            try {
                return GSON.fromJson(jsonReader, type);
            } catch (JsonSyntaxException | JsonIOException var4) {
                throw new JsonParseException(var4);
            }
        }
    }

    public static <T> T fromJson(String jsonString, Class<?> rawType, Class... typeArguments) throws JsonParseException {
        return fromJson(jsonString, JavaTypeToken.getParameterizedType(rawType, typeArguments));
    }

    public static <T> T fromJson(byte[] bytes, Type type) throws JsonParseException {
        if (bytes == null) {
            return null;
        } else {
            try {
                return fromJson(new String(bytes, "utf-8"), type);
            } catch (UnsupportedEncodingException var3) {
                throw new JsonParseException(var3);
            }
        }
    }

    public static <T> T fromJson(InputStream inputStream, Type type) throws JsonParseException {
        if (inputStream == null) {
            return null;
        } else {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream));

            try {
                return GSON.fromJson(jsonReader, type);
            } catch (JsonSyntaxException | JsonIOException var4) {
                throw new JsonParseException(var4);
            }
        }
    }

    public static <T> T fromJson(Reader reader, Type type) throws JsonParseException {
        if (reader == null) {
            return null;
        } else {
            JsonReader jsonReader = new JsonReader(reader);

            try {
                return GSON.fromJson(jsonReader, type);
            } catch (JsonSyntaxException | JsonIOException var4) {
                throw new JsonParseException(var4);
            }
        }
    }

    public static <T> T fromJson(String jsonString, JavaTypeToken<T> javaTypeToken) throws JsonParseException {
        return jsonString != null && !"".equals(jsonString) ? (T) fromJson(jsonString, javaTypeToken.getType()) : null;
    }

    public static <T> List<T> fromJsonArray(String jsonString, Class<T> clsOfT) throws JsonParseException {
        if (jsonString != null && !"".equals(jsonString)) {
            ListParameterizedType parameterizedType = new ListParameterizedType(clsOfT);
            return (List) fromJson(jsonString, parameterizedType);
        } else {
            return null;
        }
    }


}

