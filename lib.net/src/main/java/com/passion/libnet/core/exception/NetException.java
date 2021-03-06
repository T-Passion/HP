package com.passion.libnet.core.exception;

/**
 * Created by chaos
 * on 2017/11/16. 14:00
 * 文件描述：
 */

public class NetException extends Exception{

    public final ErrorResponse mErrorResponse;

    public NetException() {
        this.mErrorResponse = null;
    }

    public NetException(String message, Throwable cause) {
        super(message, cause);
        this.mErrorResponse = null;
    }

    public NetException(String message) {
        super(message);
        this.mErrorResponse = null;
    }

    public NetException(Throwable cause) {
        super(cause);
        this.mErrorResponse = null;
    }

    public NetException(String message, Throwable cause, ErrorResponse errorResponse) {
        super(message, cause);
        this.mErrorResponse = errorResponse;
    }


}
