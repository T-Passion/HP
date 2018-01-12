package com.passion.libnet.core.imp;

import com.passion.libnet.core.RequestModel;

/**
 * Created by chaos
 * on 2017/11/15. 22:50
 * 文件描述：
 */

public interface RequestInterceptor {

    RequestModel intercept(RequestModel requestModel);
}
