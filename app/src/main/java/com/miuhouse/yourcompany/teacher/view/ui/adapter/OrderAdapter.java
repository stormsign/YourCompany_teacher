package com.miuhouse.yourcompany.teacher.view.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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

    private long test = System.currentTimeMillis();
    private List<OrderHolder> holderList;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            OrderHolder holder = (OrderHolder) msg.obj;
            holder.button.setText(holder.hour + ":" + holder.minute);
        }
    };

    public OrderAdapter(Context context, List<OrderEntity> list) {
        super(context, list);
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
        Thread thread;

        long hour;
        long minute;
        private long beginTime;
        private String timeLength;
        private int position;

        boolean isRunning = true;

        OrderHolder holder = this;

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

            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (beginTime <0
                            || Util.isEmpty(timeLength)
                            || position < 0){
                        isRunning = false;
                    }

                    while(isRunning){
                        long longLength = Integer.parseInt(timeLength)*60*60*1000;
                        final long endTime = longLength + beginTime;
//                        if (endTime >= System.currentTimeMillis()){
//                            isRunning = true;
//                        }else {
//                            isRunning = false;
//                        }
                        long passedTime = System.currentTimeMillis() - beginTime;
                        hour = passedTime / (1000 * 60 * 60);
                        minute = passedTime % (1000 * 60 * 60) / (1000 * 60);
                        L.i(hour + " " + minute);
//                        if (minute == 35){
//                            Intent intent = new Intent("com.miuhouse.yourcompany.teacher.ACTION.TIMESUP");
//                            intent.putExtra("position", position);
//                            context.sendBroadcast(intent);
//                        }
                        Message msg = Message.obtain();
                        msg.obj = holder;
                        handler.sendMessage(msg);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
//            if (beginTime > 0
//                    && !Util.isEmpty(timeLength)
//                    && position > 0){
                thread.start();
//            }

        }

        public void start(){
            isRunning = true;
            thread.start();
        }

        public void setParams(long classBeginTimeActual,
                              String timeLength,
                              int position){
            beginTime = classBeginTimeActual;
            this.timeLength = timeLength;
            this.position = position;
//            isRunning = false;
//            if (thread.isAlive()) {
//                thread.stop();
//            }
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        L.i("position :"+position + " holder : " + holder.toString());
        OrderEntity order = (OrderEntity) list.get(position);
        OrderHolder mholder = (OrderHolder) holder;
        order.setClassBeginTimeActual(1469036332490L-position*1000*60);
        order.setLesson("3");
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
        setButton(mholder.button, order.getOrderStatus());
        mholder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnOrderClick) {
                    mOnOrderClick.onOrderClick();
                }
            }
        });

        mholder.setParams(order.getClassBeginTimeActual(), order.getLesson(), position);
        mholder.isRunning = true;
//        mholder.start();

//        if (order.getOrderStatus().equals(Values.getKey(Values.orderStatuses, "进行中"))){
//            startCountTime(mholder,
//                    order.getClassBeginTimeActual(),
//                    order.getLesson(),
//                    position);
//        }

    }

    private void startCountTime(final OrderHolder holder,
                                final long classBeginTimeActual,
                                String timeLength,
                                final int position) {
        long longLength = Integer.parseInt(timeLength)*60*60*1000;
        final long endTime = longLength + classBeginTimeActual;
        L.i("endtime" + endTime);
        L.i("classBeginTimeActual" + classBeginTimeActual);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(endTime >= System.currentTimeMillis()){
                        long passedTime = System.currentTimeMillis() - classBeginTimeActual;
                        holder.hour = passedTime / (1000 * 60 * 60);
                        holder.minute = passedTime % (1000 * 60 * 60) / (1000 * 60);
                        L.i(holder.hour + " " + holder.minute);
                        if (holder.minute == 35){
                            Intent intent = new Intent("com.miuhouse.yourcompany.teacher.ACTION.TIMESUP");
                            intent.putExtra("position", position);
                            context.sendBroadcast(intent);
                        }
                        Message msg = Message.obtain();
                        msg.obj = holder;
                        handler.sendMessage(msg);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
    }




    private void setButton(TextView button, String orderStatus) {
//        int status = Integer.parseInt(orderStatus);
//        switch (status){
//
//        }
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
