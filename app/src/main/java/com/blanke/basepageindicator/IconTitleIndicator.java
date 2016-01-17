package com.blanke.basepageindicator;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by blanke on 15-12-9.
 */
public class IconTitleIndicator extends TitleIndicator {
    public static final int LEFT = 0, TOP = 1, RIGHT = 2, BOTTOM = 3;
    private int mIconWidth, mIconHeight;
    private int mIconOrientation = TOP;
    private boolean isShowTitle = true;

    public IconTitleIndicator(Context context) {
        this(context, null);
    }

    public IconTitleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setItemUnderPaint(null);
    }

    @Override
    protected View getTabItemView(PagerAdapter adapter, int position) {
        IconPageAdapter iconPageAdapter = null;
        if (adapter instanceof IconPageAdapter) {
            iconPageAdapter = (IconPageAdapter) adapter;
        }
        final IconTextView view = new IconTextView(getContext());
        if (isShowTitle) {
            CharSequence title = adapter.getPageTitle(position);
            view.setText(title == null ? "" : title);
        }
        view.setTextSize(getTextSize());
        view.iconWidth = mIconWidth;
        view.iconHeight = mIconHeight;
        view.iconOrientation = mIconOrientation;
        if (position == adapter.getCount() - 1) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    Log.d("icon indicator", view.getMeasuredWidth() + " , " + view.getPaddingLeft() + "," + view.getCompoundPaddingLeft());
                }//notitle  : 350,50,50     title: 250,50,50
            });
        }
        int c = getTextColorResId();
        if (c != 0) {
            view.setTextColor(getResources().getColorStateList(c));
        }
        if (iconPageAdapter != null) {
            view.setIcon(iconPageAdapter.getIconResId(position));
        }
        return view;
    }

    class IconTextView extends TextView {
        int iconWidth;
        int iconHeight;
        int iconOrientation = TOP;

        public IconTextView(Context context) {
            super(context);
            setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            setGravity(Gravity.CENTER);
            setCompoundDrawablePadding(0);
        }

        public void setIcon(int resId) {
            if (resId > 0) {
                final Resources resources = getContext().getResources();
                Drawable icon = resources.getDrawable(resId);
                int width = iconWidth == 0 ? icon.getIntrinsicWidth() : iconWidth;
                int height = iconHeight == 0 ? icon.getIntrinsicHeight() : iconHeight;
                icon.setBounds(0, 0, width, height);
                switch (iconOrientation) {
                    case LEFT:
                        setCompoundDrawables(icon, null, null, null);
                        break;
                    case TOP:
                        setCompoundDrawables(null, icon, null, null);
                        break;
                    case RIGHT:
                        setCompoundDrawables(null, null, icon, null);
                        break;
                    case BOTTOM:
                        setCompoundDrawables(null, null, null, icon);
                        break;
                }
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            if (getText().length() == 0) {
                int widthMode = MeasureSpec.getMode(widthMeasureSpec);
                int heightMode = MeasureSpec.getMode(heightMeasureSpec);
                int widthSize = MeasureSpec.getSize(widthMeasureSpec);
                int heightSize = MeasureSpec.getSize(heightMeasureSpec);
                int w = iconWidth;
                int h = iconHeight;
                if (widthMode == MeasureSpec.EXACTLY) {
                    w = widthSize;
                }
                if (heightMode == MeasureSpec.EXACTLY) {
                    h = heightSize;
                }
                w += getPaddingLeft() + getPaddingRight();//
//                w += (getCompoundPaddingLeft() + getCompoundPaddingRight()) / 2;
                h += getPaddingTop() + getPaddingBottom();//
//                h += (getCompoundPaddingTop() + getCompoundPaddingBottom()) / 2;
                setMeasuredDimension(w, h);
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }

    }

    public int getIconWidth() {
        return mIconWidth;
    }

    public void setIconWidth(int mIconWidth) {
        this.mIconWidth = mIconWidth;
    }

    public int getIconHeight() {
        return mIconHeight;
    }

    public void setIconHeight(int mIconHeight) {
        this.mIconHeight = mIconHeight;
    }

    public void setIconWidthHeight(int width, int height) {
        this.mIconWidth = width;
        this.mIconHeight = height;
    }

    public void setIconWidthHeight(int widthHeight) {
        setIconWidthHeight(widthHeight, widthHeight);
    }

    public int getIconOrientation() {
        return mIconOrientation;
    }

    public void setIconOrientation(int mIconOrientation) {
        this.mIconOrientation = mIconOrientation;
    }

    public boolean isShowTitle() {
        return isShowTitle;
    }

    public void setIsShowTitle(boolean isShowTitle) {
        this.isShowTitle = isShowTitle;
    }
}