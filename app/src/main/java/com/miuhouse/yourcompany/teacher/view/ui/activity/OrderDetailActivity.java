package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.presenter.OrderDetailPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IOrderDetailPresenter;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IOrderDetailActivity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by khb on 2016/7/20.
 */
public class OrderDetailActivity extends BaseActivity implements IOrderDetailActivity {

    private ImageView studentHead;
    private TextView orderType;
    private ImageView ivOrderStatus;
    private TextView tvOrderStatus;
    private TextView studentName;
    private TextView teacher;
    private TextView tel;
    private TextView address;
    private TextView schedule;
    private TextView demend;
    private ImageView call;
    private LinearLayout content;
    private IOrderDetailPresenter presenter;
    private TextView classCount;
    private TextView totalPrice;
    private TextView time;

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
        studentHead = (ImageView) findViewById(R.id.studentHead);
        studentName = (TextView) findViewById(R.id.studentName);
        classCount = (TextView) findViewById(R.id.classCount);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        teacher = (TextView) findViewById(R.id.teacher);
        tel = (TextView) findViewById(R.id.tel);
        address = (TextView) findViewById(R.id.classAddress);
        schedule = (TextView) findViewById(R.id.schedule);
        demend = (TextView) findViewById(R.id.demand);
        call = (ImageView) findViewById(R.id.call);
        content = (LinearLayout) findViewById(R.id.content);

//        String teacherId = AccountDBTask.getAccount().getTeacherId();
//        String orderInfoId = getIntent().getStringExtra("orderId");
        String teacherId = "4028b88155c4dd070155c4dd8a340000";
        String orderInfoId = "4028b88155c4836f0155c48f0a020006";
        presenter.getOrderDetail(teacherId, orderInfoId);

    }

    @Override
    public void fillView(OrderEntity order) {
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
                call();
            }
        });
    }

    @Override
    public void showCountdown(OrderEntity order) {

    }

    @Override
    public void showOrderStatus(OrderEntity order) {

        int imgId = Util.getResourceId(this,
                String.format("ico_orderdetail_%s", order.getOrderStatus()),
                "mipmap");
        int strId = Util.getResourceId(this,
                String.format("order_status_%s", order.getOrderStatus()),
                "string");
        Glide.with(this).load(imgId)
                .placeholder(R.mipmap.asy)
                .error(R.mipmap.ic_launcher)
                .into(ivOrderStatus);
        tvOrderStatus.setText(getResources().getString(strId));

    }

    @Override
    public void call() {

    }


    @Override
    public void showLoading(String msg) {
        super.showLoading(msg);
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void hideError() {
        super.hideError();
    }
}
