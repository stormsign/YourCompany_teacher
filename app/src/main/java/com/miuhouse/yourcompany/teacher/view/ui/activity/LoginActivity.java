package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.view.View;

import com.miuhouse.yourcompany.teacher.view.ui.activity.view.ILoginView;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseView;

/**
 * Created by kings on 7/1/2016.
 */
public class LoginActivity extends BaseActivity implements ILoginView{
    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected String setRight() {
        return null;
    }

    @Override
    protected void initViewAndEvents() {

    }

    @Override
    protected int getContentLayoutId() {
        return 0;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }
}
