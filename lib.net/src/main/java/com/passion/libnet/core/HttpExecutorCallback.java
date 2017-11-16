package com.passion.libnet.core;

import com.passion.libnet.core.imp.Callback;
import com.passion.libnet.core.imp.HttpCall;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * Created by chaos
 * on 2017/11/16. 18:41
 * 文件描述：
 */

public class HttpExecutorCallback<T> implements HttpCall<T> {
    private final Executor executor;
    private final HttpCall<T> delegate;

    HttpExecutorCallback(Executor executor, HttpCall<T> delegate) {
        this.executor = executor;
        this.delegate = delegate;
    }

    public ResponseModel<T> execute() throws IOException {
        return this.delegate.execute();
    }

    public void execute(final Callback<T> callback) {
        this.delegate.execute(new Callback<T>() {
            public void onResponse(final ResponseModel<T> responseModel) {
                HttpExecutorCallback.this.executor.execute(new Runnable() {
                    public void run() {
                        if(callback != null) {
                            callback.onResponse(responseModel);
                        }

                    }
                });
            }

            public void onSuccess(final T data) {
                HttpExecutorCallback.this.executor.execute(new Runnable() {
                    public void run() {
                        if(callback != null) {
                            callback.onSuccess(data);
                        }

                    }
                });
            }

            public void onFailure(final Throwable t) {
                HttpExecutorCallback.this.executor.execute(new Runnable() {
                    public void run() {
                        if(callback != null) {
                            callback.onFailure(t);
                        }

                    }
                });
            }
        });
    }

    public void download(File file, final FileCallback callback) {
        final HttpExecutorCallback.DownloadProgressRunnable runnable = new HttpExecutorCallback.DownloadProgressRunnable(callback);
        this.delegate.download(file, new FileCallback() {
            public void onProgress(long currentSize, long totalSize, int progress) {
                runnable.currentSize = currentSize;
                runnable.totalSize = totalSize;
                runnable.progress = progress;
                HttpExecutorCallback.this.executor.execute(runnable);
            }

            public void onResponse(final ResponseModel responseModel) {
                HttpExecutorCallback.this.executor.execute(new Runnable() {
                    public void run() {
                        if(callback != null) {
                            callback.onResponse(responseModel);
                        }

                    }
                });
            }

            public void onSuccess(final File data) {
                HttpExecutorCallback.this.executor.execute(new Runnable() {
                    public void run() {
                        if(callback != null) {
                            callback.onSuccess(data);
                        }

                    }
                });
            }

            public void onFailure(final Throwable t) {
                HttpExecutorCallback.this.executor.execute(new Runnable() {
                    public void run() {
                        if(callback != null) {
                            callback.onFailure(t);
                        }

                    }
                });
            }
        });
    }

    public ResponseModel download(File file) throws IOException {
        return this.delegate.download(file);
    }

    public void cancel(Object tag) {
        this.delegate.cancel(tag);
    }

    private static class DownloadProgressRunnable implements Runnable {
        long currentSize;
        long totalSize;
        int progress;
        FileCallback callback;

        DownloadProgressRunnable(FileCallback callback) {
            this.callback = callback;
        }

        public void run() {
            if(this.callback != null) {
                this.callback.onProgress(this.currentSize, this.totalSize, this.progress);
            }

        }
    }
}