package com.chaos.ultrohupu.di;

import com.chaos.base.di.BaseComponent;
import com.chaos.base.di.BaseInjector;
import com.chaos.base.di.imp.IInjector;
import com.chaos.base.utils.LogUtils;

/**
 * Created by huangdou
 * on 2017/10/13.
 * 该类需要被反射调用 混淆时避开该类
 */

public class HPInjector implements IInjector {

    static HPComponent sHPComponent;

    private AppInjector mAppInjector = new AppInjector();
    private BaseInjector mBaseInjector = new BaseInjector();

    @Override
    public void initComponent() {
        sHPComponent = DaggerHPComponent.builder().build();

        AppComponent appComponent = sHPComponent.plus(mAppInjector.getModule());
        mAppInjector.setComponent(appComponent);

        BaseComponent baseComponent = sHPComponent.plus(mBaseInjector.getModule());
        mBaseInjector.setComponent(baseComponent);

    }

    @Override
    public boolean inject(Object target) {
        LogUtils.i("start App injector");
        if (mAppInjector.inject(target)) {
            LogUtils.i("inject " + target.getClass() + " ok");
        } else if (mBaseInjector.inject(target)) {
            LogUtils.i("inject " + target.getClass() + " ok");
        } else {
            LogUtils.i("inject " + target.getClass() + " fail");
            return false;
        }
        return true;
    }
}
