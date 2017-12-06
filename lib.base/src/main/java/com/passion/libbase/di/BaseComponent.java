package com.passion.libbase.di;


import com.passion.libbase.HPApplication;

import dagger.Subcomponent;

/**
 * Created by huangdou
 * on 2017/10/9.
 * 该类为全局对象的依赖注入
 */
@Subcomponent(modules = BaseModule.class)
public interface BaseComponent {

    void inject(HPApplication HPApplication);

}
