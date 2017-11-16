package com.passion.libnet.core;

import com.passion.libnet.core.imp.Callback;

import java.io.File;

/**
 * Created by chaos
 * on 2017/11/16. 14:27
 * 文件描述：
 */

public abstract class FileCallback extends Callback<File> {
    public FileCallback() {
    }

    protected void onResponse(ResponseModel<File> responseModel) {
    }

    public void onProgress(long currentSize, long totalSize, int progress) {
    }
}
