package com.passion.libnet.core.imp;

import com.passion.libnet.core.FileCallback;
import com.passion.libnet.core.ResponseModel;

import java.io.File;
import java.io.IOException;

/**
 * Created by chaos
 * on 2017/11/16. 14:26
 * 文件描述：
 */

public interface HttpCall<T>  {
    ResponseModel<T> execute() throws IOException;

    void execute(Callback<T> var1);

    ResponseModel download(File var1) throws IOException;

    void download(File var1, FileCallback var2);

    void cancel(Object var1);
}
