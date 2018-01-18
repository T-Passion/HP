package com.passion.hp.module.main.presenter;

import com.passion.hp.module.main.contract.MainContract;
import com.passion.libbase.mvp.BasePresenter;

/**
 * Created by chaos
 * on 2017/12/19. 14:34
 * 文件描述：
 */

public class MainPresenter extends BasePresenter<MainContract.Model,MainContract.View> implements MainContract.Presenter {


    public MainPresenter(MainContract.View view) {
        super(view);
    }

    public MainPresenter(MainContract.View view, MainContract.Model model) {
        super(view, model);
    }

    @Override
    public void getStatusInit() {

    }
}
