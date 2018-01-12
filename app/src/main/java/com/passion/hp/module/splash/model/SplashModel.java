package com.passion.hp.module.splash.model;


import com.passion.hp.module.splash.contract.SplashContract;
import com.passion.libbase.constants.BizApiConstant;
import com.passion.libbase.mvp.BaseModel;
import com.passion.libnet.api.NetWorker;
import com.passion.libnet.core.RequestModel;
import com.passion.libnet.core.imp.NetCallback;
import com.passion.libnet.core.request.RequestVar;

import javax.inject.Inject;

/**
 * Created by huangdou
 * on 2017/10/10.
 */

public class SplashModel extends BaseModel implements SplashContract.Model {



    @Inject
    public SplashModel() {
    }

    @Override
    public void getSplashRandomRes(NetCallback callback) {
        RequestModel requestModel = new RequestModel
                .GetBuilder(BizApiConstant.GET_COVER_AD_VALUE)
                .version(RequestVar.VERSION_V2)
                .addUrlParameter("page",String.valueOf(1))
                .addUrlParameter("page_size",String.valueOf(10))
                .build();
        NetWorker.getINS().get(requestModel, callback);
    }
}
