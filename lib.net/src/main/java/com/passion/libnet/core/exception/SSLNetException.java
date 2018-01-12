package com.passion.libnet.core.exception;

/**
 * Created by chaos
 * on 2017/11/16. 17:00
 * 文件描述：
 */

public class SSLNetException extends NetException{

    public SSLNetException() {
    }

    public SSLNetException(String message, Throwable cause) {
        super(message, cause);
    }

    public SSLNetException(String message, Throwable cause, ErrorResponse errorResponse) {
        super(message, cause, errorResponse);
    }

}
