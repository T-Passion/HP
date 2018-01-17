package com.passion.libbase.mvp;

/**
 * Created by huangdou
 * on 2017/10/10.
 */

public interface IBaseView {

    void showNetLoading();
    void showNetLoading(String message);
    void closeLoading();

    void emptyUi(Object... args);
    void errorUi(Object... args);
}
