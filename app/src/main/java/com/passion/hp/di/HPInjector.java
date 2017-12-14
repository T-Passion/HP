package com.passion.hp.di;


import com.passion.libbase.di.BaseComponent;
import com.passion.libbase.di.BaseInjector;
import com.passion.libbase.di.imp.IInjector;
import com.passion.libbase.utils.LogUtil;

/**
 * Created by chaos
 * on 2017/10/13.
 * 作为应用Dagger的入口
 * 注意：该类需要被反射调用 混淆时避开该类
 */

public class HPInjector implements IInjector {

    static HPComponent sHPComponent;

    private AppInjector mAppInjector = new AppInjector();
    private BaseInjector mBaseInjector = new BaseInjector();

    @Override
    public void initComponent() {
        sHPComponent = DaggerHPComponent.builder().hPModule(new HPModule()).build();
        //app module
        AppComponent appComponent = sHPComponent.plus(mAppInjector.getModule());
        mAppInjector.setComponent(appComponent);
        //base module
        BaseComponent baseComponent = sHPComponent.plus(mBaseInjector.getModule());
        mBaseInjector.setComponent(baseComponent);

    }

    @Override
    public boolean inject(Object target) {
        LogUtil.i("start App injector");
        if (mAppInjector.inject(target)) {
            LogUtil.i("inject " + target.getClass() + " ok");
        } else if (mBaseInjector.inject(target)) {
            LogUtil.i("inject " + target.getClass() + " ok");
        } else {
            LogUtil.i("inject " + target.getClass() + " fail");
            return false;
        }
        return true;
    }
}
