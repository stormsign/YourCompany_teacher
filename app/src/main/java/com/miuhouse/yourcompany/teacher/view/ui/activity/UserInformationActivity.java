package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.view.View;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

/**
 * Created by kings on 7/8/2016.
 */
public class UserInformationActivity extends BaseActivity {
    @Override
    protected String setTitle() {
        return "我的资料";
    }

    @Override
    protected String setRight() {
        return "提交";
    }

    @Override
    protected void initViewAndEvents() {

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_information;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }
}
