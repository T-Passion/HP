package com.passion.libnet.core.exception;

/**
 * Created by chaos
 * on 2017/11/16. 14:08
 * 文件描述：
 */

public class ErrorResponse {


    public final int statusCode;
    public final byte[] data;

    public ErrorResponse(int statusCode, byte[] data) {
        this.statusCode = statusCode;
        this.data = data;
    }
}
