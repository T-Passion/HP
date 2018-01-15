package com.passion.libbase;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.passion.libbase.imp.OnNetReconnectListener;
import com.passion.libbase.mvp.IBaseView;
import com.passion.libbase.router.HPRouter;
import com.passion.libbase.utils.AnnotationUtil;
import com.passion.libbase.utils.HPInjectUtil;
import com.passion.libutils.ActvStackUtil;
import com.passion.widget.main.WidActionTitleBar;
import com.passion.widget.main.WidNetProgressView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chaos
 * on 17-7-2.
 */

public abstract class AbstractBaseActivity extends AppCompatActivity implements OnNetReconnectListener, IBaseView {

    LinearLayout mRootContentView;//根视图
    LinearLayout mContentLayout;//内容视图
    View mContentView;//内容
    WidActionTitleBar mActionTitleBar;//应用标题栏
    FrameLayout mEmptyLayout;//空数据内容显示
    WidNetProgressView mProgressView;//刷新内容显示
    FrameLayout mProgressLayout;//刷新内容显示

    private Unbinder mViewUnbind;
    //当前页面布局id
    protected int mContentLayoutId;

    @Inject
    public EventBus mEventBus;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_layout);
        immerseTheme();
        findView();
        injector();
        inflateUiBind();
        //子类实现
        initVars(mContentView);
        loadInitDta();
    }

    public void fullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void findView() {
        mRootContentView = (LinearLayout) findViewById(R.id.rootContentView);
        mActionTitleBar = (WidActionTitleBar) findViewById(R.id.actionTitleBar);
        mContentLayout = (LinearLayout) findViewById(R.id.contentLayout);
        mEmptyLayout = (FrameLayout) findViewById(R.id.emptyLayout);
        mProgressView = (WidNetProgressView) findViewById(R.id.progressView);
        mProgressLayout = (FrameLayout) findViewById(R.id.progressLayout);
    }

    private void injector() {
        //注入
        HPInjectUtil.inject(this);
        //路由
        HPRouter.inject(this);
        //activity栈 管理
        ActvStackUtil.addActivity(this);


    }


    private void inflateUiBind() {
        mContentLayoutId = getLayoutId();
        mContentView = getLayoutInflater().inflate(mContentLayoutId, mContentLayout, true);
        mViewUnbind = ButterKnife.bind(this, mContentView);
    }

    /**
     * @return 获得当前页面的布局
     */
    private int getLayoutId() {
        int layoutId = AnnotationUtil.getLayoutId(this);
        if (layoutId != -1) {
            return layoutId;
        } else {
            throw new RuntimeException(getClass() + "当前页面没有给定布局资源");
        }
    }

    /**
     * 初始化当前页面的布局设置
     *
     * @param view 当前页面视图
     */
    protected abstract void initVars(View view);

    /**
     * 加载初始数据
     */
    protected abstract void loadInitDta();

    /**
     * 注册事件
     */
    public void registerBus() {
        mEventBus.register(this);
    }

    public View getRootView() {
        return mRootContentView;
    }

    public View getContentLayout() {
        return mContentLayout;
    }

    public void setTitleBarVisibility(boolean visible) {
        mActionTitleBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * @param title 标题
     */
    protected void setTitleBar(String title) {
        this.setTitleBar(title, null);
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
     * @param rightTxt      标题
     * @param rightIcon     标题图片
     * @param rightListener 监听器
     */
    public void setTitleBarRight(String rightTxt, Integer rightIcon, View.OnClickListener rightListener) {
        mActionTitleBar.setTitleBarRight(rightTxt, rightIcon, rightListener);
    }

    private void setActionTitleBar(String leftTxt, Integer leftDrawableId, View.OnClickListener leftListener,
                                   String title, Integer titleDrawableId,
                                   String rightTxt, Integer rightDrawableId, View.OnClickListener rightListener) {
        this.setTitleBarLeft(leftTxt, leftDrawableId, leftListener);
        this.setTitleBar(title, titleDrawableId);
        this.setTitleBarRight(rightTxt, rightDrawableId, rightListener);
    }


    /**
     * 是否展示空页面
     *
     * @param showEmpty true：展示，false：隐藏
     */
    public void setEmptyUI(boolean showEmpty) {
        setEmptyLayout(showEmpty, -1, -1);
    }

    /**
     * 设置空页面
     *
     * @param showEmpty  true：展示，false：隐藏
     * @param emptyTxtId 文案
     * @param emptyIcon  图标
     */
    public void setEmptyLayout(boolean showEmpty, Integer emptyTxtId, Integer emptyIcon) {
        setEmptyLayout(showEmpty, getString(emptyTxtId), emptyIcon);
    }

    /**
     * 设置空页面
     *
     * @param showEmpty true：展示，false：隐藏
     * @param emptyTxt  文案
     * @param emptyIcon 图标
     */
    public void setEmptyLayout(boolean showEmpty, String emptyTxt, Integer emptyIcon) {
        setEmptyLayout(showEmpty, emptyTxt, -1, emptyIcon);
    }

    /**
     * 设置空页面
     *
     * @param showEmpty     true：展示，false：隐藏
     * @param emptyTxt      文案
     * @param emptyTxtColor 文案色值
     * @param emptyIcon     图标
     */
    public void setEmptyLayout(boolean showEmpty, String emptyTxt, @ColorInt Integer emptyTxtColor, Integer emptyIcon) {
        if (showEmpty) {
            mProgressLayout.setVisibility(View.GONE);
            mContentLayout.setVisibility(View.GONE);
            mEmptyLayout.setVisibility(View.VISIBLE);
            //
            if (!TextUtils.isEmpty(emptyTxt)) {
                ((TextView) mEmptyLayout.findViewById(R.id.emptyTxt)).setText(emptyTxt);
            } else {/*default*/}
            //
            if (emptyTxtColor != null) {
                ((TextView) mEmptyLayout.findViewById(R.id.emptyTxt)).setTextColor(emptyTxtColor);
            } else {/*default*/}
            //
            if (emptyIcon != null) {
                ((ImageView) mEmptyLayout.findViewById(R.id.emptyImg)).setImageResource(emptyIcon);
            } else {/*default*/}

        } else {
            mProgressLayout.setVisibility(View.GONE);
            mEmptyLayout.setVisibility(View.GONE);
            mContentLayout.setVisibility(View.VISIBLE);
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

    /**
     * 事件订阅的默认实现
     *
     * @param e 事件
     */
    @Subscribe
    public void onEvent(Object e) {

    }

    private void immerseTheme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mViewUnbind != null) {
            mViewUnbind.unbind();
            mViewUnbind = null;
        }
        if (mEventBus != null && mEventBus.isRegistered(this)) {
            mEventBus.unregister(this);
        }
        ActvStackUtil.remove(this);
    }

    @Override
    public void showNetLoading() {
        this.showNetLoading(null);
    }

    @Override
    public void showNetLoading(String message) {
        mProgressLayout.setVisibility(View.VISIBLE);
        mEmptyLayout.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.GONE);
    }

    @Override
    public void closeLoading() {
        mProgressLayout.setVisibility(View.GONE);
    }
}
