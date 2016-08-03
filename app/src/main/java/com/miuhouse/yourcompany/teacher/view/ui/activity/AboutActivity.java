package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

/**
 * Created by kings on 8/2/2016.
 */
public class AboutActivity extends BaseActivity {

    private TextView tvVersionName;

    @Override
    protected String setTitle() {
        return "关于陪伴学习";
    }

    @Override
    protected String setRight() {
        return null;
    }

    @Override
    protected void initViewAndEvents() {
        tvVersionName = (TextView) findViewById(R.id.tv_version_name);
        initData();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }

    private void initData() {
        tvVersionName.setText("版本 V" + Util.getVersionName());
    }
}
