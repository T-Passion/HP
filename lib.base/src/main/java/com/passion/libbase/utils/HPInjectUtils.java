package com.passion.libbase.utils;

import com.chaos.base.di.imp.IInjector;

/**
 * Created by huangdou
 * on 2017/10/13.
 */

public class HPInjectUtils {

    private static final String HP_INJECTOR = "com.chaos.ultrohupu.di.HPInjector";

    private static Class<?> mInjector = null;


    /**
     * 在应用初始化的时候，调用
     *
     * @param target
     * @param packageName
     */
    public static void init(Object target, String packageName) {
        init(HP_INJECTOR);
        inject(target);
    }

    /**
     * @param clazzName
     */
    static void init(String clazzName) {
        try {
            mInjector = Class.forName(clazzName);
            mInjector.newInstance();
            mInjector.getDeclaredMethod(IInjector.INIT_COMPONENT).setAccessible(true);
            mInjector.getMethod(IInjector.INIT_COMPONENT).invoke(null);
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }

    /**
     * @param target
     */
    public static void inject(Object target) {
        try {
            mInjector.getDeclaredMethod(IInjector.INJECT, Object.class).setAccessible(true);
            mInjector.getMethod(IInjector.INJECT, Object.class).invoke(null, target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
