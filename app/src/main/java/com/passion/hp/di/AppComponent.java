package com.passion.hp.di;


import com.passion.hp.module.splash.ui.SplashActivity;

import dagger.Subcomponent;

/**
 * Created by huangdou
 * on 2017/10/13.
 */
@Subcomponent(modules = {
        AppModule.class
})
public interface AppComponent {

    void inject(SplashActivity splashActivity);


}
