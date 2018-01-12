package com.passion.libnet.core.imp;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chaos
 * on 2017/11/16. 17:24
 * 文件描述：
 */
@Deprecated
public interface Dispatcher {

    Response dispatch(Request var1) throws IOException;
}
