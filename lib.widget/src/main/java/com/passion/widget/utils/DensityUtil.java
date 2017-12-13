package com.passion.widget.utils;

import android.content.Context;

/**
 * Created by chaos
 * on 2017/12/13. 10:44
 * 文件描述：
 */

public class DensityUtil {


    //将pixel转换成dip(dp)
    public static int px2Dip(Context context, float pixelValue) {
        float density = context.getResources().getDisplayMetrics().density;
        int dipValue = (int) (pixelValue / density + 0.5f);
        return dipValue;
    }

    //将dip(dp)转换成pixel
    public static int dip2Px(Context context, float dipValue) {
        if (context == null) {
            return 0;
        }
        float density = context.getResources().getDisplayMetrics().density;
        int pixelValue = (int) (dipValue * density + 0.5f);
        return pixelValue;
    }


    //将pixel转换成sp
    public static int px2Sp(Context context, float pixelValue) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        int sp = (int) (pixelValue / scaledDensity + 0.5f);
        return sp;
    }

    //将sp转换成pixel
    public static int sp2Px(Context context, float spValue) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        int pixelValue = (int) (spValue * scaledDensity + 0.5f);
        return pixelValue;
    }
}
