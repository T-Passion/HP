package com.passion.libbase.di;


import com.passion.libbase.BaseApplication;
import com.passion.libbase.di.imp.IInjector;
import com.passion.libbase.utils.LogUtils;

/**
 * Created by huangdou
 * on 2017/10/13.
 */

public class BaseInjector implements IInjector {

    private BaseComponent sBaseComponent;
    private BaseModule sBaseModule;

    @Override
    public void initComponent() {

    }

    public BaseModule getModule() {
        if (sBaseModule == null) {
            synchronized (BaseInjector.class) {
                if (sBaseModule == null) {
                    sBaseModule = new BaseModule();
                }
            }
        }
        return sBaseModule;
    }

    public void setComponent(BaseComponent baseComponent) {
        sBaseComponent = baseComponent;
    }

    @Override
    public boolean inject(Object target) {
        LogUtils.i("start Base injector");
        Class tClass = target.getClass();
        if (tClass == BaseApplication.class) {
            sBaseComponent.inject((BaseApplication) target);
        } else {
            return false;
        }
        return true;
    }
}
