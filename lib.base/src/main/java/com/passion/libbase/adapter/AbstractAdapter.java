package com.passion.libbase.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by chaos
 * On 17-7-2.
 * Email:iwenchaos@gmail.com
 * Description:
 *
 * @note T 实体对象
 */

public abstract class AbstractAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> dataList;

    private LayoutInflater layoutInflater;

    public AbstractAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public AbstractAdapter(Context context, List<T> dataList) {
        this.context = context;
        this.dataList = dataList;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public View inflate(@LayoutRes int layoutId) {
        return layoutInflater.inflate(layoutId, null);
    }


    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }


    protected void addAll(List<T> list) {
        if (list != null) {
            dataList.addAll(list);
        }
    }
}
