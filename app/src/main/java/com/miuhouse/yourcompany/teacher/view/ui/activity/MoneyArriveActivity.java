package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IMoneyArriveActivity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

/**
 * Created by khb on 2016/7/18.
 */
public class MoneyArriveActivity extends BaseActivity implements IMoneyArriveActivity {

    private RelativeLayout purse;
    private TextView money;
    private ImageView ivMyLord;
    private TextView tvMyLord;
    private TextView classType;
    private TextView time;

    @Override
    protected String setTitle() {
        return "到账详情";
    }

    @Override
    protected String setRight() {
        return null;
    }

    @Override
    protected void initViewAndEvents() {
        getIntent();
        purse = (RelativeLayout) findViewById(R.id.purse);
        money = (TextView) findViewById(R.id.money);
        ivMyLord = (ImageView) findViewById(R.id.mylord);
        tvMyLord = (TextView) findViewById(R.id.mylordName);
        classType = (TextView) findViewById(R.id.classType);
        time = (TextView) findViewById(R.id.time);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_moneyarrive;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }
}
