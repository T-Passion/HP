package com.passion.libnet.core.response;

/**
 * Created by chaos
 * on 2018/1/12. 14:26
 * 文件描述：
 */

public class HttpResult<T> {

    public static final int SUCCESS = 1; // 成功
    public static final int FAILURE = 0; // 失败

    /**
     * 数据对象，标准json格式
     */
    private T result;

    /**
     * 接口调用是否成功，0：失败，1：成功
     * <p>
     * <b>这个code既代表了客户端有没有调通接口、也代表了服务端业务处理有没有成功<br />
     * 如果调通了接口，但是服务端业务处理失败了（比如购物车清理失败）该code的值仍是0
     * </b>
     * </p>
     */
    private int code;

    /**
     * 业务编码 接口调用错误业务编码，具体编码。格式为ERR_XXX000，其中ERR_为固定前缀；XXX为平台简称，
     * 比如交易平台为TCS；000为平台内部返回码编号；ERR_PUB000为公共返回码，000为可变数据部分。编码定义链接
     */
    private String errorCode;
    /**
     * 错误消息内容，国际化项目后根据请求的语言类型由API层返回不同语言的消息内容
     */
    private String message;

    private int is_loging;
    private long crt;

    private ErrorInfo error;


    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public int getIs_loging() {
        return is_loging;
    }

    public void setIs_loging(int is_loging) {
        this.is_loging = is_loging;
    }

    public long getCrt() {
        return crt;
    }

    public void setCrt(long crt) {
        this.crt = crt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorInfo getError() {
        return error;
    }

    public void setError(ErrorInfo error) {
        this.error = error;
    }
}
