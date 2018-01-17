package com.passion.hp.module.home.presenter;

import com.passion.hp.module.home.contract.HomeContract;
import com.passion.libbase.mvp.BasePresenter;

/**
 * Created by chaos
 * on 2018/1/17. 10:28
 * 文件描述：
 */

public class HomePresenter extends BasePresenter<HomeContract.Model,HomeContract.View> implements  HomeContract.Presenter{




    public HomePresenter(HomeContract.View view, HomeContract.Model model) {
        super(view, model);
    }
}
