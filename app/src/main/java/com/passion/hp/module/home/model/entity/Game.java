package com.passion.hp.module.home.model.entity;

import java.util.List;

/**
 * Created by chaos
 * on 2018/1/20. 16:42
 * 文件描述：
 */

public class Game {

    private List<GameVo> game_lists;
    private int refresh_time;

    public List<GameVo> getGame_lists() {
        return game_lists;
    }

    public void setGame_lists(List<GameVo> game_lists) {
        this.game_lists = game_lists;
    }

    public int getRefresh_time() {
        return refresh_time;
    }

    public void setRefresh_time(int refresh_time) {
        this.refresh_time = refresh_time;
    }
}
