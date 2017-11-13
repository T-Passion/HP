package com.passion.libbase;

import android.app.Application;

import com.passion.libbase.router.HPRouter;
import com.passion.libbase.utils.HPInjectUtils;
import com.passion.libbase.utils.LogUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by chaos
 * On 17-7-2.
 * Email:iwenchaos@gmail.com
 * Description:
 */

public class BaseApplication extends Application {

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        //
        initLeaker();
        //
        initRouter();
        //
        initDagger2();
        //
        initLog();
    }

    private void initRouter() {
        HPRouter.init(this);
    }

    private void initLeaker() {
        mRefWatcher = LeakCanary.install(this);
    }

    private void initDagger2() {
        HPInjectUtils.init(this, getPackageName());
    }

    private void initLog() {
        LogUtils.init(getPackageName(), BuildConfig.DEBUG);
    }
}
