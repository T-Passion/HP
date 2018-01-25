package com.passion.hp.module.home.model.entity;

import java.util.List;

/**
 * Created by chaos
 * on 2018/1/20. 16:19
 * 文件描述：
 */

public class NewsAllVo  {

    private int nextDataExists;

    /**
     * 新闻集合
     */
    private List<NewsVo> data;
    /**
     * 种类tab集合
     */
    private List<TabVo> cate_list;

    private Game game;

    public int getNextDataExists() {
        return nextDataExists;
    }

    public void setNextDataExists(int nextDataExists) {
        this.nextDataExists = nextDataExists;
    }

    public List<TabVo> getCate_list() {
        return cate_list;
    }

    public List<TabVo> getTabList(){
        return getCate_list();
    }

    public void setCate_list(List<TabVo> cate_list) {
        this.cate_list = cate_list;
    }



    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<NewsVo> getData() {
        return data;
    }

    public void setData(List<NewsVo> data) {
        this.data = data;
    }
}
