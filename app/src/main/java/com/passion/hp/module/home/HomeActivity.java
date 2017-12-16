package com.passion.hp.module.home;


import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.passion.hp.R;
import com.passion.libbase.AbstractBaseActivity;
import com.passion.libbase.constants.RouterPath;
import com.passion.libbase.imp.LayoutId;
import com.passion.widget.main.NavigationTabBar;

import java.util.ArrayList;
import java.util.List;

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
        initTabBars();
//        setTitleBar(null,getDrawable());


    }

    @Override
    protected void loadInitDta() {

    }

    private void initTabBars(){
        final List<NavigationTabBar.Model> models = new ArrayList<>();
        final int whiteColor = getResources().getColor(R.color.white);
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.btn_news_up),whiteColor)
                .selectedIcon(getResources().getDrawable(R.drawable.btn_news_down))
                .build()
        );
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.btn_news_up),whiteColor)
                .selectedIcon(getResources().getDrawable(R.drawable.btn_news_down))
                .build()

        );
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.btn_news_up),whiteColor)
                .selectedIcon(getResources().getDrawable(R.drawable.btn_news_down))
                .build()

        );
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.btn_news_up),whiteColor)
                .selectedIcon(getResources().getDrawable(R.drawable.btn_news_down))
                .build()

        );
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.btn_news_up),whiteColor)
                .selectedIcon(getResources().getDrawable(R.drawable.btn_news_down))
                .build()

        );

        mTabContainer.setModels(models);
    }
}
