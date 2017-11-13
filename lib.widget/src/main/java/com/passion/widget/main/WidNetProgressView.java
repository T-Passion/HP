package com.passion.widget.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.passion.libwidget.R;
import com.passion.widget.imp.ViewInterface;
import com.passion.widget.main.render.LoadingDrawable;
import com.passion.widget.main.render.LoadingRenderer;
import com.passion.widget.main.render.LoadingRendererFactory;


/**
 * Created by huangdou
 * on 2017/10/10.
 */

public class WidNetProgressView extends android.support.v7.widget.AppCompatImageView implements ViewInterface {
    private Context mContext;
    private LoadingDrawable mLoadingDrawable;

    public WidNetProgressView(Context context) {
        super(context);
    }

    public WidNetProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WidNetProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }


    @Override
    public void initAttr(AttributeSet attrs) {
        try {
            TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.WidNetProgressView);
            int loadingStyleId = ta.getInt(R.styleable.WidNetProgressView_loading_renderer, 0);
            LoadingRenderer loadingRenderer = LoadingRendererFactory.createLoadingRenderer(mContext, loadingStyleId);
            setLoadingRender(loadingRenderer);
            ta.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {
    }


    public void setLoadingRender(LoadingRenderer loadingRender) {
        mLoadingDrawable = new LoadingDrawable(loadingRender);
        setImageDrawable(mLoadingDrawable);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        final boolean visible = (visibility == VISIBLE && getVisibility() == VISIBLE);
        if (visible) {
            startAnimation();
        } else {
            stopAnimation();
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    private void startAnimation() {
        if (mLoadingDrawable != null) {
            mLoadingDrawable.start();
        }
    }

    private void stopAnimation() {
        if (mLoadingDrawable != null) {
            mLoadingDrawable.stop();
        }
    }


}
