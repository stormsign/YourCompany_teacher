package com.miuhouse.yourcompany.teacher.view.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.application.ActivityManager;
import com.miuhouse.yourcompany.teacher.db.AccountDBTask;
import com.miuhouse.yourcompany.teacher.interactor.OrderManageInteractor;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.presenter.OrderManagePresenter;
import com.miuhouse.yourcompany.teacher.view.ui.activity.MainActivity;
import com.miuhouse.yourcompany.teacher.view.ui.activity.OrderDetailActivity;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.OrderAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IOrderManageFragment;
import com.miuhouse.yourcompany.teacher.view.widget.ViewOverrideManager;

import java.util.ArrayList;
import java.util.List;

/** 订单管理
 * 全部订单
 * Created by khb on 2016/7/18.
 */
public class FragmentA extends BaseFragment implements IOrderManageFragment, SwipeRefreshLayout.OnRefreshListener, OrderAdapter.OnOrderClick {

    private RecyclerView alist;
    private SwipeRefreshLayout refresh;

    private List<OrderEntity> list;
    private OrderAdapter adapter;
    private OrderManagePresenter presenter;

    private int page = 1;
    private String teacherId;

    @Override
    public int getFragmentResourceId() {
        return R.layout.fragment_a;
    }

    @Override
    public void getViews(View view) {
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.aRefresh);
        alist = (RecyclerView) view.findViewById(R.id.aList);
    }

    @Override
    public void setupView() {
        list = new ArrayList<>();
        teacherId = AccountDBTask.getAccount().getId();
//        teacherId = "4028b88155c4dd070155c4dd8a340000";

        presenter = new OrderManagePresenter(this);
        refresh.setOnRefreshListener(this);
        refresh.setColorSchemeResources(R.color.themeColor);
        alist.setLayoutManager(new LinearLayoutManager(context));
        adapter = new OrderAdapter(context, list, 1);
        adapter.setOnOrderClick(this);
        alist.setAdapter(adapter);
//        presenter.getAOrders(teacherId, page);
    }

    @Override
    public View getOverrideParentView() {
        return refresh;
    }

    @Override
    public void refresh(OrderManageInteractor.OrderListBean bean) {
        if (page == 1){
            list.clear();
        }
        list.addAll(bean.getOrderList());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void goToDetail(OrderEntity order, int reqCode) {
        startActivity(new Intent(context, OrderDetailActivity.class)
                .putExtra("orderId", order.getId()));
    }

    @Override
    public void onRefresh() {
        page = 1;
        presenter.getAOrders(teacherId, page);
    }

    @Override
    public void onOrderClick(OrderEntity order) {

    }

    @Override
    public void onButtonClick(OrderEntity order) {

    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        presenter.getAOrders(teacherId, page);
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
    public void showError(int type) {
//        super.showError(msg);
        if (type == ViewOverrideManager.NO_ORDER){
            viewOverrideManager.showLoading(type, new ViewOverrideManager.OnExceptionalClick() {
                @Override
                public void onExceptionalClick() {
                    ActivityManager.getInstance().finishAll();
                    startActivity(new Intent(context, MainActivity.class));
                }
            });
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (refresh.isRefreshing()){
            refresh.setRefreshing(false);
        }
    }

    @Override
    public void hideError() {
        super.hideError();
    }

//    @Override
//    public void beforeBeginClass() {
//
//    }
//
//    @Override
//    public void afterBeginClass() {
//
//    }
}
