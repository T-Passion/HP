package com.passion.hp.module.home.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.passion.hp.R;
import com.passion.hp.module.home.apdater.HomeTabPagerAdapter;
import com.passion.hp.module.home.contract.HomeContract;
import com.passion.hp.module.home.model.HomeModel;
import com.passion.hp.module.home.presenter.HomePresenter;
import com.passion.libbase.AbstractBaseFragment;
import com.passion.libbase.constants.RouterPath;
import com.passion.libbase.imp.LayoutId;
import com.passion.libbase.utils.DensityUtil;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import butterknife.BindView;

/**
 * Created by chaos
 * on 2017/12/19. 11:11
 * 文件描述：首页
 */

@Route(path = RouterPath.HOME_FRAGMENT)
@LayoutId(R.layout.fragment_home_layout)
public class HomeFragment extends AbstractBaseFragment implements HomeContract.View {


    @BindView(R.id.homePagerTabs)
    ScrollIndicatorView mHomePagerTabs;
    @BindView(R.id.homePagers)
    ViewPager mHomePagers;


    private HomeContract.Model mModel;
    private HomeContract.Presenter mPresenter;


    private HomeTabPagerAdapter mIndiAdapter;

    public static HomeFragment newInstance(Bundle args) {
        HomeFragment home = new HomeFragment();
        home.setArguments(args);
        return home;
    }

    @Override
    public void initVars(View view) {
        mHomePagerTabs.setOnTransitionListener(new OnTransitionTextListener().setColor(Color.RED, Color.LTGRAY));
        mHomePagerTabs.setScrollBar(new ColorBar(getContext(), Color.RED, (int) DensityUtil.dp2Px( 2)));
        mHomePagers.setOffscreenPageLimit(2);

        mModel = new HomeModel();
        mPresenter = new HomePresenter(this, mModel);


        IndicatorViewPager indicatorPagerContainer = new IndicatorViewPager(mHomePagerTabs, mHomePagers);
        mIndiAdapter = new HomeTabPagerAdapter(getFragmentManager());
        indicatorPagerContainer.setAdapter(mIndiAdapter);
    }

    @Override
    public void loadInitDta() {
        mPresenter.getHomeTabs();
    }

    @Override
    public void renderTabs() {
        mIndiAdapter.setTabList().notifyDataSetChanged();
    }

    @Override
    public void renderContent() {
        mIndiAdapter.setNewsList().notifyDataSetChanged();
    }
}
