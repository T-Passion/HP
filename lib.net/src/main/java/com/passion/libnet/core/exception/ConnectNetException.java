package com.passion.libnet.core.exception;

/**
 * Created by chaos
 * on 2017/11/16. 14:08
 * 文件描述：
 */

public class ConnectNetException  extends NetException {
    public ConnectNetException() {
    }

    public ConnectNetException(Throwable cause) {
        super(cause);
    }

    public ConnectNetException(String detailMessage, Throwable cause) {
        super(detailMessage, cause);
    }
}

