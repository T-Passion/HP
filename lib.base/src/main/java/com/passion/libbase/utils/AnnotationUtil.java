package com.passion.libbase.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.passion.libbase.imp.LayoutId;

/**
 * Created by chaos
 * on 2017/11/14. 15:22
 * 文件描述：
 */

public class AnnotationUtil {
    /**
     * 获取注解Id
     *
     * @param activity
     * @return
     */
    public static int getLayoutId(Activity activity) {
        return layoutId(activity);
    }

    public static int getLayoutId(Fragment fragment) {
        return layoutId(fragment);
    }

    private static int layoutId(Object o) {
        Class<?> activityClass = o.getClass();
        LayoutId layoutId = activityClass.getAnnotation(LayoutId.class);
        while (layoutId == null) {
            activityClass = activityClass.getSuperclass();
            if (activityClass == null) {
                return -1;
            } else {
                layoutId = activityClass.getAnnotation(LayoutId.class);
            }
        }
        return layoutId.value();
    }


}

