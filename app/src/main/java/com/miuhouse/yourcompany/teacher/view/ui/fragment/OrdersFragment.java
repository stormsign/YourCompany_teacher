package com.miuhouse.yourcompany.teacher.view.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.interactor.OrderListInteractor;
import com.miuhouse.yourcompany.teacher.model.OrderItemEntity;
import com.miuhouse.yourcompany.teacher.presenter.OrderListPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IOrderListPresenter;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.OrderListAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IOrdersFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khb on 2016/7/6.
 */
public class OrdersFragment extends BaseFragment implements IOrdersFragment, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView rvOrderList;
    private RelativeLayout mSquare;
    private RelativeLayout mMyOrder;
    private ImageView ivSquare;
    private ImageView ivMyOrder;
    private TextView mTvSquare;
    private TextView mNyOrder;
    private SwipeRefreshLayout mRefresh;

    private int page = 1;

    private IOrderListPresenter iOrderListPresenter;
    private List<OrderItemEntity> list;

    @Override
    public int getFragmentResourceId() {
        return R.layout.fragment_orders;
    }

    @Override
    public void getViews(View view) {
        iOrderListPresenter = new OrderListPresenter(this);
        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        rvOrderList = (RecyclerView) view.findViewById(R.id.orderList);
        mSquare = (RelativeLayout) view.findViewById(R.id.square);
        mMyOrder = (RelativeLayout) view.findViewById(R.id.myOrder);
        ivSquare = (ImageView) view.findViewById(R.id.iconSquare);
        ivMyOrder = (ImageView) view.findViewById(R.id.iconMyOrder);
        mTvSquare = (TextView) view.findViewById(R.id.textSquare);
        mNyOrder = (TextView) view.findViewById(R.id.textMyOrder);
    }

    @Override
    public void setupView() {
        list = new ArrayList<>();
        mRefresh.setColorSchemeResources(android.R.color.holo_orange_light);
        mRefresh.setOnRefreshListener(this);
        rvOrderList.setLayoutManager(new LinearLayoutManager(context));
        OrderListAdapter adapter = new OrderListAdapter(context, list);
    }

    @Override
    public void refresh(OrderListInteractor.OrderListBean data) {

        OrderItemEntity entity = new OrderItemEntity();
        entity.setId("123456789");
        entity.setClassCount(2);
        entity.setDetail("。。。。。。。");
        entity.setDistance("4.3");
        entity.setHeader("http://p3.music.126.net/nUGiKZdgmElnsyx0ThbYrA==/2946691185724731.jpg?param=180y180");
        entity.setName("螚安");
        entity.setOrderTopic("二胡");
        entity.setOrderType(1);
        entity.setPrice(50);
        entity.setStatus(1);
        entity.setTime(System.currentTimeMillis());
        for (int i = 0; i<10; i++) {
            list.add(entity);
        }


    }

    @Override
    public void goToOrderDetail() {

    }

    @Override
    public void changeList() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void showLoading(String msg) {
//        super.showLoading(msg);
    }

    @Override
    public void hideLoading() {
        mRefresh.setRefreshing(false);
//        super.hideLoading();
    }


}
