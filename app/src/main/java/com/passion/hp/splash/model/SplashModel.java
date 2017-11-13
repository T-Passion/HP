package com.passion.hp.splash.model;


import com.passion.hp.splash.Constant;
import com.passion.hp.splash.contract.SplashContract;
import com.passion.libbase.mvp.BaseModel;

import java.util.Random;

import javax.inject.Inject;

/**
 * Created by huangdou
 * on 2017/10/10.
 */

public class SplashModel extends BaseModel implements SplashContract.Model {



    @Inject
    public SplashModel() {
    }

    @Override
    public String getSplashRandomRes() {
        Random random = new Random();
        return Constant.SPLASH_IMG.SPLASH_LIST.get(random.nextInt(Constant.SPLASH_IMG.SPLASH_LIST.size()));
    }
}
