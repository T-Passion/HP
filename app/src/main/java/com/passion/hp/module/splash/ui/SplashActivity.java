package com.passion.hp.module.splash.ui;

import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.passion.hp.R;
import com.passion.hp.module.splash.contract.SplashContract;
import com.passion.hp.module.splash.model.SplashModel;
import com.passion.hp.module.splash.presenter.SplashPresenter;
import com.passion.libbase.AbstractBaseActivity;
import com.passion.libbase.constants.RouterPath;
import com.passion.libbase.imp.LayoutId;
import com.passion.libbase.router.HPRouter;
import com.passion.widget.main.WidJumpView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by chaos
 * on 2017/10/9.
 * 启动页：启动三秒后，进入home
 */
@LayoutId(R.layout.activity_splash_layout)
@Route(path = RouterPath.SPLASH_ACTIVITY)
public class SplashActivity extends AbstractBaseActivity implements SplashContract.View {


    @BindView(R.id.splashAdImg)
    ImageView mSplashAdImg;
    @BindView(R.id.splashLogo)
    ImageView mSplashLogo;
    @BindView(R.id.splashJump)
    WidJumpView mSplashJump;

    SplashModel mModel = new SplashModel();

    SplashContract.Presenter mPresenter;

    @Override
    protected void initVars(View view) {
        fullScreen(view);
        mPresenter = new SplashPresenter(this,mModel);
        mSplashJump.setJumpAction(new WidJumpView.OnJumpAction() {
            @Override
            public void onEnd() {
                HPRouter.navigate(RouterPath.HOME_ACTIVITY);
                finish();
            }
        }).start();
    }

    private void fullScreen(View view){
        setTitleBarVisibility(false);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
    @OnClick(R.id.splashAdImg)
    public void onADClick(){
        //TODO 广告位
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
