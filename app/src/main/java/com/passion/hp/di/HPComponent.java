package com.chaos.ultrohupu.di;

import com.chaos.base.di.BaseComponent;
import com.chaos.base.di.BaseModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by chaos
 * on 2017/10/16. 14:29
 * 文件描述：
 */

@Singleton
@Component()
public interface HPComponent {

    AppComponent plus(AppModule appModule);

    BaseComponent plus(BaseModule baseModule);

}
