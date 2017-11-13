package com.chaos.ultrohupu.di;

import com.chaos.base.di.imp.IInjector;
import com.chaos.ultrohupu.splash.ui.SplashActivity;

/**
 * Created by huangdou
 * on 2017/10/13.
 */

public class AppInjector implements IInjector {

    private AppComponent sAppComponent;
    private AppModule sAppModule;

    @Override
    public void initComponent() {

    }

    public AppModule getModule() {
        if (sAppModule == null) {
            synchronized (AppInjector.class) {
                if (sAppModule == null) {
                    sAppModule = new AppModule();
                }
            }
        }
        return sAppModule;
    }

    public void setComponent(AppComponent appComponent) {
        sAppComponent = appComponent;
    }

    @Override
    public boolean inject(Object target) {
        Class tClass = target.getClass();
        if (tClass == SplashActivity.class) {
            sAppComponent.inject((SplashActivity) target);
        } else {
            return false;
        }
        return true;
    }
}
