package com.passion.hp.module.home.apdater;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.passion.hp.R;
import com.passion.hp.module.home.model.entity.TabVo;
import com.passion.libbase.utils.DensityUtil;
import com.shizhefei.view.indicator.IndicatorViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaos
 * on 2018/1/17. 14:05
 * 文件描述：
 */

public class HomeTabPagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

    private final Context mContext;
    private List<TabVo> mTabList;
    private List<Fragment> mPagerList;

    public HomeTabPagerAdapter(FragmentManager fragmentManager, Context context) {
        this(fragmentManager, context, null, null);
    }

    public HomeTabPagerAdapter(FragmentManager fragmentManager,
                               Context context,
                               List<TabVo> tabList,
                               List<Fragment> pagerList) {
        super(fragmentManager);
        mContext = context;
        mTabList = tabList;
        mPagerList = pagerList;
    }

    public HomeTabPagerAdapter setTabList(List<TabVo> tabs) {
        if (mTabList == null) {
            mTabList = new ArrayList<>();
        }
        if (tabs != null) {
            mTabList.addAll(tabs);
        }
        return this;
    }

    public HomeTabPagerAdapter setPagerList(List<Fragment> pagers) {
        if (mPagerList == null) {
            mPagerList = new ArrayList<>();
        }
        if (pagers != null) {
            mPagerList.addAll(pagers);
        }
        return this;
    }



    @Override
    public int getCount() {
        return mTabList == null ? 0 : mTabList.size();
    }

    /**
     * tab view
     */
    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        TabVo tabVo = mTabList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adater_home_tab_layout, container, false);
        }
        TextView tabView = (TextView) convertView;
        tabView.setText(tabVo.getName());
        //因为wrap_content属性，导致产生ui抖动
        int width = DensityUtil.getTextWidth(tabView);
        int padding = (int) DensityUtil.dp2Px(8);
        //因为wrap的布局 字体大小变化会导致textView大小变化产生抖动，这里通过设置textView宽度就避免抖动现象
        //1.3f是根据上面字体大小变化的倍数1.3f设置
        tabView.setWidth((int) (width * 1.3f) + padding);

        return convertView;
    }

    /**
     * pager view
     */
    @Override
    public Fragment getFragmentForPage(int position) {
        if (mPagerList != null && mPagerList.size() > position) {
            Fragment pager = mPagerList.get(position);
            return pager;
        }
        return null;
    }
}
