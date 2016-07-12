package com.miuhouse.yourcompany.teacher.view.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.interactor.OrderListInteractor;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.presenter.OrderListPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IOrderListPresenter;
import com.miuhouse.yourcompany.teacher.utils.Values;
import com.miuhouse.yourcompany.teacher.view.ui.activity.OrderActivity;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.OrderListAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IOrdersFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khb on 2016/7/6.
 */
public class OrdersFragment extends BaseFragment implements IOrdersFragment, SwipeRefreshLayout.OnRefreshListener, OrderListAdapter.OnOrderClickListener, View.OnClickListener {

    private RecyclerView rvOrderList;
    private RelativeLayout mSquare;
    private RelativeLayout mMyOrder;
    private ImageView ivSquare;
    private ImageView ivMyOrder;
    private TextView mTvSquare;
    private TextView mTvMyOrder;
    private SwipeRefreshLayout mRefresh;

    private int page = 1;

    private IOrderListPresenter orderListPresenter;
    private List<OrderEntity> list;

    private boolean isAllList = true;
    private OrderListAdapter adapter;
    private RelativeLayout content;
    private LinearLayout top;
    private TextView orderCount;

    @Override
    public int getFragmentResourceId() {
        return R.layout.fragment_orders;
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
        mSquare = (RelativeLayout) view.findViewById(R.id.square);
        mMyOrder = (RelativeLayout) view.findViewById(R.id.myOrder);
        ivSquare = (ImageView) view.findViewById(R.id.iconSquare);
        ivMyOrder = (ImageView) view.findViewById(R.id.iconMyOrder);
        mTvSquare = (TextView) view.findViewById(R.id.textSquare);
        mTvMyOrder = (TextView) view.findViewById(R.id.textMyOrder);
        top = (LinearLayout) view.findViewById(R.id.top);
        content = (RelativeLayout) view.findViewById(R.id.content);
        orderCount = (TextView) view.findViewById(R.id.orderCount);
    }

    @Override
    public void setupView() {
        list = new ArrayList<>();
        Toast.makeText(context, Values.getKey(Values.orderStatuses, "进行中")+"", Toast.LENGTH_LONG).show();
        mRefresh.setColorSchemeResources(android.R.color.holo_orange_light);
        mRefresh.setOnRefreshListener(this);
        rvOrderList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new OrderListAdapter(context, list);
        adapter.setOnOrderClickListener(this);
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

        mSquare.setOnClickListener(this);
        mMyOrder.setOnClickListener(this);

        isAllList = true;
        changeListToggle(isAllList);
    }


    @Override
    public void refresh(OrderListInteractor.OrderListBean data) {
//        OrderEntity entity = new OrderEntity();
        if (null != data
                && null != data.getOrderList()
                && data.getOrderList().size()>0) {
            if (page == 1) {
                list.clear();
            }
            list.addAll(data.getOrderList());
            adapter.notifyDataSetChanged();
            if (data.getCount()>0){
                orderCount.setText(data.getCount()+"");
            }else{
                orderCount.setText("0");
            }
        }
    }

    @Override
    public void onOrderClick(OrderEntity order) {
        startActivity(new Intent(context, OrderActivity.class)
        .putExtra("order", order));
    }

//    @Override
//    public void goToOrderDetail(OrderEntity order) {
//
//    }

    @Override
    public void changeListToggle(boolean isAllList) {
        page = 1;
        if (isAllList){
            ivSquare.setImageResource(R.mipmap.ico_orderlist_s);
            mTvSquare.setTextColor(getResources().getColor(R.color.themeColor));
            ivMyOrder.setImageResource(R.mipmap.ico_myorder_n);
            mTvMyOrder.setTextColor(getResources().getColor(R.color.textDarktwo));

            orderListPresenter.getAllList(page);
        }else {
            ivSquare.setImageResource(R.mipmap.ico_orderlist_n);
            mTvSquare.setTextColor(getResources().getColor(R.color.textDarktwo));
            ivMyOrder.setImageResource(R.mipmap.ico_myorder_s);
            mTvMyOrder.setTextColor(getResources().getColor(R.color.themeColor));

            orderListPresenter.getMyList(page);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.square:
                isAllList = true;
                break;
            case R.id.myOrder:
                isAllList = false;
                break;
        }
        changeListToggle(isAllList);
    }

    @Override
    public void onRefresh() {
        if (mRefresh.isRefreshing()) {
            mRefresh.setRefreshing(false);
        }
        changeListToggle(isAllList);
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
//        super.hideLoading();
    }

    @Override
    public void showError(String msg) {
        top.setVisibility(View.INVISIBLE);
        super.showError(msg);
    }

    @Override
    public void hideError() {
        top.setVisibility(View.VISIBLE);
        super.hideError();
    }
}