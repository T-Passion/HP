package com.passion.hp.module.home.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.passion.hp.R;
import com.passion.hp.module.home.contract.HomeContract;
import com.passion.libbase.AbstractBaseFragment;
import com.passion.libbase.constants.RouterPath;
import com.passion.libbase.imp.LayoutId;
import com.passion.libutils.ConvertUtil;
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


    private IndicatorViewPager mIndicatorPagerContainer;

    @Override
    public void initVars(View view) {
        mHomePagerTabs.setOnTransitionListener(new OnTransitionTextListener().setColor(Color.RED, Color.LTGRAY));
        mHomePagerTabs.setScrollBar(new ColorBar(getContext(), Color.RED, ConvertUtil.dp2px(getContext(), 2)));
        mHomePagers.setOffscreenPageLimit(2);

        mIndicatorPagerContainer = new IndicatorViewPager(mHomePagerTabs,mHomePagers);
        mIndicatorPagerContainer.setAdapter(new MyAdapter());
    }

    @Override
    public void loadInitDta() {

    }


    private class MyAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {
        private String[] versions = {"Cupcake", "Donut", "Éclair", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lolipop", "Marshmallow"};
        private String[] names = {"纸杯蛋糕", "甜甜圈", "闪电泡芙", "冻酸奶", "姜饼", "蜂巢", "冰激凌三明治", "果冻豆", "奇巧巧克力棒", "棒棒糖", "棉花糖"};

        @Override
        public int getCount() {
            return versions.length;
        }

        @SuppressLint("RestrictedApi")
        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new TextView(getContext());
            }
            TextView textView = (TextView) convertView;
            textView.setText(versions[position]);

            int witdh = getTextWidth(textView);
            int padding = ConvertUtil.dp2px(getContext(), 8);
            //因为wrap的布局 字体大小变化会导致textView大小变化产生抖动，这里通过设置textView宽度就避免抖动现象
            //1.3f是根据上面字体大小变化的倍数1.3f设置
            textView.setWidth((int) (witdh * 1.3f) + padding);

            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new TextView(container.getContext());
            }
            TextView textView = (TextView) convertView;
            textView.setText(names[position]);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.GRAY);
            return convertView;
        }

        @Override
        public int getItemPosition(Object object) {
            //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
            // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
            return PagerAdapter.POSITION_UNCHANGED;
        }

        private int getTextWidth(TextView textView) {
            if (textView == null) {
                return 0;
            }
            Rect bounds = new Rect();
            String text = textView.getText().toString();
            Paint paint = textView.getPaint();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int width = bounds.left + bounds.width();
            return width;
        }

    }

}
