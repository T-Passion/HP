package com.passion.hp.module.home.ui.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by chaos
 * on 2018/1/27. 15:03
 * 文件描述：
 */

public class GridGameDecoration extends RecyclerView.ItemDecoration {
    private final int verticalItemSpacingInPx;
    private final int horizontalItemSpacingInPx;
    private final int row;

    public GridGameDecoration(int row ,int verticalItemSpacingInPx, int horizontalItemSpacingInPx) {
        this.row = row;
        this.verticalItemSpacingInPx = verticalItemSpacingInPx;
        this.horizontalItemSpacingInPx = horizontalItemSpacingInPx;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
       //根据recyclerView 的布局参数获得相应子视图的位置
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        int itemPosition = layoutParams.getViewLayoutPosition();

        int left = this.horizontalItemSpacingInPx;
        int right = this.horizontalItemSpacingInPx;

        int top = this.getItemTopSpacing(itemPosition);
        int bottom = this.getItemBottomSpacing(itemPosition,parent.getAdapter().getItemCount());
        outRect.set(left,top,right,bottom);
    }


    private int getItemTopSpacing(int position){
        if(isFirstRow(position)){
            return this.verticalItemSpacingInPx;
        }
        return this.verticalItemSpacingInPx/2;
    }
    private int getItemBottomSpacing(int position,int childCount){
        if(isLastRow(position,childCount)){
            return this.verticalItemSpacingInPx;
        }
        return this.verticalItemSpacingInPx/2;
    }

    private boolean isLastRow(int position, int childCount){
        return position == childCount -1 || position == childCount-2;
    }
    private boolean isFirstRow(int position){
        return position ==  0 || position == 1;
    }
}
