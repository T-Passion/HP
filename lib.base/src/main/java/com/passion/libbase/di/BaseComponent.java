package com.passion.libbase.di;


import com.passion.libbase.AbstractBaseActivity;
import com.passion.libbase.AbstractBaseFragment;
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

    void inject(AbstractBaseActivity abstractBaseActivity);

    void inject(AbstractBaseFragment abstractBaseFragment);

}
