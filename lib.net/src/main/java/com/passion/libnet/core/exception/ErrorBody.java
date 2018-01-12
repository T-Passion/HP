package com.passion.libnet.core.exception;

/**
 * Created by chaos
 * on 2018/1/12. 13:58
 * 文件描述：
 */

public class ErrorBody {

    private int errorCode;
    private String errorMsg;
    private Throwable exception;

    public ErrorBody() {
    }

    public ErrorBody(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public ErrorBody(Throwable exception) {
        this.exception = exception;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
