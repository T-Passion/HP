package com.passion.hp.module.home;


import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.passion.hp.R;
import com.passion.libbase.AbstractBaseActivity;
import com.passion.libbase.constants.RouterPath;
import com.passion.libbase.imp.LayoutId;
import com.passion.widget.main.NavigationTabBar;

import butterknife.BindView;

@LayoutId(R.layout.activity_home_layout)
@Route(path = RouterPath.HOME_ACTIVITY)
public class HomeActivity extends AbstractBaseActivity {


    @BindView(R.id.home_content_container)
    FrameLayout mContentContainer;
    @BindView(R.id.home_tab_container)
    NavigationTabBar mTabContainer;

    @Override
    protected void initVars(View view) {
//        setTitleBar(null,getDrawable());


    }

    @Override
    protected void loadInitDta() {

    }

    private void initTabBars(){

    }
}
