package com.blanke.basepageindicator.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blanke.basepageindicator.IconTitleIndicator;
import com.blanke.basepageindicator.Indicatorable;
import com.blanke.basepageindicator.MIUITitleIndicator;
import com.blanke.basepageindicator.R;
import com.blanke.basepageindicator.TitleIndicator;

public class AllStyleIndicatorActivity extends AppCompatActivity {

    private TitleIndicator mIndicatorTitle;
    private MIUITitleIndicator mIndicatorMiui;
    private IconTitleIndicator mIndicatorIcon;
    private ViewPager mViewpager;
    private String title[] = {"电话", "短信", "朋友", "QQ", "微信", "电话", "短信", "朋友", "QQ", "微信", "电话", "短信"};
    private int icons[] = {R.drawable.actionbar_discover, R.drawable.actionbar_music,
            R.drawable.actionbar_friends, R.drawable.actionbar_discover, R.drawable.actionbar_music,
            R.drawable.actionbar_friends, R.drawable.actionbar_discover, R.drawable.actionbar_music,
            R.drawable.actionbar_friends, R.drawable.actionbar_discover, R.drawable.actionbar_music,
            R.drawable.actionbar_friends};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_style_indicator);

        mIndicatorTitle = (TitleIndicator) findViewById(R.id.activity_all_indicator_title);
        mIndicatorMiui = (MIUITitleIndicator) findViewById(R.id.activity_all_indicator_miui);
        mIndicatorIcon = (IconTitleIndicator) findViewById(R.id.activity_all_indicator_icon);
        mViewpager = (ViewPager) findViewById(R.id.activity_all_viewpager);
        mViewpager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        mIndicatorTitle.setIsAllInScreen(false);
        mIndicatorTitle.setItemPaddingTop(15);
        mIndicatorTitle.setItemPaddingBottom(15);
        mIndicatorTitle.setItemPaddingLeft(50);
        mIndicatorTitle.setItemPaddingRight(50);
        mIndicatorTitle.setItemUnderHeight(4);
        mIndicatorTitle.setItemBackgroundResId(R.drawable.selector_tabbackground);
        mIndicatorTitle.setItemUnderColor(Color.parseColor("#CE3D3A"));
        mIndicatorTitle.setTextColorResId(R.color.selector_tabtext);

        mIndicatorMiui.setIsAllInScreen(false);
        mIndicatorMiui.setItemPaddingTop(15);
        mIndicatorMiui.setItemPaddingBottom(15);
        mIndicatorMiui.setItemPaddingLeft(50);
        mIndicatorMiui.setItemPaddingRight(50);
        mIndicatorMiui.setItemBackgroundResId(R.drawable.selector_tabbackground);
        mIndicatorMiui.setItemUnderColor(Color.parseColor("#ffffff"));
        mIndicatorMiui.setTextColorResId(R.color.selector_miui_tabtext);
        mIndicatorMiui.setVerticalOffset(15);
        mIndicatorMiui.setSplitFactory(3);
        mIndicatorMiui.setBackgroundColor(Color.parseColor("#99000099"));

        mIndicatorIcon.setIsAllInScreen(false);
        mIndicatorIcon.setIconWidthHeight(150);
        mIndicatorIcon.setIsShowTitle(false);
        mIndicatorIcon.setItemPaddingLeft(50);
        mIndicatorIcon.setItemPaddingRight(50);
        mIndicatorIcon.setBackgroundColor(Color.parseColor("#CE3D3A"));

        mIndicatorTitle.setViewPager(mViewpager);
        mIndicatorMiui.setViewPager(mViewpager);
        mIndicatorIcon.setViewPager(mViewpager);


        mIndicatorIcon.setVisibility(View.GONE);////存在bug,未解决,去掉这行，可看到效果
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
