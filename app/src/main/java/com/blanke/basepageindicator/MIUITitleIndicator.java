package com.blanke.basepageindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by blanke on 15-12-6.
 */
public class MIUITitleIndicator extends TitleIndicator {
    private Path mPath;
    private float mSplitFactory = 4;//控制三角形的大小
    private float mVerticalOffset = 5;//纵向偏移
    private float r;//三角形底边长


    public MIUITitleIndicator(Context context) {
        this(context, null);
    }

    public MIUITitleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        getItemUnderPaint().setPathEffect(new CornerPathEffect(3));
    }

    @Override
    protected View getTabItemView(PagerAdapter adapter, int position) {
        final View v = super.getTabItemView(adapter, position);
        if (position == 0 && mPath == null) {
            v.post(new Runnable() {
                @Override
                public void run() {
                    if (mPath == null) {
                        initPath(v.getWidth());
                    }
                }
            });
        }
        return v;
    }

    private void initPath(float viewWidth) {
        r = viewWidth / mSplitFactory;
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(r, 0);
        mPath.lineTo(r / 2, -r / 2);
        mPath.close();
        invalidate();//重绘
    }

    @Override
    protected void drawItemUnder(Canvas canvas, float left, float top, float right, float bottom, Paint paint) {
        if (mPath != null) {
            canvas.save();
            canvas.translate((left + right - r) / 2, bottom + mVerticalOffset);
            canvas.drawPath(mPath, paint);
            canvas.restore();
        }
    }

    public float getVerticalOffset() {
        return mVerticalOffset;
    }

    public void setVerticalOffset(float mVerticalOffset) {
        this.mVerticalOffset = mVerticalOffset;
    }

    public float getSplitFactory() {
        return mSplitFactory;
    }

    public void setSplitFactory(float mSplitFactory) {
        this.mSplitFactory = mSplitFactory;
    }
}
