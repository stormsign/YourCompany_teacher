package com.miuhouse.yourcompany.teacher.view.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.presenter.OrderManagePresenter;
import com.miuhouse.yourcompany.teacher.view.ui.activity.OrderDetailActivity;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.OrderAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IOrderManageFragment;

import java.util.ArrayList;
import java.util.List;

/**订单管理
 * 待进行订单
 * Created by khb on 2016/7/18.
 */
public class FragmentC extends BaseFragment implements IOrderManageFragment, SwipeRefreshLayout.OnRefreshListener, OrderAdapter.OnOrderClick{

    private RecyclerView clist;
    private SwipeRefreshLayout refresh;

    private List<OrderEntity> list;
    private OrderAdapter adapter;
    private OrderManagePresenter presenter;

    private int page = 1;
    private MyReceiver receiver;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            list.clear();
            for (int i=0; i<msg.arg2; i++){
                OrderEntity order = new OrderEntity();
                order.setClassBeginTimeActual(1469071381770L - (i*5)*60*1000 - 60*1000*24 );
                order.setLesson("2");
                list.add(order);
            }
            if (msg.arg1<msg.arg2){
                list.remove(msg.arg1);
            }
            adapter.notifyDataSetChanged();
            Message msg2 = Message.obtain();
            msg2.arg1 = msg.arg1;
            msg2.arg2 = msg.arg2;
            sendMessageDelayed(msg2, 1000);
        }
    };

    @Override
    public int getFragmentResourceId() {
        return R.layout.fragment_c;
    }

    @Override
    public void getViews(View view) {
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.cRefresh);
        clist = (RecyclerView) view.findViewById(R.id.cList);
    }

    @Override
    public void setupView() {
        list = new ArrayList<>();
        presenter = new OrderManagePresenter(this);
        refresh.setOnRefreshListener(this);
        refresh.setColorSchemeResources(R.color.themeColor);
        clist.setLayoutManager(new LinearLayoutManager(context));
        adapter = new OrderAdapter(context, list, 4);
        adapter.setOnOrderClick(this);
        clist.setAdapter(adapter);
        presenter.getAOrders(page);
        refresh();
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter("com.miuhouse.yourcompany.teacher.ACTION.TIMESUP");
        context.registerReceiver(receiver, filter);
    }

    private class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int position = intent.getIntExtra("position", 0);
//            list.remove(position);
            adapter.notifyItemRemoved(position);
            Message msg = Message.obtain();
            msg.arg1 = position;
            msg.arg2 = list.size();
            handler.sendMessageDelayed(msg, 1000);
        }
    }

    @Override
    public View getOverrideParentView() {
        return refresh;
    }

    @Override
    public void refresh() {
        for (int i=0; i<10; i++){
            OrderEntity order = new OrderEntity();
            order.setClassBeginTimeActual(1469071381770L - (i*5)*60*1000 - 60*1000*24);
            order.setLesson("2");
            list.add(order);
        }
        Message msg = Message.obtain();
        msg.arg1 = list.size()+1;
        msg.arg2 = list.size();
        handler.sendMessageDelayed(msg, 1000);
    }

    @Override
    public void onRefresh() {
        page = 1;
        presenter.getAOrders(page);
    }

    @Override
    public void onOrderClick() {
        startActivity(new Intent(context, OrderDetailActivity.class));
    }

    @Override
    public void onButtonClick() {

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(receiver);
    }
}
