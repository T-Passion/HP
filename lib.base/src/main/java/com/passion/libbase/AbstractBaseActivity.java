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

import com.passion.libbase.imp.OnNetReconnectListener;
import com.passion.libbase.mvp.IBaseView;
import com.passion.libbase.router.HPRouter;
import com.passion.libbase.utils.HPInjectUtils;
import com.passion.widget.main.WidActionTitleBar;
import com.passion.widget.main.WidNetProgressView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chaos
 * on 17-7-2.
 */

public abstract class AbstractBaseActivity extends FragmentActivity implements OnNetReconnectListener, IBaseView {
    protected Context mContext;

    LinearLayout mRootContentView;//根视图
    FrameLayout mContentLayout;//内容视图
    WidActionTitleBar mActionTitleBar;//应用标题栏
    FrameLayout mEmptyLayout;//空数据内容显示
    WidNetProgressView mProgressView;//刷新内容显示
    FrameLayout mProgressLayout;//刷新内容显示

    private Unbinder mViewUnbind;
    //有数据内容部分
    protected ViewGroup mContentView;
    //当前页面布局id
    protected int mContentLayoutId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_layout);
        findView();
        injector();
        mContentView = inflateUiBind();
        ButterKnife.bind(this,mContentView);
        initThings(mContentLayout);
        loadInitDta();
    }
    private void findView(){
        mRootContentView = (LinearLayout) findViewById(R.id.rootContentView);
        mContentLayout = (FrameLayout) findViewById(R.id.contentLayout);
        mActionTitleBar = (WidActionTitleBar) findViewById(R.id.actionTitleBar);
        mEmptyLayout = (FrameLayout) findViewById(R.id.emptyLayout);
        mProgressView = (WidNetProgressView) findViewById(R.id.progressView);
        mProgressLayout = (FrameLayout) findViewById(R.id.progressLayout);
    }

    private void injector() {
        //路由
        HPRouter.inject(this);
        //注入
        HPInjectUtils.inject(this);
    }


    private ViewGroup inflateUiBind() {
        mContentLayoutId = getContentLayoutId();
        ViewGroup vg = (ViewGroup) LayoutInflater.from(this).inflate(mContentLayoutId, mContentLayout, true);
        return vg;

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

    public View getRootView(){
        return mRootContentView;
    }

    public View getContentLayout(){
        return mContentLayout;
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

    /**
     * 是否展示空页面
     * @param showEmpty true：展示，false：隐藏
     */
    public void setEmptyUI(boolean showEmpty) {
        setEmptyLayout(showEmpty, -1, -1);
    }

    /**
     * 设置空页面
     * @param showEmpty true：展示，false：隐藏
     * @param emptyTxtId 文案
     * @param emptyIcon 图标
     */
    public void setEmptyLayout(boolean showEmpty, Integer emptyTxtId, Integer emptyIcon) {
        setEmptyLayout(showEmpty, getString(emptyTxtId), emptyIcon);
    }

    /**
     * 设置空页面
     * @param showEmpty true：展示，false：隐藏
     * @param emptyTxt 文案
     * @param emptyIcon 图标
     */
    public void setEmptyLayout(boolean showEmpty, String emptyTxt, Integer emptyIcon) {
        setEmptyLayout(showEmpty, emptyTxt, -1, emptyIcon);
    }

    /**
     * 设置空页面
     * @param showEmpty true：展示，false：隐藏
     * @param emptyTxt 文案
     * @param emptyTxtColor 文案色值
     * @param emptyIcon 图标
     */
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
