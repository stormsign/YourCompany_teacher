package com.miuhouse.yourcompany.teacher.view.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
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
 * @author Created by khb on 2016/6/30.
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

        StatusCompat.compat(this, getResources().getColor(R.color.themeColor));
//        由于4.4版本的状态栏是一个自定义view，因此在添加内容View，但又没有加标题栏时，内容View会覆盖状态栏
//        因此这里也要单独处理
//        没有加标题栏时，内容View可以设置fitSystemWindows
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);
            baseView.setFitsSystemWindows(true);
            if (rootView.findViewById(R.id.titlebar) != null)
                baseView.setFitsSystemWindows(false);
        }
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
     * 添加标题栏，子类可覆盖，若子类覆写时没有做实现，则不显示标题栏
     */
    protected void initTitle() {

        myTitlebar = new MyTitlebar(this);
        int height = 0;
//        在调用StatusCompat后，4.4版本的状态栏不像4.4以下和5.0及其以上版本一样是固定在顶端，
//        而是变成一个view添加上去，因此要考虑状态栏的高度，
//        所以4.4要单独处理
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            height = Util.getStatusBarHeight(this) * 3;
        } else {
            height = Util.getStatusBarHeight(this) * 2;
        }

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                height);
        myTitlebar.setLayoutParams(params);
        myTitlebar.setOnMyTitlebarClickListener(this);
        myTitlebar.setTitle(setTitle());
        myTitlebar.setRightButtonText(setRight());

    }

    /**
     * @param rightText
     * @author pengjun on 07-05
     * 给Right TextView 随时设置文本
     *
     */
    public void setRightButtonText(String rightText) {
        if (myTitlebar != null) {
            myTitlebar.setRightButtonText(rightText);
        }
    }

    protected abstract String setTitle();

    protected abstract String setRight();

    /**
     * 对页面操作
     */
    protected abstract void initViewAndEvents();

    /**
     * 获取要加载的页面
     *
     * @return
     */
    protected abstract int getContentLayoutId();

    /**
     * 获取一个ViewGroup，这个ViewGroup内将显示异常页面
     *
     * @return
     */
    protected abstract View getOverrideParentView();

    /**
     * 设置加载界面，子类可覆盖
     *
     * @param msg
     */
    @Override
    public void showLoading(String msg) {

    }

    /**
     * 设置错误页面，子类可覆盖
     *
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
