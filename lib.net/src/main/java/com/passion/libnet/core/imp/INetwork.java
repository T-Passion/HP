package com.passion.libnet.core.imp;

import com.passion.libnet.core.RequestModel;

import java.util.Map;

/**
 * Created by chaos
 * on 2017/11/15. 16:56
 * 文件描述：
 * 功能：支持GET，POST，
 *      不支持OPTIONS，PUT，DELETE，TRACES
 */

public interface INetwork {

    void get(String url,Map<String, String> params,Callback callback);

    void post(String url, Map<String, String> params,Callback callback);

    void upload(RequestModel requestModel,Callback callback);

    void download(String url,Map<String,String> params,Callback callback);




}
