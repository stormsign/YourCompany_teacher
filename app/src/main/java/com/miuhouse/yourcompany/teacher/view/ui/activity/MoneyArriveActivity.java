package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.model.TradingRecordBean;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IMoneyArriveActivity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

import java.text.NumberFormat;
import java.util.Locale;

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
        TradingRecordBean tradingRecordBean = (TradingRecordBean) getIntent().getSerializableExtra("tradingRecordBean");
        L.i("TAG", "tradingRecordBean=" + tradingRecordBean.getAmount());
        purse = (RelativeLayout) findViewById(R.id.purse);
        money = (TextView) findViewById(R.id.money);
        ivMyLord = (ImageView) findViewById(R.id.mylord);
        tvMyLord = (TextView) findViewById(R.id.mylordName);
        classType = (TextView) findViewById(R.id.classType);
        time = (TextView) findViewById(R.id.time);
        if (tradingRecordBean.getChangeType().equals("1")) {

            money.setText("+" + NumberFormat.getCurrencyInstance(Locale.CHINA).format((tradingRecordBean.getAmount())));
        } else {
            money.setText("-" + NumberFormat.getCurrencyInstance(Locale.CHINA).format((tradingRecordBean.getAmount())));
        }

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
