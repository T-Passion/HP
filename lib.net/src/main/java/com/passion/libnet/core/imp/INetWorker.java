package com.passion.libnet.core.imp;

import com.passion.libnet.core.RequestModel;

/**
 * Created by chaos
 * on 2017/11/15. 16:56
 * 文件描述：
 * 功能：支持GET，POST，
 *      不支持OPTIONS，PUT，DELETE，TRACES
 */

public interface INetWorker {
    /**
     *
     * @param params 请求参数
     * @param netCallback 请求结果回调
     */
    <T> void get(RequestModel params,NetCallback<T> netCallback);

    /**
     *
     * @param params 请求参数
     * @param netCallback 请求结果回调
     */
    <T> void post(RequestModel params,NetCallback<T> netCallback);






}
