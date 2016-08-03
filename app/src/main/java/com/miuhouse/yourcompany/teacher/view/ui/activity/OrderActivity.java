package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.db.AccountDBTask;
import com.miuhouse.yourcompany.teacher.db.DictDBTask;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.presenter.OrderPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IOrderPresenter;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IOrderActivity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;
import com.miuhouse.yourcompany.teacher.view.widget.CircularImageViewHome;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by khb on 2016/7/11.
 */
public class OrderActivity extends BaseActivity implements IOrderActivity {

    private OrderEntity order;
    private CircularImageViewHome header;
    private TextView name;
    private TextView orderType;
    private TextView topic;
    private TextView beginTime;
    private TextView distance;
    private TextView price;
    private TextView classCount;
    private TextView totalPrice;
    private TextView detail;

    private IOrderPresenter presenter ;
    private RelativeLayout content;

    @Override
    protected String setTitle() {
        return "需求详情";
    }

    @Override
    protected String setRight() {
        return null;
    }

    @Override
    protected void initViewAndEvents() {
        presenter = new OrderPresenter(this);
        order = (OrderEntity) getIntent().getSerializableExtra("order");
        header = (CircularImageViewHome) findViewById(R.id.header);
        name = (TextView) findViewById(R.id.name);
        orderType = (TextView) findViewById(R.id.orderType);
        topic = (TextView) findViewById(R.id.topic);
        beginTime = (TextView) findViewById(R.id.beginTime);
        distance = (TextView) findViewById(R.id.distance);
        price = (TextView) findViewById(R.id.price);
        classCount = (TextView) findViewById(R.id.classCount);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        detail = (TextView) findViewById(R.id.detail);
        if (null!= order.getUserHeader()){
            Glide.with(activity).load(order.getUserHeader())
                    .placeholder(R.mipmap.asy)
                    .error(R.mipmap.ic_launcher)
                    .into(header);
        }
        name.setText(order.getCname());
        setOrderType(order.getMajorDemand(), orderType);
        setOrderTopic(topic, order.getMajorDemand(), order.getMinorDemand());
        beginTime.setText(formatTime(order.getClassBeginTimePromise()));
        distance.setText(formatDistance(order.getDistance()));
        price.setText("￥"+getPrice(order));
        classCount.setText("x"+order.getLesson());
        totalPrice.setText("￥"+order.getAmount());
        detail.setText(order.getDescription());
        TextView grabOrder = (TextView) findViewById(R.id.grabOrder);
        if (!order.getOrderStatus().equals("2")){
            grabOrder.setVisibility(View.GONE);
        }else {
            grabOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.grabOrder(AccountDBTask.getAccount().getId(), order.getId());
                }
            });
        }
        content = (RelativeLayout) findViewById(R.id.content);
    }

    private void setOrderType(String majorDemand, TextView orderType) {
        if (!Util.isEmpty(majorDemand)){
            int stringId = Util.getResourceId(this, String.format("order_type_%s", majorDemand), "string");
            orderType.setText(getResources().getString(stringId));
            int colorId = Util.getResourceId(this, String.format("background_order_%s", majorDemand), "color");
            orderType.setBackgroundResource(colorId);
        }
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

    private String formatTime(long time){
        SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm");
        return format.format(new Date(time));
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

    private String getPrice(OrderEntity order) {
        float fPrice = order.getAmount()/Integer.parseInt(order.getLesson());
        float price = (float)(Math.round(fPrice*100)/100);
        return price+"";
    }

    private int getTotalPrice(int count, int price){
        return count * price;
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    protected View getOverrideParentView() {
        return content;
    }

    @Override
    public void showLoading(String msg) {
        super.showLoading(msg);
    }

    @Override
    public void showError(int type) {
        super.showError(type);
    }
}
