package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.view.View;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

/**
 * Created by kings on 7/8/2016.
 */
public class ChangeUserName extends BaseActivity  {
    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected String setRight() {
        return "保存";
    }

    @Override
    protected void initViewAndEvents() {

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_change_username;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }
}
