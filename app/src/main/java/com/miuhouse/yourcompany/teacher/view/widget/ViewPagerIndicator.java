package com.miuhouse.yourcompany.teacher.view.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miuhouse.yourcompany.teacher.R;

import java.util.List;

/**
 * Created by khb on 2015/8/27.
 */
public class ViewPagerIndicator extends LinearLayout {

    /**
     * 绘制三角形的画笔
     */
    private Paint mPaint;
    /**
     * path构成一个三角形
     */
    private Path mPath;
    /**
     * 三角形的宽度
     */
    private int mTriangleWidth;
    /**
     * 三角形高度
     */
    private int mTriangleHeight;
    /**
     * 三角形的宽度为单个tab的1/6
     */
    private static final float RATIO_TRIANGLE = 1.0f/6;
    /**
     * 三角形最大宽度
     */
    private int DIMENSION_TRIANGLE_WIDTH = (int) (getScreenWidth()/3*RATIO_TRIANGLE);
    /**
     * 初始时，三角形指示器的偏移量
     */
    private int mInitTranslationX;
    /**
     * 手指滑动时的偏移量
     */
    private float mTranslationX;



    private int mRectangleWidth;
    private int mRectangleHeight = 6;
    private static final float RATIO_RECTANGLE = 1.0f/1;
    private int DIMENSION_RECTANGLE_WIDTH = (int) (getScreenWidth()/3*RATIO_RECTANGLE);




    /**
     * 默认的Tab数量
     */
    private static final int COUNT_DEFAULT_TAB = 4;
    /**
     * tab数量
     */
    private int mTabVisibleCount = COUNT_DEFAULT_TAB;
    /**
     * tab上的内容
     */
    private List<String> mTabTitles;
    /**
     * 与之绑定的ViewPager
     */
    public ViewPager mViewPager;
    /**
     * 标题正常时的颜色
     */
    private static final int COLOR_TEXT_NORMAL = 0x77FFFFFF;
    /**
     * 标题选中时的颜色
     */
    private static final int COLOR_TEXT_HIGHLIGHTCOLOR = 0xFFFFFFFF;
    private List<Integer> mTabImgs;

    public static final int INDICATOR_TYPE_TRIANGLE = 0;
    public static final int INDICATOR_TYPE_RECTANGLE = 1;
    private int indicatorType;

    public ViewPagerIndicator(Context context){
        this(context, null);
    }

    public ViewPagerIndicator(Context context, int type){
        this(context, null);
        indicatorType = type;
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        mTabVisibleCount = mTypedArray.getInt(R.styleable.ViewPagerIndicator_item_count,
                COUNT_DEFAULT_TAB);
        indicatorType = mTypedArray.getInt(R.styleable.ViewPagerIndicator_indicator_type, INDICATOR_TYPE_TRIANGLE);
        if (mTabVisibleCount < 0){
            mTabVisibleCount = COUNT_DEFAULT_TAB;
        }
        mTypedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
//        mPaint.setColor(Color.parseColor("#ffffffff"));
        mPaint.setColor(getResources().getColor(R.color.white));
        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setPathEffect(new CornerPathEffect(3));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        if (count == 0){
            return ;
        }

        //设置每个tab的宽度
        for (int i = 0;i<count;i++){
            View view = getChildAt(i);
            LayoutParams params = (LayoutParams) view.getLayoutParams();
            params.width = getScreenWidth()/mTabVisibleCount;
            view.setLayoutParams(params);
//            view.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
        }
        //设置点击事件
        setItemClickEvent();
    }

    /**
     * 初始化三角形宽度
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth = (int) (w/mTabVisibleCount*RATIO_TRIANGLE);
        mTriangleWidth = Math.min(DIMENSION_TRIANGLE_WIDTH, mTriangleWidth);

        mRectangleWidth = (int) (w/mTabVisibleCount*RATIO_RECTANGLE);
        mRectangleWidth = Math.min(DIMENSION_RECTANGLE_WIDTH, mRectangleWidth);
        if (indicatorType == INDICATOR_TYPE_TRIANGLE) {
            initTriangle();
//        初始化三角形的偏移量
            mInitTranslationX = getWidth()/mTabVisibleCount/2 - mTriangleWidth/2;
        }else {
            initRectangle();
//        初始化条形偏移量
            mInitTranslationX = 0;
        }

    }

    /**
     * 初始化三角形指示器
     */
    private void initTriangle() {
        mPath = new Path();
        mTriangleHeight = (int) (mTriangleWidth/2/Math.sqrt(2));    //三角形的高度
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.close();
    }

    private void initRectangle() {
        mPath =new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(0, -mRectangleHeight);
        mPath.lineTo(mRectangleWidth, -mRectangleHeight);
        mPath.lineTo(mRectangleWidth, 0);
        mPath.close();
    }

    //    点击切换pager
    private void setItemClickEvent() {
        int count = getChildCount();
        for (int i = 0; i<count; i++){
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }

//    绘制指示器
    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 1);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    public void setViewPager(ViewPager viewPager, int position){
        this.mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(position, positionOffset);
                if (myOnPageChangeListener != null) {
                    myOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
//                设置字体颜色高亮
                resetTextViewColor(position);
                highListTextView(position);
                if (myOnPageChangeListener != null) {
                    myOnPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int position) {
                if (myOnPageChangeListener != null) {
                    myOnPageChangeListener.onPageScrollStateChanged(position);
                }
            }
        });
        mViewPager.setCurrentItem(position);
        highListTextView(position);

    }


//    重置文本颜色
    private void resetTextViewColor(int position) {
        for (int i = 0; i<getChildCount(); i++){
            if (i!=position) {
                View view = getChildAt(i);
                if (view instanceof TextView) {
                    TextView tv = (TextView) view;
                    tv.setTextColor(COLOR_TEXT_NORMAL);
                }
                if (view instanceof ImageView) {
                    view.setSelected(false);
                }
            }
        }
    }
//    高亮文本颜色
    private void highListTextView(int position) {
        View view = getChildAt(position);
        if (view instanceof TextView){
            TextView tv = (TextView)view;
            tv.setTextColor(COLOR_TEXT_HIGHLIGHTCOLOR);
        }
        if (view instanceof ImageView){
            view.setSelected(true);
        }
    }
//    指示器跟随手指滚动，以及容器滚动
    private void scroll(int position, float positionOffset) {
//        指针随着手指移动的偏移量
        mTranslationX = getWidth()/mTabVisibleCount*(position+positionOffset);
        int tabWidth = getScreenWidth()/mTabVisibleCount;
        if (positionOffset>0 //指针已经移动
                && position>=(mTabVisibleCount-2)   //指针移到倒数第二个
                && getChildCount()>mTabVisibleCount //tab总数量大于可显示的的tab数
                ){
            if (mTabVisibleCount > 1){
                this.scrollTo(
                        (position-(mTabVisibleCount-2))*tabWidth+(int)(tabWidth*positionOffset),    //容器是反方向移动
                        0
                );
            }else{  //tab数为1时
                this.scrollTo(
                        position*tabWidth+(int)(tabWidth*positionOffset),
                        0
                );
            }
        }
        invalidate();

    }

    public  interface MyOnPageChangeListener{
        public void onPageScrolled(int position, float positionOffset, int positinoOffsetPixels);
        public void onPageSelected(int position);
        public void onPageScrollStateChanged(int position);
    }
    private MyOnPageChangeListener myOnPageChangeListener;
    public void setMyOnPageChangeListener(MyOnPageChangeListener myOnPageChangeListener){
        this.myOnPageChangeListener = myOnPageChangeListener;
    }

    public  void setTabVisibleCount(int count){
        this.mTabVisibleCount = count;
    }

    public  void setTabItemTitles(List<String> list) {
        if (!list.isEmpty()) {
            this.removeAllViews();
            this.mTabTitles = list;
            for (String title : list) {
                addView(generateView(title));
            }
            setItemClickEvent();
        }
    }

    public  void setTabItemImgs(List<Integer> list) {
        if (!list.isEmpty()) {
            this.removeAllViews();
            this.mTabImgs = list;
            for (Integer res : list) {
                addView(generateView(res));
            }
            setItemClickEvent();
        }
    }


    private View generateView(String title) {
        TextView tv = new TextView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.width = getScreenWidth() / mTabVisibleCount;
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(COLOR_TEXT_NORMAL);
        tv.setText(title);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setLayoutParams(params);
        return tv;
    }

    private View generateView(int res) {
        final ImageView imageView = new ImageView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = getScreenWidth() / mTabVisibleCount;
        params.gravity = Gravity.CENTER;
        imageView.setPadding(0
                , getResources().getDimensionPixelSize(R.dimen.home_indicator_padding)
                , 0
                , getResources().getDimensionPixelSize(R.dimen.home_indicator_padding));
        imageView.setImageResource(res);
        imageView.setLayoutParams(params);
//        imageView.setBackgroundColor(getResources().getColor(R.color.themeColor));
//        setImageTintSelector(imageView, res);
        setImageSelector(imageView, res);
        return imageView;
    }

    private void setImageSelector(ImageView imageView, int res) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{-android.R.attr.state_selected}
                , getResources().getDrawable(res));
        switch (res) {
            case R.mipmap.home_msg_n:
                drawable.addState(new int[]{android.R.attr.state_selected}
                        , getResources().getDrawable(R.mipmap.home_msg_s));
                break;
            case R.mipmap.home_orderlist_n:
                drawable.addState(new int[]{android.R.attr.state_selected}
                        , getResources().getDrawable(R.mipmap.home_orderlist_s));
                break;
            case R.mipmap.home_account_n:
                drawable.addState(new int[]{android.R.attr.state_selected}
                        , getResources().getDrawable(R.mipmap.home_account_s));
                break;
        }
        imageView.setImageDrawable(drawable);
    }

    //    代码实现着色选择器
    private void setImageTintSelector(ImageView imageView, int resId) {
        Drawable drawable = ContextCompat.getDrawable(getContext(),resId);
        int [] colors = new int[]{ContextCompat.getColor(getContext(), android.R.color.holo_red_light)
                                    ,ContextCompat.getColor(getContext(), android.R.color.white)};
        int [][] states = new int[colors.length][];
        states[0] = new int [] {android.R.attr.state_selected};
        states[1] = new int []{};
        ColorStateList colorStateList = new ColorStateList(states, colors);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(states[0], drawable);
        stateListDrawable.addState(states[1], drawable);
        Drawable.ConstantState constantState = stateListDrawable.getConstantState();
        drawable = DrawableCompat.wrap(constantState == null ?
            stateListDrawable :
            constantState.newDrawable().mutate());
        DrawableCompat.setTintList(drawable, colorStateList);
        imageView.setImageDrawable(drawable);
    }


    /**
    * 获得屏幕的宽度
    *
            * @return
            */
    public int getScreenWidth()
    {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

}

















