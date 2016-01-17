package com.blanke.basepageindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by blanke on 15-12-5.
 */
public abstract class BaseIndicator extends HorizontalScrollView implements Indicatorable, ViewPager.OnPageChangeListener {
    private final static String TAG = "BaseIndicator";
    private LinearLayout.LayoutParams mTabItemLayoutParams;
    private LinearLayout.LayoutParams mTabLayoutParams;
    private LinearLayout.LayoutParams mTabItemScreenLayoutParams;//所有item在一个屏幕均分时候的params
    private LinearLayout mContentLayout;
    private ViewPager mViewPager;
    private PagerAdapter mViewPagerAdapter;
    private ViewPager.OnPageChangeListener mUserPageListener;//用户设置的监听器
    private int mTabCount;
    private int mCurrentPosition, mScrollPosition;
    private float mCurrentPositionOffset;//每次滑动的偏移量，通过这个变量重绘，指示器才有跟随滑动效果
    private int mItemBackgroundResId;
    private Paint mItemUnderPaint;//每个item下划线，也就是指示器的paint
    private Paint mDividerPaint;//item间的分隔线
    private Paint mTabUnderPaint;//整个tab下面的横线
    private boolean isAllInScreen = true;//是否所有item在一个屏幕均分，也就是不会左右滑动
    private int mItemPadding, mItemPaddingLeft, mItemPaddingTop, mItemPaddingRight, mItemPaddingBottom;//item padding
    private float mItemUnderHeight = 2;
    private float mTabUnderHeight = 0;
    private float mDividerWidth = 0;
    private float mDividerPadding = 15;
    private boolean isScrollFollow = true;//指示器是否跟随滑动
    private boolean isUserClickScrolled = false;//是否是用户点击产生的滑动

    public BaseIndicator(Context context) {
        this(context, null);
    }

    public BaseIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setHorizontalScrollBarEnabled(false);
        setFillViewport(true);
        setWillNotDraw(false);
        mTabLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mTabItemLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        mTabItemScreenLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        mContentLayout = new LinearLayout(getContext());
        mContentLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(mContentLayout, mTabLayoutParams);
        mItemUnderPaint = new Paint();
        mItemUnderPaint.setAntiAlias(true);
        mItemUnderPaint.setStyle(Paint.Style.FILL);
        mDividerPaint = new Paint();
        mDividerPaint.setAntiAlias(true);
        mDividerPaint.setStyle(Paint.Style.FILL);
        mTabUnderPaint = new Paint();
        mTabUnderPaint.setAntiAlias(true);
        mTabUnderPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public BaseIndicator setViewPager(ViewPager viewPager) {
        if (viewPager == null) {
            throwException("ViewPager不能为空");
        }
        this.mViewPager = viewPager;
        this.mViewPagerAdapter = viewPager.getAdapter();
        if (mViewPagerAdapter == null) {
            throwException("ViewPage.getAdapter()不能为空");
        }
        mViewPager.addOnPageChangeListener(this);
        notifyDataSetChanged();
        return this;
    }

    @Override
    public void setViewPager(ViewPager viewPager, int position) {
        setViewPager(viewPager);
        setCurrentItem(position);
    }

    @Override
    public void setCurrentItem(int position) {
        mViewPager.setCurrentItem(position);
        mCurrentPosition = position;
        setTabSelected();
        invalidate();
    }

    private void setTabSelected() {
//        Log.d(TAG, "setTabSelected position=" + mCurrentPosition);
        for (int i = 0; i < mTabCount; i++) {
            View child = mContentLayout.getChildAt(i);
            child.setSelected(i == mCurrentPosition);
        }
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.mUserPageListener = listener;
    }

    /**
     * 获得item
     *
     * @param adapter
     * @param position
     * @return
     */
    protected abstract View getTabItemView(PagerAdapter adapter, int position);

    @Override
    public void notifyDataSetChanged() {
        mContentLayout.removeAllViews();
        boolean temp = mItemPadding > 0;
        mTabCount = mViewPagerAdapter.getCount();
        for (int i = 0; i < mTabCount; i++) {
            View v = getTabItemView(mViewPagerAdapter, i);
            v.setBackgroundResource(mItemBackgroundResId);
            v.setLayoutParams(mTabItemLayoutParams);
            if (isAllInScreen) {//item禁止滑动，都在屏幕类，宽度均分
                v.setLayoutParams(mTabItemScreenLayoutParams);
            }
            if (temp) {
                v.setPadding(mItemPadding, mItemPadding, mItemPadding, mItemPadding);
            } else {
                v.setPadding(mItemPaddingLeft, mItemPaddingTop, mItemPaddingRight, mItemPaddingBottom);
            }
            final int finalI = i;
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalI != mCurrentPosition) {
                        isUserClickScrolled = true;
                        mViewPager.setCurrentItem(finalI);
                        setTabSelected();
                    }
                }
            });
            mContentLayout.addView(v);
        }
        post(new Runnable() {
            public void run() {
                mCurrentPosition = mViewPager.getCurrentItem();
                scrollToChild(mCurrentPosition, 0);
                setTabSelected();
            }
        });
    }

    /**
     * 主要是横向滚动，跟随viewpager的滚动
     *
     * @param position
     * @param offset
     */
    private void scrollToChild(int position, int offset) {
        if (mTabCount == 0 || isAllInScreen) {
            return;
        }
        View v = mContentLayout.getChildAt(position);
//        int newScrollX = v.getLeft() + offset - (getWidth() - v.getWidth()) / 2;//item宽度不相等时会出现滑动效果不好
        int newScrollX = v.getLeft() + offset - (getWidth()) / 2;//这里每次滑动的距离是屏幕的一半，所以最终selected的item会是屏幕中线右对齐
        scrollTo(newScrollX, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode() || mTabCount == 0) {
            return;
        }
        final int height = getHeight();
        View currentTab = mContentLayout.getChildAt(mScrollPosition);
        float lineLeft = currentTab.getLeft();
        float lineRight = currentTab.getRight();
        //  计算指示器的区域坐标
        if (mCurrentPositionOffset > 0f && mScrollPosition < mTabCount - 1) {
            View nextTab = mContentLayout.getChildAt(mScrollPosition + 1);
            final float nextTabLeft = nextTab.getLeft();
            final float nextTabRight = nextTab.getRight();
            lineLeft += (nextTabLeft - lineLeft) * mCurrentPositionOffset;
            lineRight += (nextTabRight - lineRight) * mCurrentPositionOffset;
//            Log.d(TAG, "onDraw　indicator mScrollPosition=" + mScrollPosition + ",left=" + lineLeft + " ,right=" + lineRight + ",nextTabLeft=" + nextTabLeft + ",nextTabRight=" + nextTabRight);
        }
        drawItemUnder(canvas, lineLeft + getPaddingLeft(), height - mItemUnderHeight, lineRight + getPaddingLeft(), height, mItemUnderPaint);
        drawTabUnder(canvas, 0, height - mTabUnderHeight, getWidth(), height, mTabUnderPaint);
        for (int i = 0; i < mTabCount - 1; i++) {
            View tab = mContentLayout.getChildAt(i);
            drawDivider(canvas, tab.getRight(), mDividerPadding, tab.getRight() + mDividerWidth, height - mDividerPadding, mDividerPaint);
        }
    }


    protected void drawItemUnder(Canvas canvas, float left, float top, float right, float bottom, Paint paint) {

    }

    protected void drawTabUnder(Canvas canvas, float left, float top, float right, float bottom, Paint paint) {

    }

    protected void drawDivider(Canvas canvas, float left, float top, float right, float bottom, Paint paint) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        Log.d(TAG, "onPageScrolled  position=" + position + " ,positionOffset=" + positionOffset);
        mScrollPosition = position;
        mCurrentPositionOffset = positionOffset;
        if (!isAllInScreen) {
            scrollToChild(position, (int) (positionOffset * mContentLayout.getChildAt(position).getWidth()));
        }
        if (mItemUnderPaint != null) {//不绘制指示器　没必要重绘
            if (isScrollFollow || isUserClickScrolled) {//没有动画跟随效果
                invalidate();
            }
        }
        if (mUserPageListener != null) {
            mUserPageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
//        Log.d(TAG, "onPageSelected position=" + position);
        mCurrentPosition = position;
        setTabSelected();
        if (!isScrollFollow && !isUserClickScrolled) {//不允许跟随滑动且不是用户主动点击滑动，直接绘制目标position的指示器
            mScrollPosition = position;
            mCurrentPositionOffset = 0;
            invalidate();
        }
        if (mUserPageListener != null) {
            mUserPageListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        Log.d(TAG,"state="+state);
        if (state == ViewPager.SCROLL_STATE_IDLE) {//滑动完成动画完全结束
            isUserClickScrolled = false;
//            invalidate();//当不允许跟随滑动时，有延迟。最终采用的在onPageSelected中判断
            if (!isAllInScreen) {
                scrollToChild(mViewPager.getCurrentItem(), 0);
            }
        }
        if (mUserPageListener != null) {
            mUserPageListener.onPageScrollStateChanged(state);
        }

    }

    private void throwException(String msg) {
        throw new IllegalStateException(msg);
    }

    public int getItemBackgroundResId() {
        return mItemBackgroundResId;
    }

    public void setItemBackgroundResId(int mItemBackgroundResId) {
        this.mItemBackgroundResId = mItemBackgroundResId;
    }

    public LinearLayout.LayoutParams getTabItemLayoutParams() {
        return mTabItemLayoutParams;
    }

    public void setTabItemLayoutParams(LinearLayout.LayoutParams mTabItemLayoutParams) {
        this.mTabItemLayoutParams = mTabItemLayoutParams;
//        notifyDataSetChanged();
    }

    public LinearLayout.LayoutParams getTabLayoutParams() {
        return mTabLayoutParams;
    }

    public void setTabLayoutParams(LinearLayout.LayoutParams mTabLayoutParams) {
        this.mTabLayoutParams = mTabLayoutParams;
        mContentLayout.setLayoutParams(mTabLayoutParams);
    }

    public ViewPager.OnPageChangeListener getOnPageListener() {
        return mUserPageListener;
    }

    public Paint getDividerPaint() {
        return mDividerPaint;
    }

    public void setDividerPaint(Paint mDividerPaint) {
        this.mDividerPaint = mDividerPaint;
    }

    public Paint getItemUnderPaint() {
        return mItemUnderPaint;
    }

    public void setItemUnderPaint(Paint mItemUnderPaint) {
        this.mItemUnderPaint = mItemUnderPaint;
    }

    public Paint getTabUnderPaint() {
        return mTabUnderPaint;
    }

    public void setTabUnderPaint(Paint mTabUnderPaint) {
        this.mTabUnderPaint = mTabUnderPaint;
    }

    public boolean isAllInScreen() {
        return isAllInScreen;
    }

    public void setIsAllInScreen(boolean isAllInScreen) {
        this.isAllInScreen = isAllInScreen;
    }

    public int getItemPadding() {
        return mItemPadding;
    }

    public void setItemPadding(int mItemPadding) {
        this.mItemPadding = mItemPadding;
    }

    public int getItemPaddingBottom() {
        return mItemPaddingBottom;
    }

    public void setItemPaddingBottom(int mItemPaddingBottom) {
        this.mItemPaddingBottom = mItemPaddingBottom;
    }

    public int getItemPaddingLeft() {
        return mItemPaddingLeft;
    }

    public void setItemPaddingLeft(int mItemPaddingLeft) {
        this.mItemPaddingLeft = mItemPaddingLeft;
    }

    public int getItemPaddingRight() {
        return mItemPaddingRight;
    }

    public void setItemPaddingRight(int mItemPaddingRight) {
        this.mItemPaddingRight = mItemPaddingRight;
    }

    public int getItemPaddingTop() {
        return mItemPaddingTop;
    }

    public void setItemPaddingTop(int mItemPaddingTop) {
        this.mItemPaddingTop = mItemPaddingTop;
    }

    public float getItemUnderHeight() {
        return mItemUnderHeight;
    }

    public void setItemUnderHeight(float mItemUbderHeight) {
        this.mItemUnderHeight = mItemUbderHeight;
        invalidate();
    }

    public float getDividerWidth() {
        return mDividerWidth;
    }

    public void setDividerWidth(float mDividerWidth) {
        this.mDividerWidth = mDividerWidth;
    }

    public float getTabUnderHeight() {
        return mTabUnderHeight;
    }

    public void setTabUnderHeight(float mTabUnderHeight) {
        this.mTabUnderHeight = mTabUnderHeight;
    }

    public float getDividerPadding() {
        return mDividerPadding;
    }

    public void setDividerPadding(float mDividerPadding) {
        this.mDividerPadding = mDividerPadding;
    }

    public void setItemUnderColor(int mItemUnderColor) {
        mItemUnderPaint.setColor(mItemUnderColor);
    }

    public void setTabUnderColor(int color) {
        mTabUnderPaint.setColor(color);
    }

    public void setDivederColor(int color) {
        mDividerPaint.setColor(color);
    }

    public boolean isScrollFollow() {
        return isScrollFollow;
    }

    public void setIsScrollFollow(boolean isScrollFollow) {
        this.isScrollFollow = isScrollFollow;
    }
}