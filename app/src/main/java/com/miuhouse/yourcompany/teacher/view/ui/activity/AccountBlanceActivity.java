package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.listener.OnListItemClick;
import com.miuhouse.yourcompany.teacher.model.TradingRecordBean;
import com.miuhouse.yourcompany.teacher.model.TradingRecordListBean;
import com.miuhouse.yourcompany.teacher.presenter.TradingRecordPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.ITradingRecordPresenter;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.ITradingRecordView;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.TradingRecordAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 账户余额
 * Created by kings on 7/20/2016.
 */
public class AccountBlanceActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnListItemClick, ITradingRecordView {

    private SwipeRefreshLayout refresh;
    private RecyclerView tradingRecord;
    private TextView tvBlance;
    private LinearLayout content;

    private TradingRecordAdapter adapter;
    private List<TradingRecordBean> list = new ArrayList<>();
    private ITradingRecordPresenter tradingRecordPresenter;
    private int page = 1;
    private double blance;

    //    private List
    @Override
    protected String setTitle() {
        return "账户余额";
    }

    @Override
    protected String setRight() {
        return "提现";
    }

    @Override
    protected void initViewAndEvents() {

        blance = getIntent().getDoubleExtra("blance", 0.00);
        tradingRecordPresenter = new TradingRecordPresenter(this);

        content = (LinearLayout) findViewById(R.id.content);
        tvBlance = (TextView) findViewById(R.id.tv_blance);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        tradingRecord = (RecyclerView) findViewById(R.id.recycler_trading_record);
        refresh.setColorSchemeResources(android.R.color.holo_orange_light);
        refresh.setOnRefreshListener(this);
        tradingRecord.setLayoutManager(new LinearLayoutManager(context));

        adapter = new TradingRecordAdapter(context, list, this);
        tradingRecord.setAdapter(adapter);

//        tvBlance.setstyle
        tvBlance.setText(NumberFormat.getCurrencyInstance(Locale.CHINA).format(blance));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account_balance;
    }

    @Override
    protected View getOverrideParentView() {
        return content;
    }

    @Override
    public void onRefresh() {
        tradingRecordPresenter.getTradingRecordList(null, page, Constants.PAGE_SIZE);
    }

    @Override
    public void onItemClick(Object data) {
        TradingRecordBean tradingRecordBean = (TradingRecordBean) data;

        //提现记录详情（为提现完成时返回）
        if (tradingRecordBean.getChangeType().equals("1")) {
            Intent intent = new Intent(this, MoneyArriveActivity.class);

            intent.putExtra("withdrawId", tradingRecordBean.getId());
            startActivity(intent);

        } else {
            //订单完成记录详情（为订单完成时返回）
            Intent intent = new Intent(this, WithdrawMoneyDetailsActivity.class);
            intent.putExtra("tradingRecordBean", tradingRecordBean);
            startActivity(intent);

        }

    }

    @Override
    public void getTradingRecord(TradingRecordListBean tradingRecordListBean) {
        L.i("TAG", "tradingRecordListBean=" + tradingRecordListBean.getWithdraw().size());
        list.addAll(tradingRecordListBean.getWithdraw());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRightClick() {
        Intent intent = new Intent(this, WithdrawMoneyActivity.class);
        intent.putExtra("blance", blance);
        startActivity(intent);
    }

    @Override
    public void showLoading(String msg) {
        super.showLoading(msg);
//        viewOverrideManager.showLoading(msg, true);
    }

    @Override
    public void request() {
        tradingRecordPresenter.getTradingRecordList(null, page, Constants.PAGE_SIZE);
    }
}
