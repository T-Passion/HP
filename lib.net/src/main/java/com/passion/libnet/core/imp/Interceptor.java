package com.passion.libnet.core.imp;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import okhttp3.Connection;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chaos
 * on 2017/11/16. 14:36
 * 文件描述：
 */

public interface Interceptor {


    Response intercept(Interceptor.Chain var1) throws IOException;

    public interface Chain {


        Request request();

        Response proceed(Request var1) throws IOException;

        @Nullable
        Connection connection();
    }
}
