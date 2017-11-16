package com.passion.libnet.core.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * Created by chaos
 * on 2017/11/16. 18:39
 * 文件描述：
 */

public class Platform {
    private static final Platform INSTANCE = new Platform();

    private Platform() {
    }

    public static Platform getInstance() {
        return INSTANCE;
    }

    public Executor getCallbackExecutor() {
        return new Platform.MainThreadExecutor();
    }

    private static class MainThreadExecutor implements Executor {
        private final Handler handler;

        private MainThreadExecutor() {
            this.handler = new Handler(Looper.getMainLooper());
        }

        public void execute(Runnable command) {
            this.handler.post(command);
        }
    }
}
