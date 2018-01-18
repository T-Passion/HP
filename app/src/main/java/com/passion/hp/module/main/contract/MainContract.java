package com.passion.hp.module.main.contract;

import com.passion.libbase.mvp.IBaseModel;
import com.passion.libbase.mvp.IBasePresenter;
import com.passion.libbase.mvp.IBaseView;
import com.passion.libnet.api.HPRestCallback;

/**
 * Created by chaos
 * on 2017/12/19. 14:30
 * 文件描述：
 */

public interface MainContract {

    /**
    *
    */
    interface Model extends IBaseModel {

        /**
         * 获取应用初始数据
         */
        void loadStatusInit(HPRestCallback callback);
    }

    /**
    *
    */
    interface View extends IBaseView {

    }
    /**
    *
    */
    interface Presenter extends IBasePresenter {

        void getStatusInit();

    }

}
