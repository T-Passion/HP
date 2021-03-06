package com.passion.libbase.utils;


import com.passion.libbase.di.imp.IInjector;

/**
 * Created by huangdou
 * on 2017/10/13.
 */

public class HPInjectUtil {

    private static final String HP_INJECTOR = "com.passion.hp.di.global.HPInjector";

    private static Class<?> mInjector = null;

    private static Object sReceiver;

    /**
     * 在应用初始化的时候，调用
     */
    public static void init() {
        init(HP_INJECTOR);
    }

    /**
     * @param clazzName
     */
    private static void init(String clazzName) {
        try {
            mInjector = Class.forName(clazzName);
            sReceiver = mInjector.newInstance();
            mInjector.getDeclaredMethod(IInjector.INIT_COMPONENT).setAccessible(true);
            mInjector.getMethod(IInjector.INIT_COMPONENT).invoke(sReceiver);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("dagger初始化失败");
        }
    }

    /**
     * @param target
     */
    public static void inject(Object target) {
        try {
            mInjector.getDeclaredMethod(IInjector.INJECT, Object.class).setAccessible(true);
            mInjector.getMethod(IInjector.INJECT, Object.class).invoke(sReceiver,target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
