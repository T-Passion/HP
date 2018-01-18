package com.passion.hp.module.home.presenter;

import com.passion.hp.module.home.contract.HomeContract;
import com.passion.hp.module.home.model.bean.TabVo;
import com.passion.libbase.mvp.BasePresenter;
import com.passion.libnet.api.HPRestCallback;
import com.passion.libnet.core.exception.ErrorBody;

import java.util.List;

/**
 * Created by chaos
 * on 2018/1/17. 10:28
 * 文件描述：
 */

public class HomePresenter extends BasePresenter<HomeContract.Model,HomeContract.View> implements  HomeContract.Presenter{




    public HomePresenter(HomeContract.View view, HomeContract.Model model) {
        super(view, model);
    }

    @Override
    public void getHomeTabs() {
        mView.showNetLoading();
        mModel.getHomeTabs(new HPRestCallback<List<TabVo>>() {
            @Override
            public void onSuccess(List<TabVo> result) {
                mView.closeLoading();
//                mView.renderTabPagers(result);
                getNBANewsList();
            }

            @Override
            public void onFailure(ErrorBody error) {
                mView.closeLoading();
                mView.renderTabPagers(mModel.getMockHomeTabs(),mModel.getMockHomePagers());

            }
        });
    }

    @Override
    public void getNBANewsList() {
        mView.showNetLoading();
        mModel.getNBANewsList(new HPRestCallback<List<TabVo>>() {
            @Override
            public void onSuccess(List<TabVo> result) {

            }

            @Override
            public void onFailure(ErrorBody error) {

            }
        });
    }
}
