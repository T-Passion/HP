package com.passion.hp.module.splash.di;

import com.passion.hp.module.splash.ui.SplashActivity;

import dagger.Subcomponent;

/**
 * Created by chaos
 * on 2017/12/13. 14:39
 * 文件描述：
 */
@Subcomponent(modules = SplashModule.class )
public interface SplashComponent {

    void inject(SplashActivity splashActivity);

}
