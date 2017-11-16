package com.passion.libnet.core.exception;

/**
 * Created by chaos
 * on 2017/11/16. 14:08
 * 文件描述：
 */

public class TimeoutNetException extends NetException{

    public TimeoutNetException(Throwable cause) {
        super(cause);
    }

    public TimeoutNetException(String detailMessage, Throwable cause) {
        super(detailMessage, cause);
    }
}
