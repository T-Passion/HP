package com.passion.hp.module.home.model.entity;

import java.util.List;

/**
 * Created by chaos
 * on 2018/1/20. 16:43
 * 文件描述：
 */

public class GameVo {


    /**
     * video_collection : 1
     * gid : 153707
     * away_timeout : 1
     * away_name : 步行者
     * process : 已结束
     * home_timeout : 1
     * home_fouls : 5
     * away_tid : 12
     * status : 1
     * home_tid : 24
     * offical_roomid : 7
     * begin_time : 1516419000
     * live_status : 1
     * date_time : 今日11:30
     * race : {"1":-2,"2":2,"3":-2,"4":0,"5":0,"6":2,"7":0,"8":-5,"9":-7,"10":-9,"11":-9,"12":-7,"13":-5,"14":-3,"15":-3,"16":-1,"17":3,"18":5,"19":9,"20":5,"21":5,"22":5,"23":5,"24":8,"25":8,"26":4,"27":2,"28":5,"29":2,"30":1,"31":0,"32":4,"33":8,"34":10,"35":8,"36":8,"37":7,"38":5,"39":4,"40":4,"41":4,"42":4,"43":2,"44":7,"45":7,"46":10,"47":13,"48":13}
     * away_fouls_color : 1
     * match_type : REGULAR
     * away_score : 86
     * live : 1
     * home_score : 99
     * away_fouls : 6
     * home_fouls_color : 1
     * race_v : [-2,2,-2,0,0,2,0,-5,-7,-9,-9,-7,-5,-3,-3,-1,3,5,9,5,5,5,5,8,8,4,2,5,2,1,0,4,8,10,8,8,7,5,4,4,4,4,2,7,7,10,13,13]
     * home_name : 湖人
     * default_tab : recap
     * tvs : ["腾讯视频","腾讯体育","CCTV5"]
     * home_logo : https://gdc.hupucdn.com/gdc/nba/team/logo/4f04d1b4f8a6548a.png
     * away_logo : https://gdc.hupucdn.com/gdc/nba/team/logo/83b033b5c036f590.png
     * will_start : 0
     * order_num : 4
     * game_id : 153707
     * game_type : 1
     * league_name : 常规赛
     * lid : 1
     * league_en : nba
     */

    private String video_collection;
    private String gid;
    private String away_timeout;
    private String away_name;
    private String process;
    private String home_timeout;
    private String home_fouls;
    private String away_tid;
    private String status;
    private String home_tid;
    private String offical_roomid;
    private String begin_time;
    private String live_status;
    private String date_time;
    private String race;
    private String away_fouls_color;
    private String match_type;
    private String away_score;
    private String live;
    private String home_score;
    private String away_fouls;
    private String home_fouls_color;
    private String race_v;
    private String home_name;
    private String default_tab;
    private String home_logo;
    private String away_logo;
    private int will_start;
    private String order_num;
    private String game_id;
    private String game_type;
    private String league_name;
    private String lid;
    private String league_en;
    private List<String> tvs;

    public String getVideo_collection() {
        return video_collection;
    }

    public void setVideo_collection(String video_collection) {
        this.video_collection = video_collection;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getAway_timeout() {
        return away_timeout;
    }

    public void setAway_timeout(String away_timeout) {
        this.away_timeout = away_timeout;
    }

    public String getAway_name() {
        return away_name;
    }

    public void setAway_name(String away_name) {
        this.away_name = away_name;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getHome_timeout() {
        return home_timeout;
    }

    public void setHome_timeout(String home_timeout) {
        this.home_timeout = home_timeout;
    }

    public String getHome_fouls() {
        return home_fouls;
    }

    public void setHome_fouls(String home_fouls) {
        this.home_fouls = home_fouls;
    }

    public String getAway_tid() {
        return away_tid;
    }

    public void setAway_tid(String away_tid) {
        this.away_tid = away_tid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHome_tid() {
        return home_tid;
    }

    public void setHome_tid(String home_tid) {
        this.home_tid = home_tid;
    }

    public String getOffical_roomid() {
        return offical_roomid;
    }

    public void setOffical_roomid(String offical_roomid) {
        this.offical_roomid = offical_roomid;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getLive_status() {
        return live_status;
    }

    public void setLive_status(String live_status) {
        this.live_status = live_status;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getAway_fouls_color() {
        return away_fouls_color;
    }

    public void setAway_fouls_color(String away_fouls_color) {
        this.away_fouls_color = away_fouls_color;
    }

    public String getMatch_type() {
        return match_type;
    }

    public void setMatch_type(String match_type) {
        this.match_type = match_type;
    }

    public String getAway_score() {
        return away_score;
    }

    public void setAway_score(String away_score) {
        this.away_score = away_score;
    }

    public String getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live = live;
    }

    public String getHome_score() {
        return home_score;
    }

    public void setHome_score(String home_score) {
        this.home_score = home_score;
    }

    public String getAway_fouls() {
        return away_fouls;
    }

    public void setAway_fouls(String away_fouls) {
        this.away_fouls = away_fouls;
    }

    public String getHome_fouls_color() {
        return home_fouls_color;
    }

    public void setHome_fouls_color(String home_fouls_color) {
        this.home_fouls_color = home_fouls_color;
    }

    public String getRace_v() {
        return race_v;
    }

    public void setRace_v(String race_v) {
        this.race_v = race_v;
    }

    public String getHome_name() {
        return home_name;
    }

    public void setHome_name(String home_name) {
        this.home_name = home_name;
    }

    public String getDefault_tab() {
        return default_tab;
    }

    public void setDefault_tab(String default_tab) {
        this.default_tab = default_tab;
    }

    public String getHome_logo() {
        return home_logo;
    }

    public void setHome_logo(String home_logo) {
        this.home_logo = home_logo;
    }

    public String getAway_logo() {
        return away_logo;
    }

    public void setAway_logo(String away_logo) {
        this.away_logo = away_logo;
    }

    public int getWill_start() {
        return will_start;
    }

    public void setWill_start(int will_start) {
        this.will_start = will_start;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getGame_type() {
        return game_type;
    }

    public void setGame_type(String game_type) {
        this.game_type = game_type;
    }

    public String getLeague_name() {
        return league_name;
    }

    public void setLeague_name(String league_name) {
        this.league_name = league_name;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getLeague_en() {
        return league_en;
    }

    public void setLeague_en(String league_en) {
        this.league_en = league_en;
    }

    public List<String> getTvs() {
        return tvs;
    }

    public void setTvs(List<String> tvs) {
        this.tvs = tvs;
    }
}
