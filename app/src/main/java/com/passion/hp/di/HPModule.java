package com.passion.hp.di;

import com.passion.libbase.Configuration;
import com.passion.libbase.HPApplication;
import com.passion.libbase.net.NetWorker;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chaos
 * on 2017/12/6. 15:15
 * 文件描述：需要在全局注入的对象
 */
@Module
public class HPModule {


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
