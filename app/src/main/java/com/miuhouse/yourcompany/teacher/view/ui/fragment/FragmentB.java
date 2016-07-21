package com.miuhouse.yourcompany.teacher.view.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.presenter.OrderManagePresenter;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.OrderAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IOrderManageFragment;

import java.util.ArrayList;
import java.util.List;

/**订单管理
 * 待上课订单
 * Created by khb on 2016/7/18.
 */
public class FragmentB extends BaseFragment implements IOrderManageFragment, SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView blist;
    private SwipeRefreshLayout refresh;

    private List<OrderEntity> list;
    private OrderAdapter adapter;
    private OrderManagePresenter presenter;

    private int page = 1;

    @Override
    public int getFragmentResourceId() {
        return R.layout.fragment_a;
    }

    @Override
    public void getViews(View view) {
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.aRefresh);
        blist = (RecyclerView) view.findViewById(R.id.aList);
    }

    @Override
    public void setupView() {
        list = new ArrayList<>();
        presenter = new OrderManagePresenter(this);
        refresh.setOnRefreshListener(this);
        refresh.setColorSchemeResources(R.color.themeColor);
        blist.setLayoutManager(new LinearLayoutManager(context));
        adapter = new OrderAdapter(context, list, 3);
        blist.setAdapter(adapter);
        presenter.getBOrders(page);
        refresh();
    }

    @Override
    public View getOverrideParentView() {
        return refresh;
    }

    @Override
    public void refresh() {
        for (int i=0; i<10; i++){
            OrderEntity order = new OrderEntity();
            order.setClassBeginTimeActual(System.currentTimeMillis() - (i*5)*60*1000 );
            order.setLesson("3");
            list.add(order);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        page = 1;
        presenter.getBOrders(page);
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
    public void showError(String msg) {
//        super.showError(msg);
        if (!refresh.isRefreshing()){
            refresh.setRefreshing(false);
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void hideError() {
        super.hideError();
    }
}
