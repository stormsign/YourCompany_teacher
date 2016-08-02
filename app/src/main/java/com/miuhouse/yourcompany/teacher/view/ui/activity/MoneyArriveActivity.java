package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.model.TradingDetailsBean;
import com.miuhouse.yourcompany.teacher.presenter.TradingDetailsPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.ITradingDetailsPresenter;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IMoneyArriveActivity;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.ITradingDetailsView;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by khb on 2016/7/18.
 */
public class MoneyArriveActivity extends BaseActivity implements IMoneyArriveActivity, ITradingDetailsView {

    private RelativeLayout purse;
    private TextView money;
    private ImageView ivMyLord;
    private TextView tvMyLord;
    private TextView classType;
    private TextView time;
    private String withdrawId;

    private ITradingDetailsPresenter tradingDetailsPresenter;
    private LinearLayout moneyArrive;
    private RelativeLayout withdraw;

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
        withdrawId = getIntent().getStringExtra("withdrawId");
        purse = (RelativeLayout) findViewById(R.id.purse);
        money = (TextView) findViewById(R.id.money);
        ivMyLord = (ImageView) findViewById(R.id.mylord);
        tvMyLord = (TextView) findViewById(R.id.mylordName);
        classType = (TextView) findViewById(R.id.classType);
        time = (TextView) findViewById(R.id.time);
        moneyArrive = (LinearLayout) findViewById(R.id.moneyArrive);
        withdraw = (RelativeLayout) findViewById(R.id.withdraw);

        tradingDetailsPresenter = new TradingDetailsPresenter(this);
        tradingDetailsPresenter.getTradingDetails(null, withdrawId);

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_moneyarrive;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }

    @Override
    public void getTradingDetails(TradingDetailsBean record) {
        purse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        money.setText("+" + NumberFormat.getCurrencyInstance(Locale.CHINA).format((record.getRecord().getAmount())));
        tvMyLord.setText(record.getRecord().getpName());
        Glide.with(this).load(record.getRecord().getHeadUrl()).centerCrop().override(Util.dip2px(this, 32), Util.dip2px(this, 32)).into(ivMyLord);
//        classType.
        time.setText(Util.formatTime(record.getRecord().getDate()));
        /*if (record.getChangeType().equals("1")){    //到账通知
            mholder.withdraw.setVisibility(View.GONE);
//            mholder.moneyArrive.setVisibility(View.VISIBLE);
            mholder.topic.setText("到账通知");
//            mholder.
        }else if (record.getChangeType().equals("2")){     //提现通知
            mholder.withdraw.setVisibility(View.VISIBLE);
//            mholder.moneyArrive.setVisibility(View.GONE);
            mholder.topic.setText("提现成功");
            mholder.account.setText(record.getAccount());
        }*/
    }
}
