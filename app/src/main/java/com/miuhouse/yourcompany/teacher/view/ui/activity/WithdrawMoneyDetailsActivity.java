package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.model.TradingRecordBean;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by kings on 7/21/2016.
 */
public class WithdrawMoneyDetailsActivity extends BaseActivity {

    private TradingRecordBean tradingRecordBean;

    private TextView money;
    private TextView tvMyLord;
    private TextView time;

    @Override
    protected String setTitle() {
        return "记录详情";
    }

    @Override
    protected String setRight() {
        return null;
    }

    @Override
    protected void initViewAndEvents() {
        tradingRecordBean = (TradingRecordBean) getIntent().getSerializableExtra("tradingRecordBean");
        money = (TextView) findViewById(R.id.money);
        tvMyLord = (TextView) findViewById(R.id.mylordName);
        time = (TextView) findViewById(R.id.time);
        money.setText("-" + NumberFormat.getCurrencyInstance(Locale.CHINA).format((tradingRecordBean.getAmount())));
        time.setText(Util.formatTime(tradingRecordBean.getFeedbackTime()));
        tvMyLord.setText(tradingRecordBean.getAccount());
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_withdraw_money_detail;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }
}
