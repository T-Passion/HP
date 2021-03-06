package com.passion.hp.module.home.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.passion.hp.R;
import com.passion.hp.module.home.apdater.SubTabNewsAdapter;
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

import java.util.List;

import butterknife.BindView;

/**
 * Created by chaos
 * on 2018/1/18. 11:09
 * 文件描述：
 */

@LayoutId(R.layout.fragment_tabs_recycler_layout)
public class SubTabListFragment extends AbstractBaseFragment implements SubTabFragmentContract.View, Indicator.OnItemSelectedListener {


    @BindView(R.id.tabsView)
    ScrollIndicatorView mTabsView;
    @BindView(R.id.contentRecycleView)
    RecyclerView mRecyclerView;

    SubTabsAdapter mTabAdapter;
    SubTabNewsAdapter mNewsAdapter;
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

        mNewsAdapter = new SubTabNewsAdapter(getContext());
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(mNewsAdapter);

        mModel = new SubTabFragmentModel(getContext());
        mPresenter = new SubTabFragmentPresenter(this, mModel);
    }

    @Override
    public void loadInitDta() {
        mPresenter.getDta();
    }


    @Override
    public void render(NewsAllVo newsAllVo) {
        renderTabs(newsAllVo.getTabList());
        renderNews(newsAllVo);
    }


    private void renderTabs(List<TabVo> cateList) {
        mTabAdapter.setSubTabs(cateList).notifyDataSetChanged();
    }

    private void renderNews(NewsAllVo newsAllVo) {
        mNewsAdapter.setNewsList(newsAllVo.getData())
                .setGame(newsAllVo.getGame())
                .notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(View view, int i, int i1) {

    }
}
