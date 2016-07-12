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
import com.miuhouse.yourcompany.teacher.utils.Util;
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
        final OrderEntity entity = (OrderEntity) list.get(position);
        final OrderListHolder mholder = (OrderListHolder)holder;

        entity.setCname("螚安");
        entity.setUserHeader("http://p3.music.126.net/nUGiKZdgmElnsyx0ThbYrA==/2946691185724731.jpg?param=180y180");
        entity.setMajorDemand("3");
        entity.setMinorDemand("3");
        entity.setClassBeginTimePromise(System.currentTimeMillis());
        entity.setDistance(23000);
        entity.setDescription("会计的分规格表快节奏的及的卡卡阿卡");
        entity.setLesson("2");
        entity.setAmount(100);


        mholder.name.setText(entity.getCname());
        if (null != entity.getUserHeader()) {
            Glide.with(context).load(entity.getUserHeader())
                    .placeholder(R.mipmap.asy)
                    .error(R.mipmap.ic_launcher)
                    .into(mholder.header);
        }
        setOrderType(mholder.orderType, entity.getMajorDemand());
        mholder.price.setText(getPrice(entity.getMajorDemand()));
        setOrderTopic(mholder.topic, entity.getMajorDemand(), entity.getMinorDemand());
        mholder.classCount.setText("x" + entity.getLesson());
        mholder.beginTime.setText(formatTime(entity.getClassBeginTimePromise()));
        mholder.distance.setText(formatDistance(entity.getDistance()));
        mholder.detail.setText(entity.getDescription());
        mholder.getOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onOrderClickListener){
                    onOrderClickListener.onOrderClick(entity);
                }
            }
        });
    }

    private String getPrice(String majorDemand) {
        if (null == majorDemand){
            return "";
        }
        if (majorDemand.equals("1")){
            return "40";
        }else if (majorDemand.equals("2")){
            return "50";
        }else if (majorDemand.equals("3")){
            return "60";
        }
        return "";
    }


    private String formatDistance(long distance) {
        if (distance<=0){
            return "";
        }
        float f = distance / 1000;
        if (f<1){
            return distance + "m";
        }else { //保留小数点后一位
            f = Math.round(f*10)/10;
            return f + "km";
        }
    }

    private String formatTime(long time){
        SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm");
        return format.format(new Date(time));
    }

    private void setOrderTopic(TextView topic, String majorDemand, String minorDemand) {
        if (null == majorDemand || null == minorDemand){
            return ;
        }
        if (majorDemand.equals("1")){
        }else if (majorDemand.equals("2")){
        }else if (majorDemand.equals("3")){
            if (minorDemand.equals("3")){
                topic.setText("书法");
            }
        }
    }

    private void setOrderType(TextView orderType, String majorDemand) {
        if (null==majorDemand){
            return ;
        }
        if (!Util.isEmpty(majorDemand)){
            int stringId = Util.getResourceId(context, String.format("order_type_%s", majorDemand), "string");
            orderType.setText(context.getResources().getString(stringId));
            int imgId = Util.getResourceId(context, String.format("home_list_title_%s", majorDemand), "mipmap");
            orderType.setBackgroundResource(imgId);
        }
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
        TextView getOrder;
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
            getOrder = (TextView) itemView.findViewById(R.id.getOrder);

        }
    }

    public void setOnOrderClickListener(OnOrderClickListener onOrderClickListener){
        this.onOrderClickListener = onOrderClickListener;
    }

    public interface OnOrderClickListener {
        void onOrderClick(OrderEntity order);
    }
}
