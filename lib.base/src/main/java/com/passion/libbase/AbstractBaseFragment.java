package com.passion.libbase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.passion.libbase.mvp.IBaseView;
import com.passion.libbase.utils.AnnotationUtil;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chaos
 * on 2017/12/19. 11:13
 * 文件描述：Fragment的基类
 */

public abstract class AbstractBaseFragment extends Fragment implements IBaseView {

    @Inject
    public EventBus mEventBus;

    private Unbinder mUnBinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(getLayoutId(), container, false);
        mUnBinder = ButterKnife.bind(this, content);
        initVars(content);
        loadInitDta();
        return content;
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
     * <p>onCreateView()之后</p>
     */
    public abstract void initVars(View view);

    /**
     * <p>onCreateView(),完成butterKnife bind之后</p>
     */
    public abstract void loadInitDta();

    /**
     * 注册事件
     */
    public void registerBus() {
        mEventBus.register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != null) {
            mUnBinder.unbind();
            mUnBinder = null;
        }
        if (mEventBus != null && mEventBus.isRegistered(this)) {
            mEventBus.unregister(this);
        }
    }

    @Override
    public void showNetLoading() {

    }

    @Override
    public void showNetLoading(String message) {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void emptyUi(Object... args) {

    }

    @Override
    public void errorUi(Object... args) {

    }
}
