package com.passion.libbase.di;


import com.passion.libbase.AbstractBaseActivity;
import com.passion.libbase.AbstractBaseFragment;
import com.passion.libbase.HPApplication;
import com.passion.libbase.di.imp.IInjector;
import com.passion.libbase.utils.LogUtil;

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
        LogUtil.i("start Base injector");
        Class tClass = target.getClass();
        if (tClass == HPApplication.class) {
            sBaseComponent.inject((HPApplication) target);
        } else if(tClass == AbstractBaseActivity.class){
            sBaseComponent.inject((AbstractBaseActivity) target);
        } else if(tClass == AbstractBaseFragment.class){
            sBaseComponent.inject((AbstractBaseFragment) target);
        } else{
            return false;
        }
        return true;
    }
}
