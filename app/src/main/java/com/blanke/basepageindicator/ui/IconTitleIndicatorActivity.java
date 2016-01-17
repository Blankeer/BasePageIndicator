package com.blanke.basepageindicator.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.blanke.basepageindicator.IconTitleIndicator;
import com.blanke.basepageindicator.Indicatorable;
import com.blanke.basepageindicator.R;

/**
 * Created by blanke on 15-12-10.
 */
public class IconTitleIndicatorActivity extends AppCompatActivity {
    private IconTitleIndicator mIndicator, mIndicator2, mIndicator3;
    private ViewPager mViewpager;
    private String title[] = {"发现", "音乐", "朋友"};
    private int icons[] = {R.drawable.actionbar_discover, R.drawable.actionbar_music, R.drawable.actionbar_friends};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icontitleindicator);
        mIndicator = (IconTitleIndicator) findViewById(R.id.activity_icontitle_indicator);
        mIndicator2 = (IconTitleIndicator) findViewById(R.id.activity_icontitle_indicator2);
        mIndicator3 = (IconTitleIndicator) findViewById(R.id.activity_icontitle_indicator3);
        mViewpager = (ViewPager) findViewById(R.id.activity_icontitle_viewpager);
        mViewpager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        init1();
        init2();
        init3();
    }

    private void init1() {
        mIndicator.setTextSize(14);
        mIndicator.setTextColorResId(R.color.selector_miui_tabtext);
        mIndicator.setIconWidthHeight(150);
        mIndicator.setIsShowTitle(false);
        mIndicator.setViewPager(mViewpager);
    }

    private void init2() {
        mIndicator2.setTextSize(14);
        mIndicator2.setTextColorResId(R.color.selector_miui_tabtext);
        mIndicator2.setIconWidthHeight(150);
        mIndicator2.setViewPager(mViewpager);
    }

    private void init3() {
        mIndicator3.setTextSize(14);
        mIndicator3.setTextColorResId(R.color.selector_miui_tabtext);
        mIndicator3.setIconWidthHeight(150);
        mIndicator3.setIconOrientation(IconTitleIndicator.LEFT);
        mIndicator3.setViewPager(mViewpager);
    }

    class MyAdapter extends FragmentPagerAdapter implements Indicatorable.IconPageAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return OtherFragment.newInstance(title[position]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public int getIconResId(int position) {
            return icons[position];
        }
    }
}