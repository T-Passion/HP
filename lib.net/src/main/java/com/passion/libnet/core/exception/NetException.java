package com.passion.libnet.core.exception;

import com.passion.libnet.core.NetResponse;

/**
 * Created by chaos
 * on 2017/11/16. 14:00
 * 文件描述：
 */

public class NetException extends Exception{

    public final NetResponse netResponse;

    public NetException() {
        this.netResponse = null;
    }

    public NetException(String message, Throwable cause) {
        super(message, cause);
        this.netResponse = null;
    }

    public NetException(String message) {
        super(message);
        this.netResponse = null;
    }

    public NetException(Throwable cause) {
        super(cause);
        this.netResponse = null;
    }

    public NetException(String message, Throwable cause, NetResponse netResponse) {
        super(message, cause);
        this.netResponse = netResponse;
    }


}
