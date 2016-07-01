package com.miuhouse.yourcompany.teacher.view.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.application.ActivityManager;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.widget.MyTitlebar;
import com.miuhouse.yourcompany.teacher.view.widget.StatusCompat;
import com.miuhouse.yourcompany.teacher.view.widget.ViewOverrideManager;

/**
 * Created by khb on 2016/6/30.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView, MyTitlebar.OnMyTitlebarClickListener {
    private Context context;
    private Activity activity;
    private MyTitlebar myTitlebar;
    private ViewOverrideManager viewOverrideManager;
    private View baseView;
    private View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        activity = this;
//        新建Activity时入栈
        ActivityManager.getInstance().pushActivity(activity);
        setContentView(getContentLayoutId());
//        initTitle();
        initViewAndEvents();
//        设置沉浸式消息栏
        StatusCompat.compat(this, getResources().getColor(android.R.color.holo_orange_dark));
//        设置异常页面管理
        viewOverrideManager = new ViewOverrideManager(getOverrideParentView());
    }

    @Override
    public void setContentView(int layoutResID) {
        baseView = View.inflate(context, R.layout.activity_base, null);
        contentView = View.inflate(context, layoutResID, null);
        initTitle();
        FrameLayout flTitle = (FrameLayout) baseView.findViewById(R.id.titlePosition);
        if (null != myTitlebar) {
            flTitle.addView(myTitlebar);
        }
        FrameLayout flContent = (FrameLayout) baseView.findViewById(R.id.contentPosition);
        flContent.addView(contentView);
        setContentView(baseView);
    }

    /**
     *  添加标题栏，子类可覆盖，若子类覆写时没有做实现，则不显示标题栏
     */
    protected void initTitle() {

        myTitlebar = new MyTitlebar(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                Util.dip2px(this, getResources().getDimension(R.dimen.height_toolbar))
//                        +
                        Util.getStatusBarHeight(this)*2);
        myTitlebar.setLayoutParams(params);
        myTitlebar.setOnMyTitlebarClickListener(this);
        myTitlebar.setTitle(setTitle());
        myTitlebar.setRightButtonText(setRight());
//        ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
//
//        contentView.addView(myTitlebar);

    }

    protected abstract String setTitle();
    protected abstract String setRight();

    /**
     * 对页面操作
     */
    protected abstract void initViewAndEvents();

    /**
     * 获取要加载的页面
     * @return
     */
    protected abstract int getContentLayoutId();

    /**
     * 获取一个ViewGroup，这个ViewGroup内将显示异常页面
     * @return
     */
    protected abstract View getOverrideParentView();

    /**
     * 设置加载界面，子类可覆盖
     * @param msg
     */
    @Override
    public void showLoading(String msg) {

    }

    /**
     * 设置错误页面，子类可覆盖
     * @param msg
     */
    @Override
    public void showError(String msg) {
        viewOverrideManager.showLoading("dd");
    }

    /**
     * 隐藏加载界面，子类可覆盖
     */
    @Override
    public void hideLoading() {
        viewOverrideManager.restoreView();
    }

    /**
     * 隐藏错误页面，子类可覆盖
     */
    @Override
    public void hideError() {

    }

    /**
     * 网络错误页面，子类可覆盖
     */
    @Override
    public void netError() {

    }

    /**
     * 设置返回按钮，子类可覆盖
     */
    @Override
    public void onBackClick() {
        finish();
    }

    /**
     * 设置右键动作，子类可覆盖
     */
    @Override
    public void onRightClick() {

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

//    退出Activity时退栈
    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().popActivity(activity);
        super.onDestroy();
    }
}
