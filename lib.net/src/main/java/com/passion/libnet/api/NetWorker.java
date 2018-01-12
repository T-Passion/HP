package com.passion.libnet.api;


import com.passion.libnet.core.NetConfig;
import com.passion.libnet.core.NetWrapper;
import com.passion.libnet.core.RequestModel;
import com.passion.libnet.core.imp.INetWorker;
import com.passion.libnet.core.imp.NetCallback;
import com.passion.libnet.core.okhttp.OkHttpInternal;

/**
 * Created by chaos
 * on 2017/11/15. 15:48
 * 文件描述：
 */

public class NetWorker implements INetWorker {

    private NetConfig mNetConfig;

    private NetWorker() {
        mNetConfig = NetWrapper.getConfig();
    }


    private static final class Holder {
        static final NetWorker NET_WORKER = new NetWorker();
    }


    public static NetWorker getINS() {
        return Holder.NET_WORKER;
    }


    @Override
    public <T> void get( RequestModel requestModel, NetCallback<T> netCallback) {
        this.execute(requestModel, netCallback);
    }

    @Override
    public <T> void post(RequestModel requestModel, NetCallback<T> netCallback) {
        this.execute(requestModel,netCallback);
    }

    private <T> void  execute(RequestModel requestModel, NetCallback<T> netCallback) {
        new OkHttpInternal<>(requestModel,netCallback).execute();
    }

}

