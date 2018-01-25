package com.passion.hp.module.home.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.passion.hp.R;
import com.passion.hp.module.home.apdater.SubTabsAdapter;
import com.passion.hp.module.home.contract.SubTabFragmentContract;
import com.passion.hp.module.home.model.SubTabFragmentModel;
import com.passion.hp.module.home.model.entity.NewsAllVo;
import com.passion.hp.module.home.model.entity.TabVo;
import com.passion.hp.module.home.presenter.SubTabFragmentPresenter;
import com.passion.libbase.AbstractBaseFragment;
import com.passion.libbase.imp.LayoutId;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.DrawableBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chaos
 * on 2018/1/18. 11:09
 * 文件描述：
 */

@LayoutId(R.layout.fragment_tabs_recycler_layout)
public class SubTabListFragment extends AbstractBaseFragment implements SubTabFragmentContract.View,Indicator.OnItemSelectedListener{


    @BindView(R.id.tabsView)
    ScrollIndicatorView mTabsView;
    @BindView(R.id.contentRecycleView)
    RecyclerView mContentRecycleView;

    SubTabsAdapter mTabAdapter;
    SubTabFragmentContract.Model mModel;
    SubTabFragmentContract.Presenter mPresenter;


    public static SubTabListFragment newInstance() {
        return newInstance(null);
    }

    public static SubTabListFragment newInstance(Bundle args) {
        SubTabListFragment subTab = new SubTabListFragment();
        subTab.setArguments(args);
        return subTab;
    }

    @Override
    public void initVars(View view) {
        mTabsView.setOnTransitionListener(new OnTransitionTextListener().setColor(Color.RED, Color.GRAY));
        mTabsView.setScrollBar(new DrawableBar(getContext(), R.drawable.shape_sub_tab_background, ScrollBar.Gravity.CENTENT_BACKGROUND));
        mTabsView.setOnItemSelectListener(this);


        mTabAdapter = new SubTabsAdapter(getContext());
        mTabsView.setAdapter(mTabAdapter);
        mTabsView.setCurrentItem(0);

        mModel = new SubTabFragmentModel(getContext());
        mPresenter = new SubTabFragmentPresenter(this,mModel);
    }

    @Override
    public void loadInitDta() {
        mPresenter.getDta();
    }


    @Override
    public void render(NewsAllVo newsAllVo) {
        renderTabs(newsAllVo.getTabList());
    }


    private void renderTabs(List<TabVo> cateList){
        mTabAdapter.setSubTabs(cateList).notifyDataSetChanged();
    }


    private List<TabVo> getSubTabs() {
        List<TabVo> tabs = new ArrayList<>();
        tabs.add(new TabVo("全部", "205601"));
        tabs.add(new TabVo("视频", "205602"));
        tabs.add(new TabVo("流言", "205603"));
        tabs.add(new TabVo("深度", "205604"));
        tabs.add(new TabVo("图集", "205605"));
        tabs.add(new TabVo("场外", "205606"));
        return tabs;
    }

    @Override
    public void onItemSelected(View view, int i, int i1) {

    }
}
