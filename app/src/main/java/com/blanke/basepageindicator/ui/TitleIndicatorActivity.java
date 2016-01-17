package com.blanke.basepageindicator.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.blanke.basepageindicator.R;
import com.blanke.basepageindicator.TitleIndicator;

/**
 * Created by blanke on 15-12-10.
 */
public class TitleIndicatorActivity extends AppCompatActivity {
    private TitleIndicator mIndicator, mIndicator2;
    private ViewPager mViewpager;
    private String title[] = {"个性推荐", "歌单", "主播电台", "排行榜"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titleindicator);
        mIndicator = (TitleIndicator) findViewById(R.id.activity_title_indicator);
        mIndicator2 = (TitleIndicator) findViewById(R.id.activity_title_indicator2);
        mViewpager = (ViewPager) findViewById(R.id.activity_title_viewpager);

        mViewpager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        mIndicator.setTextSize(18);
        mIndicator.setItemPaddingTop(15);
        mIndicator.setItemPaddingBottom(15);
        mIndicator.setItemUnderHeight(2);
        mIndicator.setIsAllInScreen(true);
        mIndicator.setItemBackgroundResId(R.drawable.selector_tabbackground);
        mIndicator.setItemUnderColor(Color.parseColor("#CE3D3A"));
        mIndicator.setTextColorResId(R.color.selector_tabtext);
        mIndicator.setViewPager(mViewpager);

        mIndicator2.setTextSize(18);
        mIndicator2.setItemPaddingTop(15);
        mIndicator2.setItemPaddingBottom(15);
        mIndicator2.setItemUnderHeight(8);
        mIndicator2.setIsAllInScreen(true);
        mIndicator2.setTabUnderHeight(1);
        mIndicator2.setTabUnderColor(Color.parseColor("#66000000"));
        mIndicator2.setDivederColor(Color.parseColor("#66000000"));
        mIndicator2.setDividerWidth(1);
        mIndicator2.setDividerPadding(25);
        mIndicator2.setItemBackgroundResId(R.drawable.selector_tabbackground);
        mIndicator2.setItemUnderColor(Color.parseColor("#CE3D3A"));
        mIndicator2.setTextColorResId(R.color.selector_tabtext);
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