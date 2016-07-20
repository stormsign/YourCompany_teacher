package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.view.View;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IOrderDetailActivity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

/**
 * Created by khb on 2016/7/20.
 */
public class OrderDetailActivity extends BaseActivity implements IOrderDetailActivity {
    @Override
    protected String setTitle() {
        return "订单详情";
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
        return R.layout.activity_orderdetail;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }

    @Override
    public void showCountdown() {

    }
}
