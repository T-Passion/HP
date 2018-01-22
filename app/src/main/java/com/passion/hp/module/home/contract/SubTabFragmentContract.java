package com.passion.hp.module.home.contract;

import com.passion.hp.module.home.model.entity.NewsAllVo;
import com.passion.libbase.mvp.IBaseModel;
import com.passion.libbase.mvp.IBasePresenter;
import com.passion.libbase.mvp.IBaseView;
import com.passion.libnet.api.HPRestCallback;

/**
 * Created by chaos
 * on 2018/1/20. 16:51
 * 文件描述：
 */

public interface SubTabFragmentContract {


    /**
    *
    */
    interface Model extends IBaseModel {

        void getData(HPRestCallback callback);

        NewsAllVo mockData();

    }

    /**
    *
    */
    interface View extends IBaseView {

        void render(NewsAllVo newsAllVo);

    }
    /**
    *
    */
    interface Presenter extends IBasePresenter {

        void getDta();

    }
}
