package com.passion.hp.module.splash.presenter;


import com.passion.hp.module.splash.contract.SplashContract;
import com.passion.hp.module.splash.model.entity.SplashVo;
import com.passion.libbase.mvp.BasePresenter;
import com.passion.libnet.api.HPRestCallback;
import com.passion.libnet.core.exception.ErrorBody;

/**
 * Created by huangdou
 * on 2017/10/10.
 */

public class SplashPresenter extends BasePresenter<SplashContract.Model, SplashContract.View> implements SplashContract.Presenter {



    public SplashPresenter(SplashContract.View view) {
        super(view);
    }

    public SplashPresenter( SplashContract.View view,SplashContract.Model model) {
        super( view,model);
    }

    @Override
    public void fetchSplashRes() {
//        mView.showNetLoading();
        mModel.getSplashRandomRes(new HPRestCallback<SplashVo>() {
            @Override
            public void onSuccess(SplashVo result) {
//                mView.closeLoading(true);
                mView.updateContent(result.getUrl());
            }

            @Override
            public void onFailure(ErrorBody errorBody) {
//                mView.closeLoading(false);
                mView.updateContent(SplashVo.SPLASH_IMG.SPLASH_LIST.get((int) (Math.random()*1)));
            }
        });

    }
}
