package com.passion.hp.module.home.apdater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.passion.hp.R;
import com.passion.hp.module.home.model.entity.TabVo;
import com.passion.libnet.core.utils.SafeCheckUtil;
import com.shizhefei.view.indicator.Indicator;

import java.util.List;

/**
 * Created by chaos
 * on 2018/1/18. 15:18
 * 文件描述：
 */

public class SubTabsAdapter extends Indicator.IndicatorAdapter {

    private List<TabVo> mSubTabs;
    private Context mContext;

    public SubTabsAdapter(Context context) {
        mContext = context;
    }

    public SubTabsAdapter(Context context, List<TabVo> subTabs) {
        mSubTabs = subTabs;
        mContext = context;
    }

    public SubTabsAdapter setSubTabs(List<TabVo> subTabs){
        mSubTabs = subTabs;
        return this;
    }



    @Override
    public int getCount() {
        return SafeCheckUtil.isNull(mSubTabs) ? 0 : mSubTabs.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        TabVo tabVo = mSubTabs.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_sub_tab_layout, container, false);
        }
        TextView tabView = (TextView) convertView;
        tabView.setText(tabVo.getName());
        return convertView;
    }

}
