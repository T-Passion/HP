package com.passion.widget.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.passion.libwidget.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by huangdou
 * on 2017/10/12.
 *
 * 控件实现了引导页的倒计时
 */

public class WidJumpView extends android.support.v7.widget.AppCompatTextView {

    private int mOutLineColor = 0xFF888888;
    private int mOutLineWidth = 4;//px

    private int mCircleColor = 0x99888888;
    private int mCircleRadius;

    private int mTextColor = Color.WHITE;

    private int mProgressLineColor = Color.RED;
    private int mProgressLineWidth = 4;//px
    private int mProgress = 0;


    private int mDuration = 2000;//ms
    private int mInterval = 500;//ms
    private int mDrawTimes = 4;//总的绘制次数
    private int mDrawedTimes;//已经绘制的次数
    private int mEachDrawAngle = 90;//默认每次绘制的度数

    private Paint mPaint;
    private Rect mBounds;
    private RectF mArcRectF;

    private Timer mTimeCounter;
    private OnJumpAction mJumpAction;
    private String mJumpText;


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

    public void initAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WidJumpView);
        mOutLineColor = typedArray.getColor(R.styleable.WidJumpView_outLineColor, 0xFF888888);
        mOutLineWidth = typedArray.getInt(R.styleable.WidJumpView_outLineWidth, 4);
        mCircleColor = typedArray.getColor(R.styleable.WidJumpView_circleColor, 0x99888888);
        mCircleRadius = typedArray.getInt(R.styleable.WidJumpView_circleRadius, 30);
        mProgressLineColor = typedArray.getColor(R.styleable.WidJumpView_progressColor, Color.RED);
        mProgressLineWidth = typedArray.getInt(R.styleable.WidJumpView_progressLineWidth, 4);
        mDuration = typedArray.getInt(R.styleable.WidJumpView_duration, 2000);
        mJumpText = typedArray.getString(R.styleable.WidJumpView_text);
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
        mCircleRadius = w / 2;
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

        //画圆
        mPaint.reset();
        mPaint.setAntiAlias(true);//防锯齿
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mCircleColor);
        canvas.drawCircle(mCenterX, mCenterX, mCircleRadius, mPaint);

        //画外边框
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mOutLineWidth);
        mPaint.setColor(mOutLineColor);
        canvas.drawCircle(mCenterX, mCenterY, mCircleRadius, mPaint);

        //画字
        mPaint.reset();
        mPaint.setColor(mTextColor);
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        float textY = mCenterY - (mPaint.descent() + mPaint.ascent()) / 2;
        canvas.drawText(mJumpText, mCenterX, textY, mPaint);

        // 画进度条
        mPaint.setStrokeWidth(mProgressLineWidth);
        mPaint.setColor(mProgressLineColor);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcRectF.set(mBounds.left + mProgressLineWidth, mBounds.top + mProgressLineWidth,
                mBounds.right - mProgressLineWidth, mBounds.bottom - mProgressLineWidth);
        canvas.drawArc(mArcRectF, -90, (mDrawedTimes + 1) * mEachDrawAngle, false, mPaint);

    }

    public void setJumpText(String s) {
        mJumpText = s;
    }

    public void setDuration(int time) {
        setDuration(time, 500);
    }

    public void setJumpAction(OnJumpAction onJumpAction) {
        mJumpAction = onJumpAction;
    }
    /**
     * 倒计时时间应该被interval整除，每隔interval毫秒更新一次UI
     *
     * @param time  一个周期
     * @param interval  每次间隔，默认为500ms
     */
    public void setDuration(int time, int interval) {
        mDuration = time;
        mInterval = interval;
        mDrawTimes = time / interval;
        mEachDrawAngle = 360 / mDrawTimes;
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
                    post(new Runnable() {
                        @Override
                        public void run() {
                            mJumpAction.onAction();
                        }
                    });
                    mTimeCounter.cancel();
                }
            }
        }, 500, mInterval);
    }

    /**
     *
     */
    public interface OnJumpAction {
        void onAction();
    }
}
