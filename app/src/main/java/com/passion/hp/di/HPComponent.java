package com.passion.hp.di;


import com.passion.libbase.di.BaseComponent;
import com.passion.libbase.di.BaseModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by chaos
 * on 2017/10/16. 14:29
 * 文件描述：总和各个子Component
 */

@Singleton
@Component(modules = HPModule.class)
public interface HPComponent {

    AppComponent plus(AppModule appModule);

    BaseComponent plus(BaseModule baseModule);

}
