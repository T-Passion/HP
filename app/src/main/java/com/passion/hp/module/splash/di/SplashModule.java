package com.passion.hp.module.splash.di;

import com.passion.hp.module.splash.contract.SplashContract;
import com.passion.hp.module.splash.presenter.SplashPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chaos
 * on 2017/12/13. 14:41
 * 文件描述：
 */
@Module
public class SplashModule {

    SplashContract.View mSplashView;

    public SplashModule(SplashContract.View splashView) {
        mSplashView = splashView;
    }

    @Provides
    public SplashContract.View provideView(){
        return mSplashView;
    }

    @Provides
    public SplashContract.Presenter providePresenter(SplashContract.View view,SplashContract.Model model){
        return new SplashPresenter(view,model);
    }


}
