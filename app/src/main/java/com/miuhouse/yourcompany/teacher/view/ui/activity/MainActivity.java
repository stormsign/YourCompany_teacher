package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;
import com.miuhouse.yourcompany.teacher.view.widget.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    private RelativeLayout content;
    private ViewPagerIndicator mPages;
    private List<Integer> imgResList = null;
    private List<String> titleList = null;

    @Override
    protected void onSaveInstanceState(Bundle outState) {}

    @Override
    protected void initTitle() {

    }

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
        imgResList = new ArrayList<>();
        titleList = new ArrayList<>();
        imgResList.add(android.R.drawable.ic_dialog_email);
        imgResList.add(android.R.drawable.ic_dialog_email);
        imgResList.add(android.R.drawable.ic_dialog_email);
//        titleList.add("订单");
//        titleList.add("看看");
//        titleList.add("版本");
        mPages = (ViewPagerIndicator) findViewById(R.id.pages);
        ViewPager pager = new ViewPager(this);
        mPages.setViewPager(pager, 0);
        mPages.setTabItemImgs(imgResList);
//        mPages.setTabItemTitles(titleList);
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
//        hideLoading();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }

}
