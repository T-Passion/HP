package com.passion.libbase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.passion.libbase.mvp.IBaseView;
import com.passion.libbase.utils.AnnotationUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chaos
 * on 2017/12/19. 11:13
 * 文件描述：Fragment的基类
 */

public abstract class AbstractBaseFragment extends Fragment implements IBaseView {


    private Unbinder mUnBinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVars();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(getLayoutId(), container, false);
        mUnBinder = ButterKnife.bind(this, content);
        loadInitDta(content);
        return content;
    }


    private int getLayoutId() {
        return AnnotationUtil.getLayoutId(this);
    }

    public abstract void initVars();

    public abstract void loadInitDta(View view);


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != null) {
            mUnBinder.unbind();
            mUnBinder = null;
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
}
