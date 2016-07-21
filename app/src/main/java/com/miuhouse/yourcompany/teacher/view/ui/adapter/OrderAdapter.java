package com.miuhouse.yourcompany.teacher.view.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseRVAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by khb on 2016/7/19.
 */
public class OrderAdapter extends BaseRVAdapter{

    private int orderType;

    public OrderAdapter(Context context, List<OrderEntity> list, int orderType) {
        super(context, list);
        this.orderType = orderType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderHolder(LayoutInflater.from(context).inflate(R.layout.item_ordermanage, null));
    }

    class OrderHolder extends RecyclerView.ViewHolder{
        TextView orderType;
        TextView orderStatus;
        ImageView studentHead;
        TextView studentName;
        TextView time;
        TextView totalPrice;
        TextView classCount;
        TextView button;
        LinearLayout content;

        public OrderHolder(View itemView) {
            super(itemView);
            content = (LinearLayout) itemView.findViewById(R.id.orderContent);
            orderType = (TextView) itemView.findViewById(R.id.orderType);
            orderStatus = (TextView) itemView.findViewById(R.id.orderStatus);
            studentHead = (ImageView) itemView.findViewById(R.id.studentHead);
            studentName = (TextView) itemView.findViewById(R.id.studentName);
            time = (TextView) itemView.findViewById(R.id.time);
            totalPrice = (TextView) itemView.findViewById(R.id.totalPrice);
            classCount = (TextView) itemView.findViewById(R.id.classCount);
            button = (TextView) itemView.findViewById(R.id.orderManageButton);

//            if (beginTime > 0
//                    && !Util.isEmpty(timeLength)
//                    && position > 0){
//            }

        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        L.i("position :"+position + " holder : " + holder.toString());

        OrderEntity order = (OrderEntity) list.get(position);
        OrderHolder mholder = (OrderHolder) holder;
        setOrderType(mholder.orderType, order.getMajorDemand());
        setOrderStatus(mholder.orderStatus, order.getOrderStatus());
        if (!Util.isEmpty(order.getUserHeader())) {
            Glide.with(context).load(order.getUserHeader())
                    .placeholder(R.mipmap.asy)
                    .error(R.mipmap.ic_launcher)
                    .into(mholder.studentHead);
        }
        mholder.studentName.setText(order.getCname());
        String strTime = "课程安排 " + formatTime(order.getClassBeginTimePromise());
        mholder.time.setText(strTime);
        mholder.totalPrice.setText("￥" + order.getAmount());
        mholder.classCount.setText("共" + order.getLesson() + "课时");
        setButton(mholder.button, order, position);
        mholder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnOrderClick) {
                    mOnOrderClick.onOrderClick();
                }
            }
        });

//        mholder.start();

//        if (order.getOrderStatus().equals(Values.getKey(Values.orderStatuses, "进行中"))){
//            startCountTime(mholder,
//                    order.getClassBeginTimeActual(),
//                    order.getLesson(),
//                    position);
//        }

    }

    private void setButton(TextView button, OrderEntity order, int position) {
//        int status = Integer.parseInt(orderStatus);
//        switch (status){
//
//        }
        if (orderType == 4) {
            long beginTime = order.getClassBeginTimeActual();
            long endTime = Integer.parseInt(order.getLesson()) * 60 * 60 * 1000 + beginTime;
            long diffTime = System.currentTimeMillis() - beginTime;

            long hour = diffTime / (60 * 60 * 1000);
            long minute = diffTime % (60 * 60 * 1000) / (60 * 1000);

            String strHour = hour < 10 ? "0" + hour : "" + hour;
            String strMinute = minute < 10 ? "0" + minute : "" + minute;
            if (System.currentTimeMillis() < endTime) {
                String strTime = strHour + ":" + strMinute;
                button.setText(strTime);
            } else {
                Intent intent = new Intent("com.miuhouse.yourcompany.teacher.ACTION.TIMESUP");
                intent.putExtra("position", position);
                context.sendBroadcast(intent);
            }
            button.setEnabled(false);
            button.setTextColor(context.getResources().getColor(R.color.textWhite));
        }else if (orderType == 3){
            button.setEnabled(true);
            button.setText("开始上课");
        }else {
            button.setVisibility(View.INVISIBLE);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnOrderClick){
                    mOnOrderClick.onButtonClick();
                }
            }
        });

    }

    private String formatTime(long classBeginTimePromise) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd hh:mm");
        return formatter.format(new Date(classBeginTimePromise));
    }


    private void setOrderStatus(TextView orderStatus, String orderStatus1) {
        orderStatus.setText("待上课");
    }

    private void setOrderType(TextView orderType, String majorDemand) {
        if (Util.isEmpty(majorDemand)){
            return ;
        }
        int stringId = Util.getResourceId(context, String.format("order_type_%s", majorDemand), "string");
        orderType.setText(context.getResources().getString(stringId));
        int mipmapId = Util.getResourceId(context, String.format("home_list_title_%s", majorDemand), "mipmap");
        orderType.setBackgroundResource(mipmapId);
    }

    public interface OnOrderClick{
        void onOrderClick();
        void onButtonClick();
    }

    private OnOrderClick mOnOrderClick;

    public void setOnOrderClick(OnOrderClick onOrderClick){
        mOnOrderClick = onOrderClick;
    }
}
