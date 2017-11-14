package com.passion.libbase.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by chaos
 * on 2017/11/14. 10:54
 * 文件描述：
 */

public class KeyboardStateListener {


    private static final int SOFT_KEY_BOARD_MIN_HEIGHT = 500;
    private KeyboardVisibilityListener mVisibilityListener;

    private boolean keyboardVisible = false;
    /**
     * 在Fragment中
     * @param f Fragment对象
     * @return KeyboardStateListener
     */
    public KeyboardStateListener registerFragment(Fragment f) {
        registerView(f.getView());
        return this;
    }

    /**
     * 在activity中
     * @param a activity对象
     * @return KeyboardStateListener
     */
    public KeyboardStateListener registerActivity(Activity a) {
        registerView(a.getWindow().getDecorView().findViewById(android.R.id.content));
        return this;
    }
    /**
     * @param v root view 对象
     */
    private void registerView(final View v) {
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                v.getWindowVisibleDisplayFrame(r);

                int heightDiff = v.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > SOFT_KEY_BOARD_MIN_HEIGHT) { // if more than 500 pixels, its probably a keyboard...
                    if (!keyboardVisible) {
                        keyboardVisible = true;
                        if (mVisibilityListener != null) {
                            mVisibilityListener.onVisibilityChanged(true,heightDiff);
                        }
                    }
                } else {
                    if (keyboardVisible) {
                        keyboardVisible = false;
                        if (mVisibilityListener != null) {
                            mVisibilityListener.onVisibilityChanged(false,heightDiff);
                        }
                    }
                }
            }
        });

    }

    /**
     *
     * @param listener  键盘可见性
     * @return KeyboardStateListener
     */
    public KeyboardStateListener setVisibilityListener(KeyboardVisibilityListener listener) {
        mVisibilityListener = listener;
        return this;
    }

    public interface KeyboardVisibilityListener {
        void onVisibilityChanged(boolean keyboardVisible,int keyboardHeight);
    }
}
