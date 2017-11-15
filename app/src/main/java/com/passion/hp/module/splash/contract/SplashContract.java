package com.passion.hp.module.splash.contract;


import com.passion.libbase.mvp.IBaseModel;
import com.passion.libbase.mvp.IBasePresenter;
import com.passion.libbase.mvp.IBaseView;

/**
 * Created by huangdou
 * on 2017/10/9.
 */

public interface SplashContract {


    /**
     *
     */
    interface Model extends IBaseModel {

        String getSplashRandomRes();

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
