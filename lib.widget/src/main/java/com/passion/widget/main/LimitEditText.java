package com.chaos.widget.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaos.widget.R;


/**
 * Created by chaos
 * on 2017/10/19. 20:30
 * 文件描述：
 * --------------------------
 * |天青色等烟雨              |
 * |                        |
 * |                        |
 * |                        |
 * |                        |
 * |                  0/100 |
 * --------------------------
 */

public class LimitEditText extends LinearLayout {
    private Context context;
    private EditText id_et_input;
    private TextView id_tv_input;

    //统计方向（1：正向，从0开始，2：反向）
    private int sort;

    private int maxCount;

    private String hintText;
    private int hintTextColor;

    private boolean ignoreCnOrEn;

    private String contentText;
    private int contentTextSize;
    private int contentTextColor;
    private float contentViewHeight;

    private OnLimitEditListener mLimitEditListener;

    public void setLimitEditListener(OnLimitEditListener limitEditListener) {
        mLimitEditListener = limitEditListener;
    }

    public LimitEditText(Context context) {
        this(context, null);
    }

    public LimitEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LimitEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LimitEditView);
        sort = typedArray.getInteger(R.styleable.LimitEditView_sort, 1);
        maxCount = typedArray.getInteger(R.styleable.LimitEditView_maxCount, 100);
        ignoreCnOrEn = typedArray.getBoolean(R.styleable.LimitEditView_ignoreCnOrEn, true);
        hintText = typedArray.getString(R.styleable.LimitEditView_hintText);
        hintTextColor = typedArray.getColor(R.styleable.LimitEditView_hintTextColor, Color.parseColor("#42000000"));
        contentText = typedArray.getString(R.styleable.LimitEditView_contentText);
        contentTextColor = typedArray.getColor(R.styleable.LimitEditView_contentTextColor, Color.parseColor("#8A000000"));
        contentTextSize = typedArray.getDimensionPixelSize(R.styleable.LimitEditView_contentTextSize, dp2px(context, 14));
        contentViewHeight = typedArray.getDimensionPixelSize(R.styleable.LimitEditView_contentViewHeight, dp2px(context, 140));
        typedArray.recycle();
        //
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.limit_edit_text_layout, this);
        id_et_input = (EditText) view.findViewById(R.id.id_et_input);
        id_tv_input = (TextView) view.findViewById(R.id.id_tv_input);

//        if (this.getBackground() == null) {
//            this.setBackgroundResource(R.drawable.selector_limit_edit_view_bg);
//        }

        id_et_input.addTextChangedListener(mTextWatcher);
        id_et_input.setHint(hintText);
        id_et_input.setHintTextColor(hintTextColor);
        id_et_input.setText(contentText);
        id_et_input.setTextColor(contentTextColor);
        id_et_input.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize);
        id_et_input.setHeight((int) contentViewHeight);
        /**
         * 配合 id_tv_input xml的 android:focusable="true"
         android:focusableInTouchMode="true"

         在id_et_input设置完文本后

         不给id_et_input 焦点
         */
        id_tv_input.requestFocus();
        //init
        configCount();
        id_et_input.setSelection(id_et_input.length()); // 将光标移动最后一个字符后面
        /**
         * focus后给背景设置Selected
         */
        id_et_input.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                setSelected(b);
            }
        });


    }


    private TextWatcher mTextWatcher = new TextWatcher() {

        private int editStart;

        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            editStart = id_et_input.getSelectionStart();
            editEnd = id_et_input.getSelectionEnd();

            // 先去掉监听器，否则会出现栈溢出
            id_et_input.removeTextChangedListener(mTextWatcher);

            if (ignoreCnOrEn) {
                //当输入字符个数超过限制的大小时，进行截断操作
                while (calculateLengthIgnoreCnOrEn(editable.toString()) > maxCount) {
                    editable.delete(editStart - 1, editEnd);
                    editStart--;
                    editEnd--;
                }
            } else {
                // 因为是中英文混合，单个字符而言，calculateLength函数都会返回1
                while (calculateLength(editable.toString()) > maxCount) { // 当输入字符个数超过限制的大小时，进行截断操作
                    editable.delete(editStart - 1, editEnd);
                    editStart--;
                    editEnd--;
                }
            }

            id_et_input.setSelection(editStart);

            // 恢复监听器
            id_et_input.addTextChangedListener(mTextWatcher);

            //update
            configCount();
        }
    };


    private long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }

    private int calculateLengthIgnoreCnOrEn(CharSequence c) {
        int len = 0;
        for (int i = 0; i < c.length(); i++) {
            len++;
        }
        return len;
    }

    private void configCount() {
        if (ignoreCnOrEn) {
            int nowCount = calculateLengthIgnoreCnOrEn(id_et_input.getText().toString());
            //
            if (sort != 1) {
                id_tv_input.setText(String.valueOf((maxCount - nowCount)) + "/" + maxCount);
            } else {
                id_tv_input.setText(String.valueOf((nowCount)) + "/" + maxCount);

            }
        } else {
            long nowCount = calculateLength(id_et_input.getText().toString());
            //
            if (sort != 1) {
                id_tv_input.setText(String.valueOf((maxCount - nowCount)) + "/" + maxCount);
            } else {
                id_tv_input.setText(String.valueOf((nowCount)) + "/" + maxCount);

            }
        }

    }

    private static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void setContentText(String content) {
        contentText = content;
        if (id_et_input == null) {
            return;
        }
        id_et_input.setText(contentText);
    }

    public String getContentText() {
        if (id_et_input != null) {
            contentText = id_et_input.getText() == null ? "" : id_et_input.getText().toString();
        }
        return contentText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
        if (id_et_input == null) {
            return;
        }
        id_et_input.setHint(hintText);
    }

    public void setContentTextSize(int size) {
        if (id_et_input == null) {
            return;
        }
        id_et_input.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void setContentTextColor(int color) {
        if (id_et_input == null) {
            return;
        }
        id_et_input.setTextColor(color);
    }

    public void setHintColor(int color) {
        if (id_et_input == null) {
            return;
        }
        id_et_input.setHintTextColor(color);
    }

    public String getHintText() {
        if (id_et_input != null) {
            hintText = id_et_input.getHint() == null ? "" : id_et_input.getHint().toString();
        }
        return hintText;
    }

    public void setMaxCount(int max_count) {
        this.maxCount = max_count;
        configCount();
    }

    public void setIgnoreCnOrEn(boolean ignoreCnOrEn) {
        this.ignoreCnOrEn = ignoreCnOrEn;
        configCount();
    }


    public interface OnLimitEditListener {
        void onListen();
    }
}

