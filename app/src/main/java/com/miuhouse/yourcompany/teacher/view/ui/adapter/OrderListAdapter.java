package com.miuhouse.yourcompany.teacher.view.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseRVAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by khb on 2016/7/7.
 */
public class OrderListAdapter extends BaseRVAdapter {

    private OnOrderClickListener onOrderClickListener;

    public OrderListAdapter(Context context, List<OrderEntity> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderListHolder(LayoutInflater.from(context).inflate(R.layout.item_orderlist, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderEntity order = (OrderEntity) list.get(position);
        OrderListHolder mholder = (OrderListHolder)holder;
        mholder.name.setText(order.getCname());
        Glide.with(context).load(order.getUserHeader())
                .placeholder(R.mipmap.asy)
                .error(R.mipmap.ic_launcher)
                .into(mholder.header);
        setOrderType(mholder.orderType, order.getMajorDemand());
        mholder.price.setText(order.getAmount() + "");
        mholder.topic.setText(order.getMinorDemand());
        mholder.classCount.setText(order.getLesson());
        mholder.beginTime.setText(formatTime(order.getClassBeginTimePromise()));
        mholder.distance.setText(formatDistance(order.getDistance()));
        mholder.detail.setText(order.getDescription());

    }

    private String formatDistance(long distance) {
        return "0";
    }

    private String formatTime(long time){
        SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm");
        return format.format(new Date(time));
    }

    private void setOrderType(TextView orderType, String majorDemand) {

    }

    class OrderListHolder extends RecyclerView.ViewHolder{
        TextView orderType;
        ImageView header;
        TextView name;
        TextView price;
        TextView topic;
        TextView classCount;
        TextView beginTime;
        TextView distance;
        TextView detail;
        public OrderListHolder(View itemView) {
            super(itemView);
            orderType = (TextView) itemView.findViewById(R.id.type);
            header = (ImageView) itemView.findViewById(R.id.header);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            topic = (TextView) itemView.findViewById(R.id.topic);
            classCount = (TextView) itemView.findViewById(R.id.classCount);
            beginTime = (TextView) itemView.findViewById(R.id.beginTime);
            distance = (TextView) itemView.findViewById(R.id.distance);
            detail = (TextView) itemView.findViewById(R.id.detail);

        }
    }

    public void setOnOrderClickListener(OnOrderClickListener onOrderClickListener){
        this.onOrderClickListener = onOrderClickListener;
    }

    public interface OnOrderClickListener {
        void onOrderClick();
    }
}
