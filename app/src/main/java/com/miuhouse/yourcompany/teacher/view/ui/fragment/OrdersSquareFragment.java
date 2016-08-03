package com.miuhouse.yourcompany.teacher.view.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.db.AccountDBTask;
import com.miuhouse.yourcompany.teacher.interactor.OrderListInteractor;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.presenter.OrderListPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IOrderListPresenter;
import com.miuhouse.yourcompany.teacher.view.ui.activity.OrderActivity;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.OrderListAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IOrdersListFragment;
import com.miuhouse.yourcompany.teacher.view.widget.ViewOverrideManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khb on 2016/7/6.
 */
public class OrdersSquareFragment extends BaseFragment implements IOrdersListFragment,
        SwipeRefreshLayout.OnRefreshListener, OrderListAdapter.OnOrderItemClickListener {

    private RecyclerView rvOrderList;
    private SwipeRefreshLayout mRefresh;

    private int page = 1;

    private IOrderListPresenter orderListPresenter;
    private List<OrderEntity> list;

    private OrderListAdapter adapter;
    private RelativeLayout content;
    private OrdersFragment parentFragment;

    @Override
    public int getFragmentResourceId() {
        return R.layout.fragment_orderssquare;
    }

    @Override
    public View getOverrideParentView() {
        return content;
    }

    @Override
    public void getViews(View view) {
        orderListPresenter = new OrderListPresenter(this);
        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        rvOrderList = (RecyclerView) view.findViewById(R.id.orderList);
        content = (RelativeLayout) view.findViewById(R.id.squareContent);
    }

    @Override
    public void setupView() {
        list = new ArrayList<>();
        parentFragment = (OrdersFragment) getParentFragment();
        mRefresh.setColorSchemeResources(android.R.color.holo_orange_light);
        mRefresh.setOnRefreshListener(this);
        rvOrderList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new OrderListAdapter(context, list);
        adapter.setOnOrderItemClickListener(this);
        rvOrderList.setAdapter(adapter);
        rvOrderList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;
            private int firstVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()
                        && firstVisibleItem != 0) {
                    page += 1;
                    orderListPresenter.getAllList(page);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        });

//        mSquare.setOnClickListener(this);
//        mMyOrder.setOnClickListener(this);

//        isAllList = true;
//        changeListToggle(isAllList);
    }

    private int count;

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        orderListPresenter.getAllList(page);
    }

    @Override
    public void refresh(OrderListInteractor.OrderListBean data) {
//        OrderEntity entity = new OrderEntity();
//        mRefresh.setRefreshing(false);
        if (page == 1) {
            list.clear();
        }
        list.addAll(data.getOrderList());
        adapter.notifyDataSetChanged();
//            if (data.getCount() > 0) {
//                orderCount.setText(data.getCount() + "");
//            } else {
//                orderCount.setText("0");
//            }
        count = (int) (data.getCount());

//            parentFragment.setSquareTop((int) (data.getCount()));
        parentFragment.setSquareCount(count);
    }

    @Override
    public void remove() {

    }

    @Override
    public void onOrderClick(OrderEntity entity) {
        startActivity(new Intent(context, OrderActivity.class)
                .putExtra("order", entity));
    }

    @Override
    public void onGetOrderClick(OrderEntity entity) {
        orderListPresenter.grabOrder(AccountDBTask.getAccount().getId(), entity.getId());
    }

    @Override
    public void onRefresh() {
//        if (mRefresh.isRefreshing()) {
//            mRefresh.setRefreshing(false);
//        }
        page = 1;
        orderListPresenter.getAllList(page);
    }

    @Override
    public void showSecondLoading() {
        viewOverrideManager.showLoading();
    }

    @Override
    public void changeListToggle() {
        OrdersFragment parentFragment = (OrdersFragment)getParentFragment();
        parentFragment.changeListToggle(false);
    }

    @Override
    public void showLoading(String msg) {
        if (!mRefresh.isRefreshing()) {
            mRefresh.post(new Runnable() {
                @Override
                public void run() {
                    mRefresh.setRefreshing(true);
                }
            });
        }
//        super.showLoading(msg);
    }

    @Override
    public void hideLoading() {
        mRefresh.setRefreshing(false);
        parentFragment.setSquareCount(count);
        super.hideLoading();
    }

    @Override
    public void showError(int type) {
//        top.setVisibility(View.INVISIBLE);
//        super.showError(msg);
        if (type == ViewOverrideManager.NO_STUDENT) {
            viewOverrideManager.showLoading(type,
                    new ViewOverrideManager.OnExceptionalClick() {
                        @Override
                        public void onExceptionalClick() {
                            page = 1;
                            orderListPresenter.getAllList(page);
//                            hideError();
                        }
                    });
        }else if (type == ViewOverrideManager.NO_NETWORK){
            viewOverrideManager.showLoading(type, new ViewOverrideManager.OnExceptionalClick() {
                @Override
                public void onExceptionalClick() {
                    page = 1;
                    orderListPresenter.getAllList(page);
                }
            });
        }else if (type == -1){
            Toast.makeText(context, "请设置接单地址", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void hideError() {
        super.hideError();
    }

    @Override
    public void onTokenExpired() {
        super.onTokenExpired();
    }
}