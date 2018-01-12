package com.passion.hp.module.splash.contract;


import com.passion.libbase.mvp.IBaseModel;
import com.passion.libbase.mvp.IBasePresenter;
import com.passion.libbase.mvp.IBaseView;
import com.passion.libnet.core.imp.NetCallback;

/**
 * Created by huangdou
 * on 2017/10/9.
 */

public interface SplashContract {


    /**
     *
     */
    interface Model extends IBaseModel {

        void getSplashRandomRes(NetCallback netCallback);

    }

    /**
     *
     */
    interface View extends IBaseView {

        void updateContent(String imageUrl);

    }

    /**
     *
     */
    interface Presenter extends IBasePresenter {

        void fetchSplashRes();

    }
}
