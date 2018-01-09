package com.passion.widget.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.passion.libwidget.R;
import com.passion.widget.utils.DensityUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by huangdou
 * on 2017/10/12.
 * <p>
 * 控件实现了引导页的倒计时
 */

public class WidJumpView extends android.support.v7.widget.AppCompatTextView {

    private static final int ROUND_ANGLE = 360;
    private static final int DEFAULT_INTERVAL = 50;
    private static final int BOUND_OFFSET = 4;

    private int mOutLineWidth = 8;//px

    private int mCircleColor;
    private int mCircleRadius;

    private int mProgressLineColor = Color.RED;
    private int mProgress = 0;


    private int mDuration = 2000;//ms
    private int mInterval = DEFAULT_INTERVAL;//ms
    private int mDrawTimes = 0;//总的绘制次数
    private int mDrawedTimes;//已经绘制的次数
    private int mEachDrawAngle = 0;//默认每次绘制的度数

    private Paint mPaint;
    private Rect mBounds;
    private RectF mArcRectF;

    private Timer mTimeCounter;
    private OnJumpAction mJumpAction;
    private String mJumpText;
    private int mTextSize;
    private int mTextColor;


    public WidJumpView(Context context) {
        this(context, null);
    }

    public WidJumpView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public WidJumpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttr(attrs);
    }

    private void init() {
        mPaint = new Paint();
        mBounds = new Rect();
        mArcRectF = new RectF();
        mTimeCounter = new Timer();
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WidJumpView);
        mOutLineWidth = typedArray.getInt(R.styleable.WidJumpView_outLineWidth, 4);
        mCircleColor = typedArray.getColor(R.styleable.WidJumpView_circleColor, Color.TRANSPARENT);
        mCircleRadius = typedArray.getInt(R.styleable.WidJumpView_circleRadius, 30);
        mProgressLineColor = typedArray.getColor(R.styleable.WidJumpView_progresColor, Color.RED);
        mDuration = typedArray.getInt(R.styleable.WidJumpView_jumpDuration, 2000);
        mJumpText = typedArray.getString(R.styleable.WidJumpView_jumpText);
        mTextColor = typedArray.getColor(R.styleable.WidJumpView_jumpTextColor, Color.WHITE);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.LimitEditView_contentTextSize, DensityUtil.dip2Px(getContext(), 12));
        if (TextUtils.isEmpty(mJumpText)) {
            mJumpText = getContext().getResources().getString(R.string.jump);
        }
        mDrawTimes = mDuration / mInterval;
        mEachDrawAngle = ROUND_ANGLE / mDrawTimes;
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        if (w > h)
            h = w;
        else
            w = h;
        //计算出圆半径
        mCircleRadius = w / 2 - BOUND_OFFSET;
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //找到view的边界
        getDrawingRect(mBounds);
        // 确定圆心
        int mCenterX = mBounds.centerX();
        int mCenterY = mBounds.centerY();

        //画内圆
        mPaint.reset();
        mPaint.setAntiAlias(true);//防锯齿
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mCircleColor);
        canvas.drawCircle(mCenterX, mCenterY, mCircleRadius - mOutLineWidth, mPaint);

        //画外边框
        mPaint.reset();
        mPaint.setAntiAlias(true);//防锯齿
        mPaint.setStyle(Paint.Style.STROKE);//设置空心圆
        mPaint.setStrokeWidth(mOutLineWidth);//圆环宽度
        mPaint.setColor(mCircleColor);//圆环颜色
        canvas.drawCircle(mCenterX, mCenterY, mCircleRadius - mOutLineWidth / 2, mPaint);

        //画字
        mPaint.reset();
        mPaint.setStrokeWidth(0);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        mPaint.setAntiAlias(true);
        float textWidth = mPaint.measureText(mJumpText);
        canvas.drawText(mJumpText, mCenterX - textWidth / 2, mCenterY + mTextSize / 2, mPaint);

        // 画进度条
        mPaint.reset();
        mPaint.setAntiAlias(true);//防锯齿
        mPaint.setStrokeWidth(mOutLineWidth);
        mPaint.setColor(mProgressLineColor);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mArcRectF.set(mCenterX - mCircleRadius, mCenterY - mCircleRadius, mCenterX + mCircleRadius, mCenterY + mCircleRadius);
        canvas.drawArc(mArcRectF, 270, (mDrawedTimes + 1) * mEachDrawAngle, false, mPaint);

    }


    public void setDuration(int time) {
        setDuration(time, DEFAULT_INTERVAL);
    }

    public WidJumpView setJumpAction(OnJumpAction onJumpAction) {
        mJumpAction = onJumpAction;
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
                if (mJumpAction != null) {
                    mJumpAction.onEnd();
                }
            }
        });
        return this;
    }

    /**
     * 倒计时时间应该被interval整除，每隔interval毫秒更新一次UI
     *
     * @param time     一个周期
     * @param interval 每次间隔，默认为200ms
     */
    public void setDuration(int time, int interval) {
        mDuration = time;
        mInterval = interval;
        mDrawTimes = time / interval;
        mEachDrawAngle = ROUND_ANGLE / mDrawTimes;
    }

    public void start() {
        final int changePer = 100 / mDrawTimes;
        mTimeCounter.schedule(new TimerTask() {
            @Override
            public void run() {
                postInvalidate();
                mDrawedTimes++;
                mProgress += changePer;
                mDuration -= mInterval;
                if (mProgress == 100) {
                    stop();
                    post(new Runnable() {
                        @Override
                        public void run() {
                            if (mJumpAction != null)
                                mJumpAction.onEnd();
                        }
                    });
                }
            }
        }, 500, mInterval);
    }

    public void stop() {
        if (mTimeCounter != null)
            mTimeCounter.cancel();
    }

    /**
     * 监听
     */
    public interface OnJumpAction {
        void onEnd();
    }
}
