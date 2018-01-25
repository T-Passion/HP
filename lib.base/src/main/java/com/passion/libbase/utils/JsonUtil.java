package com.passion.libbase.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passion.libnet.core.mapper.JavaTypeToken;
import com.passion.libnet.core.mapper.JsonMapper;
import com.passion.libnet.core.response.HttpResult;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by chaos
 * on 2017/10/10.
 */

public class JsonUtil {
    public final static Short COMPILETYPE_MOCK_FIRST = (short) 1;

    public final static Short COMPILETYPE_API_FIRT = (short) 2;

    private ObjectMapper objectMapper = null;

    public JsonUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public String getNodeText(String name, String result) {
        try {
            if (StringUtils.isEmpty(result)) {
                return null;
            }
            JsonNode node = objectMapper.readTree(result);
            if (node == null) {
                return null;
            }
            node = node.get(name);
            if (node == null) {
                return null;
            }
            return node.toString();
        } catch (JsonParseException e) {
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T clone(T t) {
        if (t == null) {
            return null;
        }
        String json = toJsonString(t);
        T t1 = (T) fromJSON(json, t.getClass());
        return t1;
    }

    public <P> P fromJSON(String json, TypeReference<P> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException e) {

        }
        return null;
    }

    public <T> T fromJSON(String json, Class<T> clazz, Class... classes) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(clazz, classes);
        try {
            return objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T fromJSON(JsonNode jsonNode, Class<T> clazz, Class... classes) {
        return fromJSON(jsonNode.toString(), clazz, classes);
    }

    public <T> T fromJSON(JsonNode jsonNode, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonNode.toString(), clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T fromJSON(String json, Class<T> clazz) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            return fromJSON(jsonNode, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T fromJSON(String name, String result, Class<T> clazz) {
        try {
            String json = getNodeText(name, result);
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            return objectMapper.readValue(json, clazz);
        } catch (JsonParseException e) {
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> List<T> fromJSONList(String name, String result, Class<T> clazz) {
        try {
            String json = getNodeText(name, result);
            if (StringUtils.isEmpty(json)) {
                return null;
            }

            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
            List<T> lst = (List<T>) objectMapper.readValue(json, javaType);
            return lst;
        } catch (JsonParseException e) {
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 非标准的json格式 传回来是一个字符串格式 先转化为标准的字符串格式再转化为对应的对象.
     *
     * @param name
     * @param result
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T fromJSONString(String name, String result, Class<T> clazz) {
        try {
            String json = getNodeText(name, result);
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            String tempjson = objectMapper.readValue(json, String.class);
            return objectMapper.readValue(tempjson, clazz);
        } catch (JsonParseException e) {
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从json array类型的字符串中读取一个list数据
     */
    public <T> List<T> fromJSONList(String jsonArrayText, Class<T> clazz) {
        if (TextUtils.isEmpty(jsonArrayText)) return null;

        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
            return objectMapper.readValue(jsonArrayText, javaType);
        } catch (JsonParseException e) {
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 非标准的json格式 传回来是一个字符串格式 先转化为标准的字符串格式再转化为对应的 list<T> 对象.
     *
     * @param name
     * @param result
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> fromJSONListString(String name, String result, Class<T> clazz) {

        try {
            String json = getNodeText(name, result);
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            String tempjson = objectMapper.readValue(json, String.class);
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
            List<T> lst = (List<T>) objectMapper.readValue(tempjson, javaType);
            return lst;
        } catch (JsonParseException e) {
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 把JavaBean转换为json字符串
     *
     * @param object
     * @return
     */
    public String toJsonString(Object object) {
        if (object != null && objectMapper != null) {
            try {
                return objectMapper.writeValueAsString(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    /**
     * 得到key的正确显示。
     *
     * @param parentKey
     * @param key
     * @return
     */
    private String getkey(String parentKey, String key) {
        if (parentKey == null || parentKey.trim().length() == 0) {
            return key;
        } else {
            return parentKey + "-" + key;
        }
    }

    /**
     * 对比两个JsonNode对象将不同的key存入到返回的map中.
     *
     * @param json1
     * @param json2
     * @param key
     * @return
     */
    public Map<String, String> compareJson(JsonNode json1, JsonNode json2, String key) {
        Map<String, String> resultMap = new HashMap<>();
        String keyName = key;
        if (json1 == null || json2 == null) {
            return resultMap;
        }
        Iterator<String> paramsIterator = json1.fieldNames();//获得一个迭代器 存储jsonNode的所有key
        while (paramsIterator.hasNext()) {
            String paramName = paramsIterator.next();//得到一个key
            JsonNode jsonSonNode1 = json1.get(paramName);
            JsonNode jsonSonNode2 = json2.get(paramName);
            if (jsonSonNode1 != null && jsonSonNode2 != null) {
                if (jsonSonNode1.getNodeType().equals(jsonSonNode2.getNodeType())) {
                    if (jsonSonNode1.isObject() && jsonSonNode2.isObject()) {
                        Map<String, String> tempResultMap = compareJson(jsonSonNode1, jsonSonNode2, getkey(keyName, paramName));
                        resultMap.putAll(tempResultMap);
                    } else if (jsonSonNode1.isArray() && jsonSonNode2.isArray()) {
                        Iterator<JsonNode> nodeIterator1 = jsonSonNode1.elements();//获取数组里的值
                        Iterator<JsonNode> nodeIterator2 = jsonSonNode2.elements();//获取数组里的值
                        //只取一组数据进行比较
                        if (nodeIterator1.hasNext() && nodeIterator2.hasNext()) {
                            JsonNode jsonNode1 = nodeIterator1.next();//
                            JsonNode jsonNode2 = nodeIterator2.next();//
                            Map<String, String> tempResultMap = compareJson(jsonNode1, jsonNode2, getkey(keyName, paramName));
                            resultMap.putAll(tempResultMap);
                        }
                    }
                } else {
                    resultMap.put(getkey(keyName, paramName), getkey(keyName, paramName));
                }
            } else {
                resultMap.put(getkey(keyName, paramName), getkey(keyName, paramName));
            }
        }
        return resultMap;
    }

    /**
     * 转化第一层级的key, 并将对应的jsonNode存储到Map中
     */
    public Map<String, JsonNode> map(String result, String name) {
        Map<String, JsonNode> tMap = new HashMap<>();

        String json = getNodeText(name, result);
        if (!StringUtils.isEmpty(json)) {
            try {
                JsonNode tJsonNode = objectMapper.readTree(json);
                Iterator<String> tIter = tJsonNode.fieldNames();
                while (tIter.hasNext()) {
                    String tKey = tIter.next();
                    tMap.put(tKey, tJsonNode.findValue(tKey));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tMap;
    }

    /**
     * 将非list形式的json数据转换为map形式
     */
    public Map<String, Object> deepMap(String json) {
        if (!TextUtils.isEmpty(json)) {
            try {
                JsonNode tJsonNode = objectMapper.readTree(json);
                return deepMap(tJsonNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<>();
    }

    public Map<String, Object> deepMap(JsonNode aNode) {
        Map<String, Object> tResult = new HashMap<>();
        if (aNode != null) {
            if (aNode.isObject()) {
                Iterator<String> tIter = aNode.fieldNames();
                while (tIter.hasNext()) {
                    String tKey = tIter.next();
                    JsonNode tSubNode = aNode.get(tKey);
                    if (tSubNode.isArray()) {
                        tResult.put(tKey, mapFromList(tSubNode));
                    } else if (tSubNode.isObject()) {
                        tResult.put(tKey, deepMap(tSubNode));
                    } else if (tSubNode.isValueNode()) {
                        tResult.put(tKey, tSubNode);
                    }
                }
            }
        }
        return tResult;
    }

    /**
     * 将list形式的json数据转换为list形式的map结构
     */
    public List<Object> mapFromList(String aArrayJson) {
        if (!TextUtils.isEmpty(aArrayJson)) {
            try {
                JsonNode tJsonNode = objectMapper.readTree(aArrayJson);
                return mapFromList(tJsonNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    private List<Object> mapFromList(JsonNode aNodeArray) {
        if (aNodeArray.isArray()) {
            List<Object> tArray = new ArrayList<>();
            for (int i = 0; i < aNodeArray.size(); i++) {
                JsonNode tElement = aNodeArray.get(i);
                if (tElement.isObject()) {
                    tArray.add(deepMap(tElement));
                } else tArray.add(tElement);
            }
            return tArray;
        }
        return new ArrayList<>();
    }

    /**
     * 获取jsonnode对应的值
     * FIXME: 目前支持类型有限, 若使用过程中发现问题, 请补充需要支持的类型
     *
     * @param aNode value node
     * @param <T>   期望的返回类型
     * @return node value
     */
    @SuppressWarnings("unchecked")
    public <T> T getNodeValue(JsonNode aNode, T defalutValue) {
        if (aNode == null) return defalutValue;
        else if (aNode.isValueNode()) {
            if (defalutValue != null) {
                return (T) fromJSON(aNode, defalutValue.getClass());
            }

            if (aNode.isTextual()) return (T) aNode.asText();
            if (aNode.isNumber()) return (T) aNode.numberValue();
            if (aNode.isBoolean()) return (T) (Boolean) aNode.asBoolean();

            return defalutValue;
        } else {
            return defalutValue;
        }
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * 判断服务端返回的json是否有两层'data'，加入网关的接口会多包装一层data
     *
     * @param message
     * @return
     */
    public boolean isDoubleData(String message) {
        try {
            JSONObject object = new JSONObject(message);
            JSONObject dataObj = object.getJSONObject("data");
            if (dataObj.has("data")) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            LogUtil.d("检测json是否有两层data抛出异常");
            return false;
        }
    }

    /**
     * 返回需要解析的json（一层data结构）
     *
     * @param message
     * @return
     */
    public String analyzeJson(String message) {
        if (isDoubleData(message)) {
            message = getNodeText("data", message);
        }
        return message;
    }


    public <T> T fromFile(Context context, String assetsFileName, Class<T> clazz) {
        //先获取assets管理器，assets中的文件无法直接访问
        AssetManager am = context.getAssets();
        String line;
        StringBuilder resultBuilder = new StringBuilder();
        try {
            InputStreamReader isr = new InputStreamReader(am.open(assetsFileName), "UTF-8");
            BufferedReader bufReader = new BufferedReader(isr);

            while ((line = bufReader.readLine()) != null) {
                resultBuilder.append(line);
            }
            isr.close();
            bufReader.close();
        } catch (IOException e) {
            LogUtil.e(e);
        }

        //下面有T来解析得到具体对象
        Type typeOuter = JavaTypeToken.getParameterizedType(HttpResult.class, clazz);
        HttpResult<T> httpResult = JsonMapper.fromJson(resultBuilder.toString(), typeOuter);
        return httpResult.getResult();
    }

}
