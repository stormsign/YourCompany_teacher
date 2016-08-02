package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.content.Intent;
import android.view.View;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

/**
 * Created by kings on 7/26/2016.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private String phone;

    @Override
    protected String setTitle() {
        return "设置";
    }

    @Override
    protected String setRight() {
        return null;
    }

    @Override
    protected void initViewAndEvents() {

        phone = getIntent().getStringExtra("phone");

        findViewById(R.id.relative_replace_phone).setOnClickListener(this);
        findViewById(R.id.relative_about).setOnClickListener(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_replace_phone:
                Intent intent = new Intent(context, ReplacePhoneActivity.class);
                intent.putExtra("phone", phone);
                startActivity(intent);
                break;
            case R.id.relative_about:
                break;
        }
    }
}
