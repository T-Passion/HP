package com.passion.hp.module.home.presenter;

import com.passion.hp.module.home.contract.SubTabFragmentContract;
import com.passion.libbase.mvp.BasePresenter;

/**
 * Created by chaos
 * on 2018/1/20. 17:10
 * 文件描述：
 */

public class SubTabFragmentPresenter extends BasePresenter<SubTabFragmentContract.Model,SubTabFragmentContract.View> {

    public SubTabFragmentPresenter(SubTabFragmentContract.View view) {
        super(view);
    }

    public SubTabFragmentPresenter(SubTabFragmentContract.View view, SubTabFragmentContract.Model model) {
        super(view, model);
    }
}
