package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.interactor.SysMsgInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnListItemClick;
import com.miuhouse.yourcompany.teacher.model.SysMsgEntity;
import com.miuhouse.yourcompany.teacher.presenter.SysMsgPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.ISysMsgPresenter;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.ISysMsgActivity;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.SysMsgAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khb on 2016/7/14.
 */
public class SysMsgActivity extends BaseActivity implements ISysMsgActivity, OnListItemClick, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refresh;
    private RecyclerView sysMsgList;

    private int page = 1;

    private List<SysMsgEntity> list;
    private SysMsgAdapter adapter;
    private LinearLayout content;
    private ISysMsgPresenter presenter;

    @Override
    protected String setTitle() {
        return "系统通知";
    }

    @Override
    protected String setRight() {
        return null;
    }

    @Override
    protected void initViewAndEvents() {
        list = new ArrayList<>();
        presenter = new SysMsgPresenter(this);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        sysMsgList = (RecyclerView) findViewById(R.id.sysMsgList);
        content = (LinearLayout) findViewById(R.id.content);
        refresh.setColorSchemeResources(R.color.themeColor);
        refresh.setOnRefreshListener(this);
        adapter = new SysMsgAdapter(context, list, this);
        sysMsgList.setAdapter(adapter);
        sysMsgList.setLayoutManager(new LinearLayoutManager(this));
        sysMsgList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;
            private int firstVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()
                        && firstVisibleItem != 0) {
                    page += 1;
                    presenter.getMsgs(page);
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
        presenter.getMsgs(page);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_sysmsg;
    }

    @Override
    protected View getOverrideParentView() {
        return content;
    }

    @Override
    public void refresh(SysMsgInteractor.SysMsgBean bean) {
        if (page == 1) {
            list.clear();
        }
        if (null != bean
                && null !=  bean.getNoticeList()
                && bean.getNoticeList().size()>0) {
            list.addAll(bean.getNoticeList());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Object data) {

    }

    @Override
    public void showLoading(String msg) {
//        super.showLoading(msg);
        if (!refresh.isRefreshing()){
            refresh.post(new Runnable() {
                @Override
                public void run() {
                    refresh.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public void hideLoading() {
//        super.hideLoading();
        if (refresh.isRefreshing()){
            refresh.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
//        if (refresh.isRefreshing()) {
//            refresh.setRefreshing(false);
//        }
        page = 1;
        presenter.getMsgs(page);
    }
}
