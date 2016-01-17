#BasePageIndicator-ViewPager指示器

参考[JakeWharton/ViewPagerIndicator](https://github.com/JakeWharton/ViewPagerIndicator)和[astuetz/PagerSlidingTabStrip](https://github.com/astuetz/PagerSlidingTabStrip)的源码，感谢。
初衷是想自定义个指示器，看了上述的源码，干脆自己写出来了一个通用的BasePageIndicator，自定义指示器只需重写以下方法：`getTabItemView()` `drawItemUnder()` `drawTabUnder()` `drawDivider()`，第一个方法是得到item的view，后面几个是绘制Item下划线，Tab下划线，Item间的分隔线。
![gif](https://github.com/Blankeer/BasePageIndicator/blob/master/Screen.gif)
贴上几个常用的。
`TitleIndicator`代码：
```
/**
 * Created by blanke on 15-12-6.
 */
public class TitleIndicator extends BaseIndicator {
    private int mTextSize = 16;
    private int mTextColorResId;

    public TitleIndicator(Context context) {
        this(context, null);
    }

    public TitleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View getTabItemView(PagerAdapter adapter, int position) {
        TextView view = new TextView(getContext());
        view.setText(adapter.getPageTitle(position));
        view.setTextSize(mTextSize);
        view.setGravity(Gravity.CENTER);
        if (mTextColorResId != 0) {
            view.setTextColor(getResources().getColorStateList(mTextColorResId));
        }
        return view;
    }


    @Override
    protected void drawItemUnder(Canvas canvas, float left, float top, float right, float bottom, Paint paint) {
        if (paint != null) {
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }

    @Override
    protected void drawTabUnder(Canvas canvas, float left, float top, float right, float bottom, Paint paint) {
        canvas.drawRect(left, top, right, bottom, paint);
    }

    @Override
    protected void drawDivider(Canvas canvas, float left, float top, float right, float bottom, Paint paint) {
        canvas.drawRect(left, top, right, bottom, paint);
    }

    /*.... get() set()*/
}
```
 `MIUITitleIndicator`代码;

```
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
	/*...get() set()*/
}
```

写的不完善，仅供参考。 




