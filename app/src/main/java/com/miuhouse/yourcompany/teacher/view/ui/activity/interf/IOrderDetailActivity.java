package com.miuhouse.yourcompany.teacher.view.ui.activity.interf;

import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseView;

/**
 * Created by khb on 2016/7/20.
 */
public interface IOrderDetailActivity extends BaseView {

    void showOrderStatus(OrderEntity order);
    void showCountdown(OrderEntity order);
    void call(String number);
    void fillView(OrderEntity order);
    void beforeBeginClass();
    void afterBeginClass();
}
