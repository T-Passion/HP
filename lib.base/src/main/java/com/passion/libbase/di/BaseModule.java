package com.passion.libbase.di;

import com.passion.libbase.Configuration;
import com.passion.libbase.HPApplication;
import com.passion.libbase.net.NetWorker;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chaos
 * on 2017/10/9.
 * 该类包含全局对象的依赖注入
 */

@Module()
public class BaseModule {

    private HPApplication mAppContext;

    private EventBus mEventBus;

    private NetWorker mNetWorker;

    @Singleton
    @Provides
    public HPApplication provideAppContext(){
        return Configuration.getAppContext();
    }

    @Singleton
    @Provides
    public EventBus provideEventBus(){
        return new EventBus();
    }
    @Singleton
    @Provides
    public NetWorker provideNetWorker(){
        return new NetWorker();
    }




}
