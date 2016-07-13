package com.miuhouse.yourcompany.teacher.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.miuhouse.yourcompany.teacher.utils.L;

/** 不能左右滑动，懒加载的ViewPager
 * Created by khb on 2016/7/13.
 */
public class CantScrollViewPager extends LazyViewPager {

    private boolean isPagingEnabled;

    public CantScrollViewPager(Context context) {
        super(context);
    }

    public CantScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private float startx;
    private float starty;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        startx = ev.getX();
        starty = ev.getY();
        return this.isPagingEnabled && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float x;
        float y;
        x = ev.getX();
        y = ev.getY();
        L.i("x:"+Math.abs(x-startx)+" -- "+"y:"+Math.abs(y-starty));
        if (Math.abs(x-startx)/Math.abs(y-starty)>1) {
            L.i("true");
            return true;
        }else {
            L.i("false");
            return false;
        }
    }

    public void setPaginEnabled(boolean isEnabled){
        this.isPagingEnabled = isEnabled;
    }
}
