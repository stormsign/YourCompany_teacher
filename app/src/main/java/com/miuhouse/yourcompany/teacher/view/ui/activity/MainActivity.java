package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;


public class MainActivity extends BaseActivity {

    private RelativeLayout content;

    @Override
    protected void onSaveInstanceState(Bundle outState) {}

    @Override
    protected void initTitle() {}

    @Override
    protected String setTitle() {
        return "主页";
    }

    @Override
    protected String setRight() {
        return "按钮";
    }

    @Override
    protected void initViewAndEvents() {
        content = (RelativeLayout) findViewById(R.id.content);
    }



    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected View getOverrideParentView() {
        return content;
    }

    @Override
    public void onBackClick() {
        L.i("back or finish!!!");
        showError("dd");
    }

    @Override
    public void onRightClick() {
        L.i("right!!!");
        hideLoading();
    }

}
