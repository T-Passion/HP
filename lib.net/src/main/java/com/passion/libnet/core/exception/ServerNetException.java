package com.passion.libnet.core.exception;

import com.passion.libnet.core.NetResponse;

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

    public ServerNetException(String message, Throwable cause, NetResponse netResponse) {
        super(message, cause, netResponse);
    }
}
