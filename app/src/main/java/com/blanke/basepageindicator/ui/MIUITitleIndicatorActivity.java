package com.blanke.basepageindicator.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.blanke.basepageindicator.MIUITitleIndicator;
import com.blanke.basepageindicator.R;

/**
 * Created by blanke on 15-12-10.
 */
public class MIUITitleIndicatorActivity extends AppCompatActivity {
    private MIUITitleIndicator mIndicator, mIndicator2;
    private ViewPager mViewpager;
    private String title[] = {"电话", "短信", "联系人", "更多"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miuititleindicator);
        mIndicator = (MIUITitleIndicator) findViewById(R.id.activity_miuititle_indicator);
        mIndicator2 = (MIUITitleIndicator) findViewById(R.id.activity_miuititle_indicator2);
        mViewpager = (ViewPager) findViewById(R.id.activity_miuititle_viewpager);

        mViewpager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        mIndicator.setTextSize(18);
        mIndicator.setItemPaddingTop(18);
        mIndicator.setItemPaddingBottom(18);
        mIndicator.setItemBackgroundResId(R.drawable.selector_tabbackground);
        mIndicator.setItemUnderColor(Color.parseColor("#ffffff"));
        mIndicator.setTextColorResId(R.color.selector_miui_tabtext);
        mIndicator.setVerticalOffset(15);
        mIndicator.setViewPager(mViewpager);

        mIndicator2.setTextSize(18);
        mIndicator2.setItemPaddingTop(18);
        mIndicator2.setItemPaddingBottom(18);
        mIndicator2.setItemBackgroundResId(R.drawable.selector_tabbackground);
        mIndicator2.setItemUnderColor(Color.parseColor("#ffffff"));
        mIndicator2.setTextColorResId(R.color.selector_miui_tabtext);
        mIndicator2.setVerticalOffset(15);
        mIndicator2.setIsScrollFollow(false);
        mIndicator2.setViewPager(mViewpager);
    }

    class MyAdapter extends FragmentPagerAdapter {
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
    }
}