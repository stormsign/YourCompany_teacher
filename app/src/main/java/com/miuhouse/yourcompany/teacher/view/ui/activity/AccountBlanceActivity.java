package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

import java.util.ArrayList;
import java.util.List;

/**
 * 账户余额
 * Created by kings on 7/20/2016.
 */
public class AccountBlanceActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnListItemClick, ITradingRecordView {
    private SwipeRefreshLayout refresh;
    private RecyclerView tradingRecord;
    private TradingRecordAdapter adapter;
    private List<TradingRecordBean> list = new ArrayList<>();
    private ITradingRecordPresenter tradingRecordPresenter;
    private int page = 1;

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
        tradingRecordPresenter = new TradingRecordPresenter(this);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        tradingRecord = (RecyclerView) findViewById(R.id.recycler_trading_record);
        refresh.setColorSchemeResources(android.R.color.holo_orange_light);
        refresh.setOnRefreshListener(this);
        tradingRecord.setLayoutManager(new LinearLayoutManager(context));
        adapter = new TradingRecordAdapter(context, list, this);

        tradingRecord.setAdapter(adapter);
        tradingRecordPresenter.getTradingRecordList(null, page, Constants.PAGE_SIZE);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account_balance;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemClick(Object data) {
        TradingRecordBean tradingRecordBean = (TradingRecordBean)data;
        Intent intent = new Intent(this, MoneyArriveActivity.class);
        intent.putExtra("tradingRecordBean",tradingRecordBean);
        startActivity(intent);
    }

    @Override
    public void getTradingRecord(TradingRecordListBean tradingRecordListBean) {
        L.i("TAG", "tradingRecordListBean=" + tradingRecordListBean.getWithdraw().size());
        list.addAll(tradingRecordListBean.getWithdraw());
        adapter.notifyDataSetChanged();
    }


}
