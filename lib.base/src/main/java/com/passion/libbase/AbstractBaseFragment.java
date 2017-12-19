package com.passion.libbase;

import android.support.v4.app.Fragment;

import com.passion.libbase.mvp.IBaseView;

/**
 * Created by chaos
 * on 2017/12/19. 11:13
 * 文件描述：
 */

public class AbstractBaseFragment extends Fragment implements IBaseView{



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
