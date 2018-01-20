package com.passion.hp.module.home.model.entity;

/**
 * Created by chaos
 * on 2018/1/20. 16:20
 * 文件描述：
 */

public class NewsVo {


    /**
     * nid : 2252575
     * title : NBA官方发布今日7支获胜球队的赢球图集
     * summary : 虎扑的这篇文章已经引发热烈讨论，赶快来看看吧。
     * uptime : 1516435509
     * img : https://c1.hoopchina.com.cn/uploads/star/event/images/180120/thumbnail-95d8e28c948ccf254bbad8b705ceecebc667321a.jpg?x-oss-process=image/resize,w_250/sharpen,100/format,webp
     * type : 1
     * lights : 0
     * replies : 3
     * read : 2252575
     */

    private String nid;
    private String title;
    private String summary;
    private String uptime;
    private String img;
    private int type;
    private String lights;
    private String replies;
    private String read;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLights() {
        return lights;
    }

    public void setLights(String lights) {
        this.lights = lights;
    }

    public String getReplies() {
        return replies;
    }

    public void setReplies(String replies) {
        this.replies = replies;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }
}
