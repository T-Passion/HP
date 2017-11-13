package com.chaos.ultrohupu.di;

import com.chaos.ultrohupu.HomeActivity;
import com.chaos.ultrohupu.splash.ui.SplashActivity;

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

    void inject(HomeActivity homeActivity);

}
