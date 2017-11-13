package com.passion.hp.splash.ui;

import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.passion.hp.R;
import com.passion.hp.splash.contract.SplashContract;
import com.passion.hp.splash.model.SplashModel;
import com.passion.hp.splash.presenter.SplashPresenter;
import com.passion.libbase.AbstractBaseActivity;
import com.passion.widget.main.WidJumpView;

import butterknife.BindView;

/**
 * Created by huangdou
 * on 2017/10/9.
 * 启动页：启动三秒后，进入home
 */

@Route(path = com.passion.hp.constant.RouterPath.SPLASH_ACTIVITY)
public class SplashActivity extends AbstractBaseActivity implements com.passion.hp.splash.contract.SplashContract.View {


    @BindView(R.id.splashAdImg)
    ImageView mSplashAdImg;
    @BindView(R.id.splashLogo)
    ImageView mSplashLogo;
    @BindView(R.id.splashJump)
    WidJumpView mSplashJump;

    SplashModel mModel = new SplashModel();

    SplashContract.Presenter mPresenter;


    @Override
    public int getContentLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initThings(View view) {
        mPresenter = new SplashPresenter(mModel, this);
    }


    @Override
    protected void loadInitDta() {
        mPresenter.fetchSplashRes();
    }

    @Override
    public void updateContent(String imageUrl) {
        Glide.with(this).load(imageUrl).into(mSplashAdImg);
    }
}
