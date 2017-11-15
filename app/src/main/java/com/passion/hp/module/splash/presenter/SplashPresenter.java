package com.passion.hp.module.splash.presenter;


import com.passion.hp.module.splash.contract.SplashContract;
import com.passion.libbase.mvp.BasePresenter;

/**
 * Created by huangdou
 * on 2017/10/10.
 */

public class SplashPresenter extends BasePresenter<SplashContract.Model, SplashContract.View> implements SplashContract.Presenter {



    public SplashPresenter(SplashContract.View view) {
        super(view);
    }

    public SplashPresenter(SplashContract.Model model, SplashContract.View view) {
        super(model, view);
    }

    @Override
    public void fetchSplashRes() {
        String imageUrl = mModel.getSplashRandomRes();
        mView.updateContent(imageUrl);
    }
}
