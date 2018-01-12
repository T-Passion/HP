package com.passion.libbase;

import com.passion.libbase.net.HPNetConfig;
import com.passion.libbase.router.HPRouter;
import com.passion.libbase.utils.HPInjectUtil;
import com.passion.libbase.utils.LogUtil;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by chaos
 * on 2017/12/6. 12:05
 * 文件描述：做全局对象的初始化，以及一些配置项
 */

public final class Configuration {


    private static HPApplication mAppContext;
    private String mBuildType;

    Configuration(Builder builder) {
        this.mAppContext = builder.mApp;
        this.mBuildType = builder.mBuildType;
        AppEnv.init(mAppContext, mBuildType);
    }

    public static HPApplication getAppContext() {
        if (mAppContext == null) {
            throw new RuntimeException("请先在Application中进行初始化");
        }
        return mAppContext;
    }


    Configuration withRouter() {
        HPRouter.init(mAppContext);
        return this;
    }

    Configuration withLeaker() {
        LeakCanary.install(mAppContext);
        return this;
    }

    Configuration withDagger2() {
        HPInjectUtil.init();
        return this;
    }

    Configuration withLog() {
        LogUtil.init(mAppContext.getPackageName(), BuildConfig.DEBUG);
        return this;
    }

    Configuration withNet() {
        HPNetConfig.initNetWork(mAppContext);
        return this;
    }

    public static class Builder {
        HPApplication mApp;
        String mBuildType;

        public Builder() {
        }

        Builder app(HPApplication app) {
            this.mApp = app;
            return this;
        }

        Builder buildType(String buildType) {
            this.mBuildType = buildType;
            return this;
        }

        Configuration build() {
            return new Configuration(this);
        }
    }
}
