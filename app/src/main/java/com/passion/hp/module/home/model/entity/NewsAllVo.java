package com.passion.hp.module.home.model.entity;

import java.util.List;

/**
 * Created by chaos
 * on 2018/1/20. 16:19
 * 文件描述：
 */

public class NewsAllVo  {

    private List<NewsVo> data;

    private List<TabVo> cate_list;

    public List<NewsVo> getData() {
        return data;
    }

    public void setData(List<NewsVo> data) {
        this.data = data;
    }
}
