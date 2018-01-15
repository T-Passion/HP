package com.passion.libnet.core.response;

/**
 * Created by chaos
 * on 2018/1/15. 11:14
 * 文件描述：
 */

public class ErrorInfo {

    private int id;
    private String text;

    public ErrorInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
