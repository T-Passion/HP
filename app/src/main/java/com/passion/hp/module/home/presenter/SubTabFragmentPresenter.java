package com.passion.hp.module.home.presenter;

import com.passion.hp.module.home.contract.SubTabFragmentContract;
import com.passion.hp.module.home.model.entity.NewsAllVo;
import com.passion.libbase.mvp.BasePresenter;
import com.passion.libnet.api.HPRestCallback;
import com.passion.libnet.core.exception.ErrorBody;

/**
 * Created by chaos
 * on 2018/1/20. 17:10
 * 文件描述：
 */

public class SubTabFragmentPresenter extends BasePresenter<SubTabFragmentContract.Model, SubTabFragmentContract.View> implements SubTabFragmentContract.Presenter {

    public SubTabFragmentPresenter(SubTabFragmentContract.View view) {
        super(view);
    }

    @Override
    public void onStart() {
        getDta();
    }

    public SubTabFragmentPresenter(SubTabFragmentContract.View view, SubTabFragmentContract.Model model) {
        super(view, model);
    }

    @Override
    public void getDta() {
        mView.showNetLoading();
        mModel.getData(new HPRestCallback<NewsAllVo>() {
            @Override
            public void onSuccess(NewsAllVo result) {
                mView.closeLoading();


            }

            @Override
            public void onFailure(ErrorBody error) {
                mView.closeLoading();
                mView.render(mModel.mockData());

            }
        });
    }
}
