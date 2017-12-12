package com.passion.libbase;

import android.app.Application;

/**
 * Created by chaos
 * On 17-7-2.
 * Email:iwenchaos@gmail.com
 * Description:
 */

public class HPApplication extends Application {

    private Configuration mConfiguration;

    @Override
    public void onCreate() {
        super.onCreate();

        mConfiguration = new Configuration.Builder()
                .app(this)
                .buildType(BuildConfig.BUILD_TYPE)
                .build();

        mConfiguration.withLog()
//                .withLeaker()
                .withDagger2()
                .withRouter();

    }


}
