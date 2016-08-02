package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.db.AccountDBTask;
import com.miuhouse.yourcompany.teacher.listener.OnListItemClick;
import com.miuhouse.yourcompany.teacher.model.TradingRecordBean;
import com.miuhouse.yourcompany.teacher.model.TradingRecordListBean;
import com.miuhouse.yourcompany.teacher.presenter.PursePresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IPursePresenter;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IPurseMsgActivity;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.PurseMsgAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;
import com.miuhouse.yourcompany.teacher.view.widget.ViewOverrideManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khb on 2016/7/26.
 */
public class PurseMsgActivity extends BaseActivity implements IPurseMsgActivity, SwipeRefreshLayout.OnRefreshListener, OnListItemClick {

    private LinearLayout content;
    private SwipeRefreshLayout refresh;
    private RecyclerView list;
    private List<TradingRecordBean> msgList;
    private PurseMsgAdapter adapter;

    private IPursePresenter presenter;
    private String teacherId;
    private int page = 1;

    @Override
    protected String setTitle() {
        return "账户消息";
    }

    @Override
    protected String setRight() {
        return null;
    }

    @Override
    protected void initViewAndEvents() {
        msgList = new ArrayList<>();
        presenter = new PursePresenter(this);
        content = (LinearLayout) findViewById(R.id.content);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        list = (RecyclerView) findViewById(R.id.purseMsgList);
        refresh.setColorSchemeResources(R.color.themeColor);
        refresh.setOnRefreshListener(this);
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PurseMsgAdapter(this, msgList, this);
        list.setAdapter(adapter);
        teacherId = AccountDBTask.getAccount().getId();
//        teacherId = "4028b88155c4dd070155c4dd8a340000";
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;
            private int firstVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()
                        && firstVisibleItem != 0) {
                    page += 1;
                    presenter.getPurseMsgs(teacherId, page);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        });

        page = 1;
        presenter.getPurseMsgs(teacherId, page);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_pursemsg;
    }

    @Override
    protected View getOverrideParentView() {
        return refresh;
    }

    @Override
    public void onRefresh() {
        page = 1;
        presenter.getPurseMsgs(teacherId, page);
    }

    @Override
    public void refresh(TradingRecordListBean data) {
        List<TradingRecordBean> list = data.getWithdraw();
        msgList.clear();
        msgList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Object data) {
        TradingRecordBean record = (TradingRecordBean) data;
        startActivity(new Intent(this, MoneyArriveActivity.class)
                .putExtra("withdrawId", record.getId()));
    }

    @Override
    public void showLoading(String msg) {
//        super.showLoading(msg);
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
            }
        });

    }

    @Override
    public void hideLoading() {
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
        }
    }

    @Override
    public void showError(int type) {
//        super.showError(type);
        if (type == ViewOverrideManager.NO_MSG) {
            viewOverrideManager.showLoading(type, null);
        }else if (type == ViewOverrideManager.NO_NETWORK){
            viewOverrideManager.showLoading(type, new ViewOverrideManager.OnExceptionalClick() {
                @Override
                public void onExceptionalClick() {
                    page = 1;
                    presenter.getPurseMsgs(teacherId, page);
                }
            });
        }
    }
}
