package com.passion.libbase;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by chaos
 * On 17-7-2.
 * Email:iwenchaos@gmail.com
 * Description:
 */

public class HPApplication extends Application {

    private Configuration mConfiguration;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mConfiguration = new Configuration.Builder()
                .app(this)
                .buildType(BuildConfig.BUILD_TYPE)
                .build();

        mConfiguration.withLog()
                .withLeaker()
                .withNet()
                .withDagger2()
                .withRouter();

    }


}
