package com.passion.libbase;

import com.passion.libbase.router.HPRouter;
import com.passion.libbase.utils.HPInjectUtils;
import com.passion.libbase.utils.LogUtils;
import com.passion.libnet.core.NetConfig;
import com.passion.libnet.core.convert.GsonConverter;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by chaos
 * on 2017/12/6. 12:05
 * 文件描述：做全局对象的初始化，以及一些配置项
 */

public final class Configuration {


    private static HPApplication mAppContext;
    private String mBuildType;

    Configuration(Builder builder){
        this.mAppContext = builder.mApp;
        this.mBuildType = builder.mBuildType;
        AppEnv.init(mAppContext,mBuildType);
    }

    public static HPApplication getAppContext(){
        if(mAppContext == null){
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
        HPInjectUtils.init(this, mAppContext.getPackageName());
        return this;
    }

    Configuration withLog() {
        LogUtils.init(mAppContext.getPackageName(), BuildConfig.DEBUG);
        return this;
    }
    Configuration withNet() {
        NetConfig netConfig = new NetConfig.Builder()
                .setAppSecret("")
                .setCookieEnable(true)
                .setCookieJar(null)
                .setDebugMode(true)
                .setJsonConverter(new GsonConverter())
                .trustAllCerts(true)
                .build();


        return this;
    }

    public static class Builder{
        HPApplication mApp;
        String mBuildType;

        public Builder() {
        }

        Builder app(HPApplication app){
            this.mApp = app;
            return this;
        }

        Builder buildType(String buildType){
            this.mBuildType = buildType;
            return this;
        }

        Configuration build(){
            return new Configuration(this);
        }
    }
}
