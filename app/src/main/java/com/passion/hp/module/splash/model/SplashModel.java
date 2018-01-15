package com.passion.hp.module.splash.model;


import com.passion.hp.module.splash.contract.SplashContract;
import com.passion.libbase.constants.BizApiConstant;
import com.passion.libbase.mvp.BaseModel;
import com.passion.libnet.api.NetWorker;
import com.passion.libnet.core.RequestModel;
import com.passion.libnet.core.imp.NetCallback;

/**
 * Created by chaos
 * on 2017/10/10.
 */

public class SplashModel extends BaseModel implements SplashContract.Model {



    public SplashModel() {
    }

    @Override
    public void getSplashRandomRes(NetCallback callback) {
        RequestModel requestModel = new RequestModel
                .GetBuilder(BizApiConstant.GET_STATUS_INIT)
                .build();
        NetWorker.getINS().get(requestModel, callback);

    }
}
