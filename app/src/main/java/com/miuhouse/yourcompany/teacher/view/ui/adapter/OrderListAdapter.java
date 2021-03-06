package com.miuhouse.yourcompany.teacher.view.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.db.DictDBTask;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseRVAdapter;
import com.miuhouse.yourcompany.teacher.view.widget.CircularImageViewHome;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by khb on 2016/7/7.
 */
public class OrderListAdapter extends BaseRVAdapter {

    private OnOrderItemClickListener onOrderItemClickListener;

    public OrderListAdapter(Context context, List<OrderEntity> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderListHolder(LayoutInflater.from(context).inflate(R.layout.item_ordersquarelist, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final OrderEntity entity = (OrderEntity) list.get(position);
        final OrderListHolder mholder = (OrderListHolder)holder;

//        entity.setUserHeader("http://p3.music.126.net/nUGiKZdgmElnsyx0ThbYrA==/2946691185724731.jpg?param=180y180");
        entity.setUserHeader(":");

        mholder.name.setText(entity.getCname());
        if (null != entity.getUserHeader()) {
            Glide.with(context).load(entity.getUserHeader())
                    .placeholder(R.mipmap.ico_head_default)
                    .error(R.mipmap.ico_head_default)
                    .into(mholder.header);
        }
        setOrderType(mholder.orderType, entity.getMajorDemand());
        mholder.price.setText(getPrice(entity));
        setOrderTopic(mholder.topic, entity.getMajorDemand(), entity.getMinorDemand());
        mholder.classCount.setText("x" + entity.getLesson());
        mholder.beginTime.setText(formatTime(entity.getClassBeginTimePromise()));
        mholder.distance.setText(formatDistance(entity.getDistance()));
        mholder.detail.setText(entity.getDescription());
        mholder.getOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.i("getOrder clicked");
                if (null != onOrderItemClickListener) {
                    onOrderItemClickListener.onGetOrderClick(entity);
                }
            }
        });
        mholder.orderContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onOrderItemClickListener) {
                    onOrderItemClickListener.onOrderClick(entity);
                }
            }
        });
    }

    private String getPrice(OrderEntity order) {
        float fPrice = order.getAmount()/Integer.parseInt(order.getLesson());
        float price = (float)(Math.round(fPrice*100)/100);
        return price+"";
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
//            DictDBTask.getDcNameList()
        }else if (majorDemand.equals("2")){
            List<String> list =  DictDBTask.getDcNameList("subject_type");
            topic.setText(list.get(Integer.parseInt(minorDemand)));
        }else if (majorDemand.equals("3")){
            List<String> list =  DictDBTask.getDcNameList("interest_type");
            topic.setText(list.get(Integer.parseInt(minorDemand)));
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

    public interface OnOrderItemClickListener{
        void onOrderClick(OrderEntity entity);
        void onGetOrderClick(OrderEntity entity);
    }

    public void setOnOrderItemClickListener(OnOrderItemClickListener onOrderItemClickListener){
        this.onOrderItemClickListener = onOrderItemClickListener;
    }

    class OrderListHolder extends RecyclerView.ViewHolder{
        TextView orderType;
        CircularImageViewHome header;
        TextView name;
        TextView price;
        TextView topic;
        TextView classCount;
        TextView beginTime;
        TextView distance;
        TextView detail;
        TextView getOrder;
        RelativeLayout orderContent;

        public OrderListHolder(View itemView) {
            super(itemView);
            orderType = (TextView) itemView.findViewById(R.id.type);
            header = (CircularImageViewHome) itemView.findViewById(R.id.header);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            topic = (TextView) itemView.findViewById(R.id.topic);
            classCount = (TextView) itemView.findViewById(R.id.classCount);
            beginTime = (TextView) itemView.findViewById(R.id.beginTime);
            distance = (TextView) itemView.findViewById(R.id.distance);
            detail = (TextView) itemView.findViewById(R.id.detail);
            getOrder = (TextView) itemView.findViewById(R.id.getOrder);
            orderContent = (RelativeLayout) itemView.findViewById(R.id.orderContent);

        }
    }

}
