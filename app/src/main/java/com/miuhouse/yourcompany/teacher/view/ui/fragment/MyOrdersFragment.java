package com.miuhouse.yourcompany.teacher.view.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.interactor.OrderListInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnListItemClick;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.presenter.OrderListPresenter;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.MyOrderAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IOrdersListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khb on 2016/7/13.
 */
public class MyOrdersFragment extends BaseFragment implements IOrdersListFragment,
        SwipeRefreshLayout.OnRefreshListener, OnListItemClick {

    private OrderListPresenter orderListPresenter;
    private LinearLayout content;
    private SwipeRefreshLayout refresh;
    private RecyclerView myOrderList;

    private int page = 1;
    private List<OrderEntity> list;
    private MyOrderAdapter adapter;
    private OrdersFragment parentFragment;

    @Override
    public int getFragmentResourceId() {
        return R.layout.fragment_myorders;
    }

    @Override
    public void getViews(View view) {
        list = new ArrayList<>();
        orderListPresenter = new OrderListPresenter(this);
        content = (LinearLayout) view.findViewById(R.id.myOrdersContent);
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        myOrderList = (RecyclerView) view.findViewById(R.id.myOrderList);

    }

    @Override
    public void setupView() {
        refresh.setColorSchemeResources(android.R.color.holo_orange_light);
        refresh.setOnRefreshListener(this);
        myOrderList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MyOrderAdapter(context, list, this);
        myOrderList.setAdapter(adapter);
        myOrderList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;
            private int firstVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()
                        && firstVisibleItem != 0) {
                    page += 1;
//                    if (isAllList){
//                        orderListPresenter.getAllList(page);
//                    }else {
//                        orderListPresenter.getMyList(page);
//                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        });
        orderListPresenter.getMyList(page);
    }

    @Override
    public View getOverrideParentView() {
        return content;
    }

    private int count;

    @Override
    public void refresh(OrderListInteractor.OrderListBean data) {
        if (null != data
                && null != data.getOrderList()
                && data.getOrderList().size() > 0) {
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
            count = (int)data.getCount();
            parentFragment = (OrdersFragment) getParentFragment();
//            parentFragment.setSquareTop((int) (data.getCount()));
            parentFragment.setMyOrderCount(count);
        }
    }

    @Override
    public void remove() {

    }

    @Override
    public void onRefresh() {
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
        }
        page = 1;
        orderListPresenter.getMyList(page);
    }

    @Override
    public void onItemClick(Object data) {
        L.i("ok");
    }

    @Override
    public void showLoading(String msg) {
        if (!refresh.isRefreshing()) {
            refresh.post(new Runnable() {
                @Override
                public void run() {
                    refresh.setRefreshing(true);
                }
            });
        }
//        super.showLoading(msg);
    }

    @Override
    public void hideLoading() {
        refresh.setRefreshing(false);
        parentFragment.setMyOrdersTop(count);
//        super.hideLoading();
    }

    @Override
    public void showError(String msg) {
//        top.setVisibility(View.INVISIBLE);
        super.showError(msg);
    }

    @Override
    public void hideError() {
//        top.setVisibility(View.VISIBLE);
        super.hideError();
    }
}
