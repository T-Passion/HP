package com.passion.hp.module.home.model;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.passion.hp.module.home.contract.SubTabFragmentContract;
import com.passion.hp.module.home.model.entity.NewsAllVo;
import com.passion.libbase.constants.BizApiConstant;
import com.passion.libbase.mvp.BaseModel;
import com.passion.libbase.utils.JsonUtil;
import com.passion.libnet.api.HPRestCallback;
import com.passion.libnet.api.NetWorker;
import com.passion.libnet.core.RequestModel;

/**
 * Created by chaos
 * on 2018/1/20. 17:10
 * 文件描述：
 */

public class SubTabFragmentModel extends BaseModel implements SubTabFragmentContract.Model {

    private Context mContext;
    public SubTabFragmentModel(Context context) {
        mContext = context;
    }

    @Override
    public void getData(HPRestCallback callback) {
        RequestModel requestModel = new RequestModel.GetBuilder(BizApiConstant.GET_NBA_NEWS).build();
        NetWorker.getINS().get(requestModel,callback);
    }

    @Override
    public NewsAllVo mockData() {
        JsonUtil jsonUtil = new JsonUtil(new ObjectMapper());
        NewsAllVo newsAllVo = jsonUtil.fromFile(mContext,"news.json",NewsAllVo.class);
        return newsAllVo;
    }
}
