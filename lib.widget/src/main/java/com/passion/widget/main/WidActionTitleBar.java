package com.passion.widget.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.passion.libwidget.R;
import com.passion.libwidget.R2;
import com.passion.widget.imp.ViewInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huangdou
 * on 2017/9/29.
 */

public class WidActionTitleBar extends View implements ViewInterface {

    @BindView(R2.id.titleBarLeftText)
    TextView mTitleBarLeftText;
    @BindView(R2.id.titleBarLeftIcon)
    ImageView mTitleBarLeftIcon;
    @BindView(R2.id.titleBarNameIcon)
    ImageView mTitleBarNameIcon;
    @BindView(R2.id.titleBarNameTxt)
    TextView mTitleBarNameTxt;
    @BindView(R2.id.titleBarRightTxt)
    TextView mTitleBarRightTxt;
    @BindView(R2.id.titleBarRightIcon)
    ImageView mTitleBarRightIcon;
    @BindView(R2.id.titleBarLeftLay)
    LinearLayout mTitleBarLeftLay;
    @BindView(R2.id.titleBarNameFL)
    FrameLayout mTitleBarNameFL;
    @BindView(R2.id.titleBarRight)
    LinearLayout mTitleBarRightLay;
    @BindView(R2.id.appTitleBar)
    RelativeLayout mAppTitleBar;
    private Context mContext;
    private View mContainer;

    public WidActionTitleBar(Context context) {
        this(context, null);
    }

    public WidActionTitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidActionTitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = getContext();
        initView();
        initAttr(attrs);
    }


    @Override
    public void initView() {
        mContainer = LayoutInflater.from(mContext).inflate(R.layout.action_title_bar_layout, null);
        ButterKnife.bind(this, mContainer);
    }

    @Override
    public void initAttr(AttributeSet attrs) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.WidActionTitleBar);
        String leftText = ta.getString(R.styleable.WidActionTitleBar_leftText);
        int leftIconId = ta.getResourceId(R.styleable.WidActionTitleBar_leftIcon, R.drawable.title_bar_left_icon_selector);
        String titleText = ta.getString(R.styleable.WidActionTitleBar_titleTxt);
        int titleIconId = ta.getResourceId(R.styleable.WidActionTitleBar_titleIcon, -1);
        String rightText = ta.getString(R.styleable.WidActionTitleBar_rightText);
        int rightIconId = ta.getResourceId(R.styleable.WidActionTitleBar_rightIcon, R.drawable.search_btn_board_day);
        //左侧图文
        if (!TextUtils.isEmpty(leftText)) {
            mTitleBarLeftText.setText(leftText);
        } else {
            mTitleBarLeftText.setVisibility(GONE);
        }
        mTitleBarLeftIcon.setImageResource(leftIconId);
        //title部分
        if (!TextUtils.isEmpty(titleText)) {
            mTitleBarNameTxt.setText(titleText);
        } else {
            mTitleBarNameTxt.setVisibility(GONE);
        }
        if (titleIconId != -1) {
            mTitleBarNameIcon.setImageResource(titleIconId);
        } else {
            mTitleBarNameIcon.setVisibility(GONE);
        }
        //右侧图文
        if (!TextUtils.isEmpty(rightText)) {
            mTitleBarRightTxt.setText(leftText);
        } else {
            mTitleBarRightTxt.setVisibility(GONE);
        }
        if (rightIconId != -1) {
            mTitleBarRightIcon.setImageResource(rightIconId);
        } else {
            mTitleBarRightIcon.setVisibility(GONE);
        }
        ta.recycle();
    }

    public void setTitleBar(String title) {
        mTitleBarNameTxt.setVisibility(TextUtils.isEmpty(title) ? GONE : VISIBLE);
        if (!TextUtils.isEmpty(title)) {
            mTitleBarNameTxt.setText(title);
        }
    }

    /**
     * @param title
     * @param titleIcon
     */
    public void setTitleBar(String title, Integer titleIcon) {
        mTitleBarNameTxt.setVisibility(TextUtils.isEmpty(title) ? GONE : VISIBLE);
        if (!TextUtils.isEmpty(title)) {
            mTitleBarNameTxt.setText(title);
        }
        mTitleBarNameIcon.setVisibility(titleIcon == null ? GONE : VISIBLE);
        if (titleIcon != null) {
            mTitleBarNameIcon.setImageResource(titleIcon);
        }
    }

    public void setTitleBar(int titleId, int titleIcon) {
        setTitleBar(getResources().getString(titleId), titleIcon);
    }

    /**
     *
     * @param title
     * @param titleIcon
     * @param leftListener
     */
    public void setTitleBarLeft(String title, Integer titleIcon, OnClickListener leftListener) {
        mTitleBarLeftText.setVisibility(TextUtils.isEmpty(title) ? GONE : VISIBLE);
        if (!TextUtils.isEmpty(title)) {
            mTitleBarLeftText.setText(title);
        }
        mTitleBarLeftIcon.setVisibility(titleIcon == null ? GONE : VISIBLE);
        if (titleIcon != null) {
            mTitleBarLeftIcon.setImageResource(titleIcon);
        }
        if (leftListener != null) {
            mTitleBarLeftLay.setOnClickListener(leftListener);
        }
    }

    public void setTitleBarLeft(int titleId, Integer titleIcon, OnClickListener leftListener) {
        setTitleBarLeft(getResources().getString(titleId), titleIcon, leftListener);
    }

    /**
     * @param title
     * @param titleIcon
     * @param rightListener
     */
    public void setTitleBarRight(String title, Integer titleIcon, OnClickListener rightListener) {
        mTitleBarRightTxt.setVisibility(TextUtils.isEmpty(title) ? GONE : VISIBLE);
        if (!TextUtils.isEmpty(title)) {
            mTitleBarRightTxt.setText(title);
        }
        mTitleBarRightIcon.setVisibility(titleIcon == null ? GONE : VISIBLE);
        if (titleIcon != null) {
            mTitleBarRightIcon.setImageResource(titleIcon);
        }
        if (rightListener != null) {
            mTitleBarRightLay.setOnClickListener(rightListener);
        }
    }

    public void setTitleBarRight(int titleId, Integer titleIcon, OnClickListener rightListener) {
        setTitleBarRight(getResources().getString(titleId), titleIcon, rightListener);
    }

    public void setTitleLeftListener(OnClickListener leftListener) {
        mTitleBarLeftLay.setOnClickListener(leftListener);
    }

    public void setTitleRightListener(OnClickListener rightListener) {
        mTitleBarRightLay.setOnClickListener(rightListener);
    }

}
