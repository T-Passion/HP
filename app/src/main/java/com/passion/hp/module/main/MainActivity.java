package com.passion.hp.module.main;


import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.passion.hp.R;
import com.passion.libbase.AbstractBaseActivity;
import com.passion.libbase.constants.RouterPath;
import com.passion.libbase.imp.LayoutId;
import com.passion.libutils.Toaster;
import com.passion.widget.main.NavigationTabBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@LayoutId(R.layout.activity_main_layout)
@Route(path = RouterPath.MAIN_ACTIVITY)
public class MainActivity extends AbstractBaseActivity implements NavigationTabBar.OnTabBarSelectedIndexListener {


    @BindView(R.id.home_content_container)
    FrameLayout mContentContainer;
    @BindView(R.id.home_tab_container)
    NavigationTabBar mTabContainer;

    @Override
    protected void initVars(View view) {
        initTabBars();
        initTitleBar();


    }

    @Override
    protected void loadInitDta() {

    }

    private void initTitleBar(){
        setTitleBar(null, R.drawable.icon_title_bar);
        setTitleBarRight(null, R.drawable.search_btn_board_day, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toaster.showToast(MainActivity.this,"搜索");
            }
        });
    }

    private void initTabBars() {
        final List<NavigationTabBar.Model> models = new ArrayList<>();
        final int whiteColor = getResources().getColor(R.color.white);
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.btn_news_up), whiteColor)
                .selectedIcon(getResources().getDrawable(R.drawable.btn_news_down))
                .build()
        );
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.btn_nba_game_up), whiteColor)
                .selectedIcon(getResources().getDrawable(R.drawable.btn_nba_game_down))
                .build()

        );
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.btn_bbs_up), whiteColor)
                .selectedIcon(getResources().getDrawable(R.drawable.btn_bbs_down))
                .build()

        );
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.btn_discovery_up), whiteColor)
                .selectedIcon(getResources().getDrawable(R.drawable.btn_discovery_down))
                .build()

        );
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.btn_more_up), whiteColor)
                .selectedIcon(getResources().getDrawable(R.drawable.btn_more_down))
                .build()

        );
        mTabContainer.setModels(models);
        mTabContainer.setModelIndex(0, true);
        mTabContainer.setOnTabBarSelectedIndexListener(this);
    }

    @Override
    public void onStartTabSelected(NavigationTabBar.Model model, int index) {

    }

    @Override
    public void onEndTabSelected(NavigationTabBar.Model model, int index) {

    }
}
