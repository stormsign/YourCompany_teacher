package com.miuhouse.yourcompany.teacher.view.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.miuhouse.yourcompany.teacher.listener.OnListItemClick;

import java.util.List;

/**
 * Created by khb on 2016/7/7.
 */
public abstract class BaseRVAdapter<T> extends RecyclerView.Adapter {

    public List<T> list;
    public Context context;
    public OnListItemClick onListItemClick;

    public BaseRVAdapter(Context context, List<T> list, OnListItemClick onListItemClick) {
        this.context = context;
        this.list = list;
        this.onListItemClick = onListItemClick;
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
