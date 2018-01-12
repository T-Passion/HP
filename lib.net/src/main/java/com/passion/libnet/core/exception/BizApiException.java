package com.passion.libnet.core.exception;

/**
 * Created by chaos
 * on 2018/1/12. 12:27
 * 文件描述：
 */

public class BizApiException extends Exception{


    final ErrorResponse mErrorResponse;

    public BizApiException() {
        this.mErrorResponse = null;
    }

    public BizApiException(String message, Throwable cause) {
        super(message, cause);
        this.mErrorResponse = null;
    }

    public BizApiException(String message) {
        super(message);
        this.mErrorResponse = null;
    }

    public BizApiException(Throwable cause) {
        super(cause);
        this.mErrorResponse = null;
    }

    public BizApiException(String message, Throwable cause, ErrorResponse errorResponse) {
        super(message, cause);
        this.mErrorResponse = errorResponse;
    }
}
