package com.passion.libnet.core;

/**
 * Created by chaos
 * on 2017/11/16. 14:08
 * 文件描述：
 */

public class NetResponse {


    public final int statusCode;
    public final byte[] data;

    public NetResponse(int statusCode, byte[] data) {
        this.statusCode = statusCode;
        this.data = data;
    }
}
