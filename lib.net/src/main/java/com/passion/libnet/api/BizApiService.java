package com.passion.libnet.api;

import com.passion.libnet.core.imp.ApiService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaos
 * on 2018/1/11. 15:20
 * 文件描述：
 */

public abstract class BizApiService implements ApiService {
    /**
     * 需要在这里注册使用到的server请求方法
     */
    protected static Map<String,String> apiMap = new HashMap<>();

    @Override
    public String get(String key) {
        return apiMap.get(key);
    }
}
