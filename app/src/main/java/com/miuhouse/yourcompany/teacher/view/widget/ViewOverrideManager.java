package com.miuhouse.yourcompany.teacher.view.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.miuhouse.yourcompany.teacher.R;

/**
 * Created by khb on 2016/6/7.
 */
public class ViewOverrideManager implements ValueAnimator.AnimatorUpdateListener {
    private View parentView;
    private ViewGroup.LayoutParams layoutParams;
    private ViewGroup container;
    private int viewIndex;

    public static final int LOADING = 0;
    public static final int NO_MSG = 1;
    public static final int NO_COMMENT = 2;
    public static final int NO_STUDENT = 3;
    public static final int NO_ORDER = 4;
    public static final int NO_NETWORK = 5;
    private View view;
    private TextView tvMsg;
    private ImageView img;
    private TextView button;
    private ImageView shadow;

    public ViewOverrideManager(View view) {
        this.parentView = view;
//        init();
    }

    private void init() {
        if (null == parentView){
            return ;
        }
        layoutParams = parentView.getLayoutParams();
        if (null != parentView.getParent()) {
            container = (ViewGroup) parentView.getParent();
        } else {
            container = (ViewGroup) parentView.getRootView().findViewById(android.R.id.content);
        }
        for (int i = 0; i < container.getChildCount(); i++) {
            if (parentView == container.getChildAt(i)) {
                viewIndex = i;
                break;
            }
        }
    }

    private void showLayout(View view) {
        if (null == container) {
            init();
        }
        if (container.getChildAt(viewIndex) != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            container.removeViewAt(viewIndex);
            container.addView(view, viewIndex, layoutParams);
        }

    }

    public void showLoading(){
        showLoading(LOADING, null);
    }

    public void showLoading(String msg) {
        showLoading(msg, false);
    }

    public void showLoading(int type, final OnExceptionalClick onExceptionalClick){
        showLoading(null, false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onExceptionalClick) {
                    restoreView();
                    onExceptionalClick.onExceptionalClick();
                }
            }
        });
        Context context = view.getContext();
        switch (type){
            case LOADING:
                animateLoading();
                break;
            case NO_MSG:
                img.setImageResource(R.mipmap.img_no_msg);
                tvMsg.setText(context.getResources().getString(R.string.exception_no_msg));
                break;
            case NO_COMMENT:
                img.setImageResource(R.mipmap.img_no_comment);
                tvMsg.setText(context.getResources().getString(R.string.exception_no_comment));
                break;
            case NO_STUDENT:
                img.setImageResource(R.mipmap.img_no_student);
                tvMsg.setText(context.getResources().getString(R.string.exception_no_student));
                button.setText("刷新");
                button.setVisibility(View.VISIBLE);
                break;
            case NO_ORDER:
                img.setImageResource(R.mipmap.img_no_order);
                tvMsg.setText(context.getResources().getString(R.string.exception_no_order));
                button.setText("立即前往");
                button.setVisibility(View.VISIBLE);
                break;
            case NO_NETWORK:
                img.setImageResource(R.mipmap.img_no_network);
                tvMsg.setText(context.getResources().getString(R.string.exception_no_network));
                button.setText("重试");
                button.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public void showLoading(String msg, boolean isShowProgress) {
        view = LayoutInflater.from(parentView.getContext()).inflate(R.layout.view_override, null);
//        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        img = (ImageView) view.findViewById(R.id.img);
        tvMsg = (TextView) view.findViewById(R.id.msg);
        shadow = (ImageView) view.findViewById(R.id.shadow);
        button = (TextView) view.findViewById(R.id.button);
        shadow.setVisibility(View.GONE);
//
//        if (isShowProgress) {
//            progressBar.setVisibility(View.VISIBLE);
//        }
//        else {
//            progressBar.setVisibility(View.GONE);
//        }

        if (msg!=null) {
            tvMsg.setText(msg);
        }
        tvMsg.setTextColor(parentView.getContext().getResources().getColor(R.color.primary_material_dark));
        showLayout(view);

//        Toast.makeText(parentView.getContext(), msg + "", Toast.LENGTH_LONG).show();


    }

    public void restoreView() {
        showLayout(parentView);
    }

    private float startX;
    private float startY;
    private float width;
    private float height;
    private float shadowWidth;
    private float shadowHeight;

    private void animateLoading() {
        shadow.setVisibility(View.VISIBLE);
        img.setImageResource(R.mipmap.loading);
        img.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                Log.i("tag", "\n l:" + left + "t:" + top + "r:" + right + "b:" + bottom +
//                        "\n L:" + oldLeft + "T:" + oldTop + "R:" + oldRight + "B:" + oldBottom);
                startX = left;
                startY = top;
                width = right - left;
                height = bottom - top;
            }
        });
        shadow.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                Log.i("tag", "\n l:" + left + "t:" + top + "r:" + right + "b:" + bottom +
//                        "\n L:" + oldLeft + "T:" + oldTop + "R:" + oldRight + "B:" + oldBottom);
                shadowWidth = right - left;
                shadowHeight = bottom - top;
            }
        });

        img.post(new Runnable() {
            @Override
            public void run() {
                animateJump();
            }
        });
    }

    private boolean isContinue = true;

    private void animateJump(){
        float endX;
        float endY = startY - 100f;
        ViewWrapper view = new ViewWrapper(img);
        ShadowWrapper shadowView = new ShadowWrapper(shadow);
        ValueAnimator downAnimator = ObjectAnimator.ofFloat(img, "y", endY, startY);
        downAnimator.setDuration(300);
        downAnimator.setInterpolator(new AccelerateInterpolator(2f));
        downAnimator.addUpdateListener(this);
//        downAnimator.setRepeatMode(ValueAnimator.REVERSE);
//        downAnimator.setRepeatCount(1);
        ValueAnimator upAnimator = ObjectAnimator.ofFloat(img, "y", startY, endY);
        upAnimator.setDuration(300);
        upAnimator.setInterpolator(new DecelerateInterpolator(2f));
        upAnimator.addUpdateListener(this);

        ValueAnimator widenAnimator = ObjectAnimator.ofFloat(view, "width", width, width+50f);
        widenAnimator.setDuration(50);
        widenAnimator.setInterpolator(new DecelerateInterpolator(1f));
        widenAnimator.setRepeatCount(1);
        widenAnimator.setRepeatMode(ValueAnimator.REVERSE);

        ValueAnimator heightenAnimator = ObjectAnimator.ofFloat(view, "height", height, height-30f);
        heightenAnimator.setDuration(50);
        heightenAnimator.setInterpolator(new DecelerateInterpolator(1f));
        heightenAnimator.setRepeatCount(1);
        heightenAnimator.setRepeatMode(ValueAnimator.REVERSE);

        ValueAnimator shadow = ObjectAnimator.ofFloat(shadowView, "width", shadowWidth-100f, shadowWidth);
        shadow.setDuration(350);
        shadow.setInterpolator(new AccelerateInterpolator(2f));
        shadow.setRepeatCount(1);
        shadow.setRepeatMode(ValueAnimator.REVERSE);

        final AnimatorSet set = new AnimatorSet();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isContinue)
//                    animateJump();
                    set.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.play(downAnimator)
                .with(shadow)
                .before(widenAnimator);
        set.playTogether(widenAnimator, heightenAnimator);
        set.play(upAnimator).after(widenAnimator);
        set.start();
    }

    public void stopLoadingAnimation(){
        this.isContinue = false;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        img.invalidate();
        shadow.invalidate();
    }

    public interface OnExceptionalClick{
        void onExceptionalClick();
    }

//    private OnExceptionalClick onExceptionalClick;

//    public void setOnExceptionalClick(OnExceptionalClick onExceptionalClick){
//        this.onExceptionalClick = onExceptionalClick;
//    }

    class ShadowWrapper {
        private View view;
        public ShadowWrapper(View view){
            this.view = view;
        }

        public void setWidth(float width){
            view.getLayoutParams().width = (int) width;
//            this,width = width;
            view.requestLayout();
        }

        public float getWidth(){
            return view.getLayoutParams().width;
        }

    }

    class ViewWrapper {
        private View view;
        public ViewWrapper(View target){
            this.view = target;
        }

        public float getWidth(){
            return view.getLayoutParams().width;
        }

        public void setWidth(float width){
            view.getLayoutParams().width = (int)width;
            view.requestLayout();
        }

        public float getHeight(){
            return view.getLayoutParams().height;
        }

        public void setHeight(float height){
            view.getLayoutParams().height = (int)height;
            view.requestLayout();
        }

        public void setPivotX(float pivotX){
            view.setPivotX(pivotX);
            view.requestLayout();
        }

        public float getPivotX(){
            return view.getPivotX();
        }

        public void setPivotY(float pivotY){
            view.setPivotY(pivotY);
            view.requestLayout();
        }

        public float getPivotY(){
            return view.getPivotY();
        }
    }

}
