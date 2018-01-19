package com.passion.hp.module.main.ui;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.passion.hp.R;
import com.passion.hp.module.home.ui.HomeFragment;
import com.passion.hp.module.main.contract.MainContract;
import com.passion.hp.module.main.model.MainModel;
import com.passion.hp.module.main.presenter.MainPresenter;
import com.passion.libbase.AbstractBaseActivity;
import com.passion.libbase.constants.RouterPath;
import com.passion.libbase.imp.LayoutId;
import com.passion.libbase.utils.LogUtil;
import com.passion.libutils.ActvStackUtil;
import com.passion.libutils.Toaster;
import com.passion.widget.main.NavigationTabBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@LayoutId(R.layout.activity_main_layout)
@Route(path = RouterPath.MAIN_ACTIVITY)
public class MainActivity extends AbstractBaseActivity implements NavigationTabBar.OnTabBarSelectedIndexListener, MainContract.View {

    @BindView(R.id.main_tab_container)
    NavigationTabBar mTabContainer;

    private MainContract.Model mModel;
    private MainContract.Presenter mPresenter;

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private int mCurTabIndex = 0;
    private long firstTime = 0;


    @Override
    protected void initVars(View view) {
        initTabBars();
        initTitleBar();
        initAllFragments();

        mModel = new MainModel();
        mPresenter = new MainPresenter(this, mModel);

        switchFragmentWithTab(mCurTabIndex);
    }

    @Override
    protected void loadInitDta() {
        //请求接口获得
        mPresenter.getStatusInit();

    }

    private void initTitleBar() {
        setTitleBar(null, R.drawable.icon_title_bar);
        setTitleBarRight(null, R.drawable.search_btn_board_day, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toaster.showToast(MainActivity.this, "搜索");
            }
        });
    }

    private void initTabBars() {
        final List<NavigationTabBar.Model> models = new ArrayList<>();
        final int whiteColor = getResources().getColor(R.color.white);
        //首页
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.btn_news_up), whiteColor)
                .selectedIcon(getResources().getDrawable(R.drawable.btn_news_down))
                .build()
        );
        //比赛
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.btn_nba_game_up), whiteColor)
                .selectedIcon(getResources().getDrawable(R.drawable.btn_nba_game_down))
                .build()

        );
        //论坛
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.btn_bbs_up), whiteColor)
                .selectedIcon(getResources().getDrawable(R.drawable.btn_bbs_down))
                .build()

        );
        //发现
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.btn_discovery_up), whiteColor)
                .selectedIcon(getResources().getDrawable(R.drawable.btn_discovery_down))
                .build()

        );
        //更多
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.btn_more_up), whiteColor)
                .selectedIcon(getResources().getDrawable(R.drawable.btn_more_down))
                .build()

        );
        mTabContainer.setModels(models);
        mTabContainer.setModelIndex(0, true);
        mTabContainer.setOnTabBarSelectedIndexListener(this);
    }

    private void initAllFragments(){
        mFragmentList.add(HomeFragment.newInstance());
        mFragmentList.add(HomeFragment.newInstance());
        mFragmentList.add(HomeFragment.newInstance());
        mFragmentList.add(HomeFragment.newInstance());
        mFragmentList.add(HomeFragment.newInstance());
    }

    @Override
    public void onStartTabSelected(NavigationTabBar.Model model, int index) {
        LogUtil.i("tab onStartTab :" + index);
    }

    @Override
    public void onEndTabSelected(NavigationTabBar.Model model, int index) {
        LogUtil.i("tab onEndTab :" + index);
        switchFragmentWithTab(index);
    }

    private void switchFragmentWithTab(int tabIndex) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.main_content_container, mFragmentList.get(tabIndex))
                .commitAllowingStateLoss();
    }


    /**
     * 双击退出
     */
    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else{
            ActvStackUtil.AppExit();
        }
    }


}
