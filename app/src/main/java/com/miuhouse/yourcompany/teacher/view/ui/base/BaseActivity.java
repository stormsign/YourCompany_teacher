package com.miuhouse.yourcompany.teacher.view.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.widget.MyTitlebar;
import com.miuhouse.yourcompany.teacher.view.widget.StatusCompat;

/**
 * Created by khb on 2016/6/30.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    private Context context;
    private Activity activity;
    private MyTitlebar myTitlebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayoutId());
        context = getApplicationContext();
        activity = this;
        initTitle();

        initViewAndEvents();
    }

    protected void initTitle() {
        StatusCompat.compat(this, getResources().getColor(android.R.color.holo_orange_dark));
        myTitlebar = new MyTitlebar(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                Util.dip2px(this, 48)+Util.getStatusBarHeight(this));
        myTitlebar.setLayoutParams(params);
        ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
        contentView.addView(myTitlebar);
    }

    protected void setTitle(String title){
        myTitlebar.setTitle(title);
    }
    protected void setRight(String text){
        myTitlebar.setRightButtonText(text);
    }

    protected abstract void initViewAndEvents();

    protected abstract int getContentLayoutId();

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void hideError() {

    }

    @Override
    public void netError() {

    }
}
