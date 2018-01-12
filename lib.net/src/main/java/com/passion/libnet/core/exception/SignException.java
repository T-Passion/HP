package com.passion.libnet.core.exception;

/**
 * Created by chaos
 * on 2017/11/15. 23:05
 * 文件描述：
 */

public class SignException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SignException() {
    }

    public SignException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignException(String message) {
        super(message);
    }

    public SignException(Throwable cause) {
        super(cause);
    }

    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}

