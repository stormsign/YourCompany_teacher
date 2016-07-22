package com.miuhouse.yourcompany.teacher.presenter;

import com.miuhouse.yourcompany.teacher.interactor.OrderDetailInteractor;
import com.miuhouse.yourcompany.teacher.interactor.interf.IOrderDetailInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.presenter.interf.IOrderDetailPresenter;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IOrderDetailActivity;

/**
 * Created by khb on 2016/7/21.
 */
public class OrderDetailPresenter implements IOrderDetailPresenter, OnLoadCallBack{


    private IOrderDetailActivity activity;
    private IOrderDetailInteractor interactor;

    public OrderDetailPresenter(IOrderDetailActivity activity){
        this.activity = activity;
        interactor = new OrderDetailInteractor(this);
    }

    @Override
    public void getOrderDetail(String teacherId, String orderInfoId) {
        interactor.getInfo(teacherId, orderInfoId);
    }

    @Override
    public void beginClass(String teacherId, String orderInfoId) {
        interactor.beginClass(teacherId, orderInfoId);
    }

    @Override
    public void onPreLoad() {
        activity.showLoading(null);
    }

    @Override
    public void onLoadSuccess(Object data) {
        OrderDetailInteractor.OrderListBean bean
                = (OrderDetailInteractor.OrderListBean) data;
        if (null != bean){
            if (bean.getCode() == 0) {
                if (null != bean.getOrderList()) {
                    activity.fillView(bean.getOrderList());
                }else {
                    activity.showError("无数据");
                }
            }else {
                activity.showError("请求失败");
            }
        }
    }

    @Override
    public void onLoadFailed(String msg) {
        activity.showError(msg);
    }
}
