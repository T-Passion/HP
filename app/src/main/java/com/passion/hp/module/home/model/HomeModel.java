package com.passion.hp.module.home.model;

import android.support.v4.app.Fragment;

import com.passion.hp.module.home.contract.HomeContract;
import com.passion.hp.module.home.model.entity.TabVo;
import com.passion.hp.module.home.ui.SubTabListFragment;
import com.passion.libbase.constants.BizApiConstant;
import com.passion.libnet.api.HPRestCallback;
import com.passion.libnet.api.NetWorker;
import com.passion.libnet.core.RequestModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by chaos
 * on 2018/1/17. 10:28
 * 文件描述：
 */

public class HomeModel implements HomeContract.Model {



    @Override
    public void getHomeTabs(HPRestCallback callback) {
        RequestModel requestModel = new RequestModel
                .GetBuilder(BizApiConstant.GET_HOME_TABS)
                .build();
        NetWorker.getINS().get(requestModel,callback);
    }

    @Override
    public List<TabVo> getMockHomeTabs() {
        List<TabVo> tabs = new ArrayList<>();
        tabs.add(new TabVo("NBA","105601"));
        tabs.add(new TabVo("关注","105602"));
        tabs.add(new TabVo("路人王","105603"));
        tabs.add(new TabVo("中国篮球","105604"));
        tabs.add(new TabVo("王者荣耀","105605"));
        tabs.add(new TabVo("英雄联盟","105606"));
        tabs.add(new TabVo("欧冠","105607"));
        return tabs;
    }

    @Override
    public List<Fragment> getMockHomePagers() {
        List<Fragment> pagers = new ArrayList<>();
        pagers.add(SubTabListFragment.newInstance());
        pagers.add(SubTabListFragment.newInstance());
        pagers.add(SubTabListFragment.newInstance());
        pagers.add(SubTabListFragment.newInstance());
        pagers.add(SubTabListFragment.newInstance());
        pagers.add(SubTabListFragment.newInstance());
        pagers.add(SubTabListFragment.newInstance());
        return pagers;
    }

    @Override
    public void getNBANewsList(HPRestCallback callback) {

    }

    @Override
    public void onDestroy() {

    }
}
