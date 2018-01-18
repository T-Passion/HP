package com.passion.hp.module.home.contract;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.passion.hp.module.home.model.bean.TabVo;
import com.passion.libbase.mvp.IBaseModel;
import com.passion.libbase.mvp.IBasePresenter;
import com.passion.libbase.mvp.IBaseView;
import com.passion.libnet.api.HPRestCallback;

import java.util.List;

/**
 * Created by chaos
 * on 2017/12/19. 11:06
 * 文件描述：
 */

public interface HomeContract {


    /**
     *
     */
    interface Model extends IBaseModel {
        /**
         * 获取home页的tab数据
         *
         * @param callback 回调
         */
        void getHomeTabs(HPRestCallback callback);

        List<TabVo> getMockHomeTabs();


        List<Fragment> getMockHomePagers();


        void getNBANewsList(HPRestCallback callback);

    }

    /**
     *
     */
    interface View extends IBaseView {

        void renderTabPagers(@NonNull List<TabVo> tabs, List<Fragment> pagers);

        void renderContent();

    }

    /**
     *
     */
    interface Presenter extends IBasePresenter {
        /**
         * 获取home页的tab数据
         */
        void getHomeTabs();


        void getNBANewsList();
    }
}
