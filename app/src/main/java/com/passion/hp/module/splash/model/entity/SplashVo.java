package com.passion.hp.module.splash.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangdou
 * on 2017/10/12.
 */

public class SplashVo {


    private String url;

    public SplashVo() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public static final class SPLASH_IMG {

        private static final String SPLASH_1 = "https://i1.hoopchina.com.cn/blogfile/201801/19/BbsImg15163423942079_720x1280big.png";
//        private static final String SPLASH_2 = "https://i1.hoopchina.com.cn/blogfile/201801/19/BbsImg15163423942079_720x1280big.png";
        public static List<String> SPLASH_LIST = new ArrayList<>();

        static {
            SPLASH_LIST.add(SPLASH_1);
//            SPLASH_LIST.add(SPLASH_2);
        }

    }
}
