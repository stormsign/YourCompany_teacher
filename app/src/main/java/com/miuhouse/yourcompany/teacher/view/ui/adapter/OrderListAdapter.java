package com.miuhouse.yourcompany.teacher.view.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.miuhouse.yourcompany.teacher.model.OrderItemEntity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseRVAdapter;

import java.util.List;

/**
 * Created by khb on 2016/7/7.
 */
public class OrderListAdapter extends BaseRVAdapter {
    public OrderListAdapter(Context context, List<OrderItemEntity> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class OrderListHolder extends RecyclerView.ViewHolder{

        public OrderListHolder(View itemView) {
            super(itemView);
        }
    }
}
