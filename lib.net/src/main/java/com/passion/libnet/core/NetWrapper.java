package com.passion.libnet.core;

import android.support.annotation.NonNull;

import com.passion.libnet.core.utils.SafeCheckUtil;

/**
 * Created by chaos
 * on 2018/1/9. 15:23
 * 文件描述：网络框架的全局上下文
 */

public class NetWrapper {
    private static NetConfig netConfig;

    public static void init(NetConfig config){
        netConfig = config;
    }

    public static @NonNull NetConfig getConfig(){
        if(SafeCheckUtil.isNull(netConfig)){
            throw new RuntimeException("NetConfig未初始化配置，请先使用NetConfig进行全局初始化");
        }
        return netConfig;
    }



}
