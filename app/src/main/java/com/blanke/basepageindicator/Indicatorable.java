package com.blanke.basepageindicator;

import android.support.v4.view.ViewPager;

public interface Indicatorable {
    interface IconPageAdapter {
        int getIconResId(int position);
    }

    BaseIndicator setViewPager(ViewPager view);

    void setViewPager(ViewPager view, int initialPosition);

    void setCurrentItem(int item);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);

    void notifyDataSetChanged();
}
