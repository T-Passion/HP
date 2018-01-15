package com.passion.libnet.api;

import com.passion.libnet.core.imp.ApiService;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by chaos
 * on 2018/1/11. 15:20
 * 文件描述：
 */

public abstract class BizApiService implements ApiService {
    /**
     * 需要在这里注册使用到的server请求方法
     */
    protected static List<String> apiList = new LinkedList<>();

    @Override
    public boolean contains(String value) {
        return apiList.contains(value);
    }
}
