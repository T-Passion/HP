package com.passion.libbase.utils;

import android.content.Context;

import com.passion.libbase.AppEnv;

public class DensityUtil {



    //将pixel转换成dip(dp)
    public static float px2Dp( float pixelValue) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (pixelValue / density + 0.5f);
    }

    //将dip(dp)转换成pixel
    public static float dp2Px( float dipValue) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (dipValue * density + 0.5f);
    }

    //将pixel转换成sp
    public static float px2Sp(float pixelValue) {
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (pixelValue / scaledDensity + 0.5f);
    }

    //将sp转换成pixel
    public static float sp2Px(float spValue) {
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        return  (spValue * scaledDensity + 0.5f);
    }

    private static Context getContext(){
        Context context = AppEnv.app();
        if(context == null){
            throw new RuntimeException("请先初始化AppEnv");
        }
        return context;
    }
}