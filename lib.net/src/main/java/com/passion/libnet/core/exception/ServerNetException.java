package com.passion.libnet.core.exception;

/**
 * Created by chaos
 * on 2017/11/16. 14:09
 * 文件描述：
 */

public class ServerNetException extends NetException{

    public ServerNetException() {
    }

    public ServerNetException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerNetException(String message, Throwable cause, ErrorResponse errorResponse) {
        super(message, cause, errorResponse);
    }
}
