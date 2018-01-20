package com.passion.hp.module.home.model.entity;

/**
 * Created by chaos
 * on 2018/1/17. 14:07
 * 文件描述：
 */

public class TabVo {

    private String cate_type;
    private String name;
    private String id;


    public TabVo() {
    }

    public TabVo(String name, String id) {
        this.name = name;
        this.id = id;
    }


    public String getCate_type() {
        return cate_type;
    }

    public void setCate_type(String cate_type) {
        this.cate_type = cate_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
