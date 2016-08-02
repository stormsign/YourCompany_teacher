package com.miuhouse.yourcompany.teacher.presenter;

import com.miuhouse.yourcompany.teacher.interactor.OrderManageInteractor;
import com.miuhouse.yourcompany.teacher.interactor.interf.IOrderManageInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.presenter.interf.IOrderManagePresenter;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IOrderManageFragment;
import com.miuhouse.yourcompany.teacher.view.widget.ViewOverrideManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by khb on 2016/7/19.
 */
public class OrderManagePresenter implements IOrderManagePresenter, OnLoadCallBack {

    private IOrderManageInteractor interactor;
    private IOrderManageFragment fragment;

    private String teacherId;

    public OrderManagePresenter(IOrderManageFragment fragment) {
        this.interactor = new OrderManageInteractor(this);
        this.fragment = fragment;
    }

    @Override
    public void getAOrders(String teacherId, int page) {
        interactor.getAOrders(teacherId, page);
    }

    @Override
    public void getBOrders(String teacherId, int page) {
        interactor.getBOrders(teacherId, page);
    }

    @Override
    public void getCOrders(String teacherId, int page) {
        interactor.getCOrders(teacherId, page);
    }

    @Override
    public void getDOrders(String teacherId, int page) {
        interactor.getDOrders(teacherId, page);
    }

    @Override
    public void beginClass(String teacherId, String orderId) {
        interactor.beginClass(teacherId, orderId);
    }

    @Override
    public void onPreLoad() {
        fragment.showLoading(null);
    }

    @Override
    public void onLoadSuccess(Object data) {
        fragment.hideLoading();
        if (data instanceof OrderManageInteractor.OrderListBean) {
            OrderManageInteractor.OrderListBean bean = (OrderManageInteractor.OrderListBean) data;
            if (bean.getCode() == 1) {
                fragment.onTokenExpired();
                return;
            }
            if (null != bean
                    && null != bean.getOrderList()
                    && bean.getOrderList().size() > 0) {
                fragment.refresh(bean);
            } else {
//                fragment.showError("没有数据");
                fragment.showError(ViewOverrideManager.NO_ORDER);
            }
        } else if (data instanceof String) {
            String resp = (String) data;
            try {
                JSONObject jObject = new JSONObject(resp);
                if (jObject.getInt("code") == 0) {
//                    activity.showLoading();
//                    fragment.afterBeginClass();
//                    这段代码很ugly..........然而没找到很好的方法
                    OrderEntity order = new OrderEntity();
                    order.setId(jObject.getString("orderid"));
                    fragment.goToDetail(order, 1);
                } else {
//                    fragment.showError(jObject.getString("msg"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        fragment.hideLoading();
    }

    @Override
    public void onLoadFailed(String msg) {
        fragment.hideLoading();
//        fragment.showError(msg);
    }
}
