package com.passion.libbase.mvp;

/**
 * Created by chaos
 * On 17-7-2.
 * Email:iwenchaos@gmail.com
 * Description:
 */

public interface IBasePresenter {
    /**
     * 初始化操作
     */
    void onStart();

    /**
     * 结束后做一些清理操作
     */
    void onDestroy();

}
