package com.passion.libbase;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaos.base.utils.HPInjectUtils;
import com.chaos.base.imp.OnNetReconnectListener;
import com.chaos.base.mvp.IBaseView;
import com.chaos.base.router.HPRouter;
import com.chaos.widget.main.WidActionTitleBar;
import com.chaos.widget.main.WidNetProgressView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chaos
 * on 17-7-2.
 */

public abstract class AbstractBaseActivity extends FragmentActivity implements OnNetReconnectListener, IBaseView {
    protected Context mContext;

    LinearLayout mRootContentView;//根视图
    @BindView(R2.id.contentLayout)
    FrameLayout mContentLayout;//内容视图
    @BindView(R2.id.actionTitleBar)
    WidActionTitleBar mActionTitleBar;//应用标题栏
    @BindView(R2.id.emptyLayout)
    FrameLayout mEmptyLayout;//空数据内容显示
    @BindView(R2.id.progressView)
    WidNetProgressView mProgressView;//刷新内容显示
    @BindView(R2.id.progressLayout)
    FrameLayout mProgressLayout;//刷新内容显示

    private Unbinder mViewUnbind;
    //有数据内容部分
    protected ViewGroup mContentView;
    //当前页面布局id
    protected int contentLayoutId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_layout);
        initRootView();
        initInjector();
        inflateContentUi();
        mViewUnbind = ButterKnife.bind(this, mContentView);


        initThings(mContentLayout);
        loadInitDta();
    }

    private void initRootView() {
        mRootContentView = (LinearLayout) findViewById(R.id.rootContentView);
        mRootContentView = (LinearLayout) findViewById(R.id.rootContentView);
        mRootContentView = (LinearLayout) findViewById(R.id.rootContentView);
    }

    private void initInjector() {
        //路由
        HPRouter.inject(this);
        //
        HPInjectUtils.inject(this);
    }


    private void inflateContentUi() {
        contentLayoutId = getContentLayoutId();
        mContentView = (ViewGroup) LayoutInflater.from(this).inflate(contentLayoutId, mContentLayout, true);
    }

    /**
     * @return 获得当前页面的布局
     */
    public abstract int getContentLayoutId();

    /**
     * 初始化当前页面的布局设置
     *
     * @param view 当前页面视图
     */
    protected abstract void initThings(View view);

    /**
     * 加载初始数据
     */
    protected abstract void loadInitDta();

    public Context getContext() {
        return mContext;
    }


    /**
     * @param title 标题
     */
    protected void setTitleBar(String title) {
        mActionTitleBar.setTitleBar(title);
    }

    /**
     * @param title     标题
     * @param titleIcon 标题图片
     */
    public void setTitleBar(String title, Integer titleIcon) {
        mActionTitleBar.setTitleBar(title, titleIcon);
    }

    /**
     * @param title        标题
     * @param titleIcon    标题图片
     * @param leftListener 监听器
     */
    public void setTitleBarLeft(String title, Integer titleIcon, View.OnClickListener leftListener) {
        mActionTitleBar.setTitleBarLeft(title, titleIcon, leftListener);
    }

    /**
     * @param titleId      标题资源id
     * @param titleIcon    标题图片
     * @param leftListener 监听器
     */
    public void setTitleBarLeft(int titleId, Integer titleIcon, View.OnClickListener leftListener) {
        mActionTitleBar.setTitleBarLeft(titleId, titleIcon, leftListener);
    }

    /**
     * @param title         标题
     * @param titleIcon     标题图片
     * @param rightListener 监听器
     */
    public void setTitleBarRight(String title, Integer titleIcon, View.OnClickListener rightListener) {
        mActionTitleBar.setTitleBarRight(title, titleIcon, rightListener);
    }

    /**
     * @param titleId       标题资源id
     * @param titleIcon     标题图片
     * @param rightListener 监听器
     */
    public void setTitleBarRight(int titleId, Integer titleIcon, View.OnClickListener rightListener) {
        mActionTitleBar.setTitleBarRight(titleId, titleIcon, rightListener);
    }

    /**
     * @param leftListener 监听器
     */
    public void setTitleLeftListener(View.OnClickListener leftListener) {
        mActionTitleBar.setOnClickListener(leftListener);
    }

    /**
     * @param rightListener 监听器
     */
    public void setTitleRightListener(View.OnClickListener rightListener) {
        mActionTitleBar.setOnClickListener(rightListener);
    }


    public void setEmptyUI(boolean showEmpty) {
        setEmptyLayout(showEmpty, -1, -1);
    }


    public void setEmptyLayout(boolean showEmpty, Integer emptyTxtId, Integer emptyIcon) {
        setEmptyLayout(showEmpty, getString(emptyTxtId), emptyIcon);
    }

    public void setEmptyLayout(boolean showEmpty, String emptyTxt, Integer emptyIcon) {
        setEmptyLayout(showEmpty, emptyTxt, -1, emptyIcon);
    }

    public void setEmptyLayout(boolean showEmpty, String emptyTxt, @ColorInt Integer emptyTxtColor, Integer emptyIcon) {
        if (showEmpty) {
            mProgressLayout.setVisibility(View.GONE);
            mContentLayout.setVisibility(View.GONE);
            mEmptyLayout.setVisibility(View.VISIBLE);

            if (!TextUtils.isEmpty(emptyTxt)) {
                ((TextView) mEmptyLayout.findViewById(R.id.emptyTxt)).setText(emptyTxt);
            } else {/*default*/}
            if (emptyTxtColor != null) {
                ((TextView) mEmptyLayout.findViewById(R.id.emptyTxt)).setTextColor(emptyTxtColor);
            } else {/*default*/}
            if (emptyIcon != null) {
                ((ImageView) mEmptyLayout.findViewById(R.id.emptyImg)).setImageResource(emptyIcon);
            } else {/*default*/}

        } else {
            mProgressLayout.setVisibility(View.GONE);
            mEmptyLayout.setVisibility(View.GONE);
            mContentLayout.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void showNetLoading(boolean show) {
        if (show) {
            mProgressLayout.setVisibility(View.VISIBLE);
            mEmptyLayout.setVisibility(View.GONE);
            mContentLayout.setVisibility(View.GONE);
        } else {
            mProgressLayout.setVisibility(View.GONE);
        }
    }

    /**
     * @param eventType 事件类型
     * @param params    参数
     */
    @Override
    public void onReconnect(String eventType, List params) {
        //默认不处理
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mViewUnbind != null) {
            mViewUnbind.unbind();
        }
    }
}
