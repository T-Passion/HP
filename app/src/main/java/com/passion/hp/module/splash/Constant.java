package com.passion.hp.module.splash;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangdou
 * on 2017/10/12.
 */

public class Constant {


    public static final class SPLASH_IMG {

        private static final String SPLASH_1 = "http://img.hb.aicdn.com/50b2fdbdda3f31408075d0b901d40b714ce642e09a6b08-Aj5J3i_fw658";
        private static final String SPLASH_2 = "http://img.hb.aicdn.com/db57373dabb2c7fa09b85af419829ed9cd4e49d79f964d-vHww3y_fw658";
        public static List<String> SPLASH_LIST = new ArrayList<>();

        static {
            SPLASH_LIST.add(SPLASH_1);
            SPLASH_LIST.add(SPLASH_2);
        }

    }
}
