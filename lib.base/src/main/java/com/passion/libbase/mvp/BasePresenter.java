package com.passion.libbase.mvp;

import android.app.Activity;

import com.trello.rxlifecycle2.RxLifecycle;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by chaos
 * On 17-7-2.
 * Email:iwenchaos@gmail.com
 * Description:
 */

public abstract class BasePresenter<M extends IBaseModel, V extends IBaseView> implements IBasePresenter {

    protected M mModel;
    protected V mView;

    protected CompositeDisposable mCompositeDisposable;

    public BasePresenter(V v) {
        this.mView = v;
        onStart();
    }

    public BasePresenter(M model, V view) {
        mModel = model;
        mView = view;
        onStart();
    }

    @Override
    public void onStart() {

    }


    @Override
    public void onDestroy() {
        unDispose();
        if (mModel != null)
            mModel.onDestroy();
        this.mModel = null;
        this.mView = null;
        this.mCompositeDisposable = null;
    }


    /**
     * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
     * 可在 {@link Activity # onDestroy()} 中使用 {@link #unDispose()} 停止正在执行的 RxJava 任务,避免内存泄漏
     * 目前框架已使用 {@link RxLifecycle} 避免内存泄漏,此方法作为备用方案
     *
     * @param disposable
     */
    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null)
            mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(disposable);
    }

    /**
     * 停止集合中正在执行的rxjava任务
     */
    public void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
        }
    }
}
