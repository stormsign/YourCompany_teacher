package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.application.App;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.presenter.OrderDetailPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IOrderDetailPresenter;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.utils.Values;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IOrderDetailActivity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;
import com.miuhouse.yourcompany.teacher.view.widget.CircularImageViewHome;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by khb on 2016/7/20.
 */
public class OrderDetailActivity extends BaseActivity implements IOrderDetailActivity {

    private CircularImageViewHome studentHead;
    private TextView orderType;
    private ImageView ivOrderStatus;
    private TextView tvOrderStatus;
    private TextView studentName;
    private TextView teacher;
    private TextView tel;
    private TextView address;
    private TextView schedule;
    private TextView demend;
    private LinearLayout call;
    private LinearLayout content;
    private IOrderDetailPresenter presenter;
    private TextView classCount;
    private TextView totalPrice;
    private TextView time;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            OrderEntity order = (OrderEntity) msg.obj;

            long beginTime = order.getClassBeginTimeActual();
            long currentTime = System.currentTimeMillis();
            long diffTime = currentTime - beginTime;
            long hour = diffTime / (60 * 60 * 1000);
            long minute = diffTime % (60 * 60 * 1000) / (60 * 1000);

            String strHour = hour < 10 ? "0" + hour : "" + hour;
            String strMinute = minute < 10 ? "0" + minute : "" + minute;
            if (diffTime < Integer.parseInt(order.getLesson())*60*60*1000){
                time.setText(strHour + ":" + strMinute);
                Message msg2 = Message.obtain();
                msg2.obj = order;
                handler.sendMessageDelayed(msg2, 1000*10);
            }else {
                time.setText("已到点");
            }


        }
    };
    private TextView actual;
    private RelativeLayout bottom;
    private TextView button;
    private String teacherId;
    private String orderInfoId;

    private OrderEntity order;
    @Override
    protected String setTitle() {
        return "订单详情";
    }

    @Override
    protected String setRight() {
        return null;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_orderdetail;
    }

    @Override
    protected View getOverrideParentView() {
        return content;
    }

    @Override
    protected void initViewAndEvents() {

        presenter = new OrderDetailPresenter(this);

        ivOrderStatus = (ImageView) findViewById(R.id.ivOrderStatus);
        tvOrderStatus = (TextView) findViewById(R.id.tvOrderStatus);
        time = (TextView) findViewById(R.id.time);
        orderType = (TextView) findViewById(R.id.orderType);
        studentHead = (CircularImageViewHome) findViewById(R.id.studentHead);
        studentName = (TextView) findViewById(R.id.studentName);
        classCount = (TextView) findViewById(R.id.classCount);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        teacher = (TextView) findViewById(R.id.teacher);
        tel = (TextView) findViewById(R.id.tel);
        address = (TextView) findViewById(R.id.classAddress);
        schedule = (TextView) findViewById(R.id.schedule);
        demend = (TextView) findViewById(R.id.demand);
        call = (LinearLayout) findViewById(R.id.call);
        content = (LinearLayout) findViewById(R.id.content);
        actual = (TextView) findViewById(R.id.actual);
        bottom = (RelativeLayout) findViewById(R.id.bottom);
        button = (TextView) findViewById(R.id.button);

//      teacherId = AccountDBTask.getAccount().getTeacherId();
        orderInfoId = getIntent().getStringExtra("orderId");
        teacherId = App.getInstance().getTeacherId();
//        orderInfoId = "4028b88155c4836f0155c48f0a020006";
        presenter.getOrderDetail(teacherId, orderInfoId);

    }

    @Override
    public void fillView(final OrderEntity order) {
        this.order = order;
        if (!Util.isEmpty(order.getUserHeader())){
            Glide.with(activity).load(order.getUserHeader())
                    .placeholder(R.mipmap.asy)
                    .error(R.mipmap.ic_launcher)
                    .into(studentHead);
        }
        studentName.setText("学生：" + order.getCname());
        classCount.setText("共购买"+order.getLesson()+"课时");
        float amount = Math.round(order.getAmount()*100)/100;
        totalPrice.setText("总计"+amount+"元");
        teacher.setText("上课人："+order.getTname());
        tel.setText(order.getMobile());
        address.setText("上课地址："+order.getAddress());
        String date = new SimpleDateFormat("MM月dd日")
                .format(new Date(order.getClassBeginTimePromise()));
        String startTime = new SimpleDateFormat("HH:mm")
                .format(new Date(order.getClassBeginTimePromise()));
        String endTime = new SimpleDateFormat("HH:mm")
                .format(new Date(order.getClassBeginTimePromise()
                                    + Integer.parseInt(order.getLesson())*60*60*1000));
        String formatTime = date + " " + startTime+"-"+endTime;

        schedule.setText("课程安排："+formatTime);
        demend.setText(order.getDescription());
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(order.getMobile());
            }
        });
        showOrderType(order);
        showOrderStatus(order);
        showCountdown(order);
    }

    private void showOrderType(OrderEntity order) {
        int strId = Util.getResourceId(this,
                String.format("order_type_%s", order.getMajorDemand()),
                "string");
        int colorId = Util.getResourceId(this,
                String.format("background_order_%s", order.getMajorDemand()),
                "color");
        orderType.setText(getResources().getString(strId));
        orderType.setBackgroundResource(colorId);
    }

    @Override
    public void showCountdown(OrderEntity order) {
        if (Integer.parseInt(order.getOrderStatus())
                == 4){

            Message msg = Message.obtain();
            msg.obj = order;
            handler.sendMessage(msg);

        }else {
            time .setVisibility(View.GONE);
        }
    }

    @Override
    public void showOrderStatus(OrderEntity order) {

        int imgId = Util.getResourceId(this,
                String.format("ico_orderdetail_%s", order.getOrderStatus()),
                "mipmap");
        int strId = Util.getResourceId(this,
                String.format("order_status_%s", order.getOrderStatus()),
                "string");
//        Glide.with(activity).load(imgId)
//                .placeholder(R.mipmap.asy)
//                .error(R.mipmap.ic_launcher)
//                .into(ivOrderStatus);

        ivOrderStatus.setImageResource(imgId);
        tvOrderStatus.setText(getResources().getString(strId));
        actual.setVisibility(View.GONE);
        bottom.setVisibility(View.GONE);
        if (Values.orderStatuses.get(order.getOrderStatus()).equals("待上课")){
            bottom.setVisibility(View.VISIBLE);
            button.setText("开始上课");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.beginClass(teacherId, orderInfoId);
                }
            });
        }else if (Values.orderStatuses.get(order.getOrderStatus()).equals("待评价")
                || Values.orderStatuses.get(order.getOrderStatus()).equals("已完成")){
            actual.setVisibility(View.VISIBLE);
            String date = new SimpleDateFormat("MM月dd日")
                    .format(new Date(order.getClassBeginTimeActual()));
            String startTime = new SimpleDateFormat("HH:mm")
                    .format(new Date(order.getClassBeginTimeActual()));
            String endTime = new SimpleDateFormat("HH:mm")
                    .format(new Date(order.getClassBeginTimeActual()
                            + Integer.parseInt(order.getLesson())*60*60*1000));
            String formatTime = date + " " + startTime+"-"+endTime;
            actual.setText("实际上课："+formatTime);
        }else {

        }

    }

    @Override
    public void call(String number) {
        //传入服务， parse（）解析号码
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        //通知activtity处理传入的call服务
        startActivity(intent);
    }

    @Override
    public void showLoading(String msg) {
        super.showLoading("LOADING...");
    }

    @Override
    public void showError(int type) {
        viewOverrideManager.showLoading(type, null);
    }

    @Override
    public void onBackClick() {
        if (null != order){
            if (order.getOrderStatus().equals("4")){        //当前为进行中状态
                setResult(1);
            }
        }
        super.onBackClick();
    }

    @Override
    public void onBackPressed() {
        if (null != order){
            if (order.getOrderStatus().equals("4")){        //当前为进行中状态
                setResult(1);
            }
        }
        super.onBackPressed();
    }

    @Override
    public void beforeBeginClass() {
        showLoading(null);
    }

    @Override
    public void afterBeginClass() {
//        if (null != order){
//            if (order.getOrderStatus().equals("3")){        //当前为待上课状态
                setResult(1);
                finish();
//            }
//        }
    }
}
