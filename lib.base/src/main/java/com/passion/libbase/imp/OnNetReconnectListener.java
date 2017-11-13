package com.passion.libbase.imp;

import java.util.List;

/**
 * 网络事件失败，重新尝试事件
 * Created by huangdou
 * on 2017/9/30.
 */

public interface OnNetReconnectListener {
    /**
     * 重新连接
     *
     * @param eventType
     * @param params
     */
    void onReconnect(String eventType, List params);
}
