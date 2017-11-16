package com.passion.libnet.core.exception;

import com.passion.libnet.core.NetResponse;

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

    public SSLNetException(String message, Throwable cause, NetResponse netResponse) {
        super(message, cause, netResponse);
    }

}
