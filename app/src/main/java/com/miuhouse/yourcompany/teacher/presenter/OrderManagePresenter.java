package com.miuhouse.yourcompany.teacher.presenter;

import com.miuhouse.yourcompany.teacher.interactor.OrderManageInteractor;
import com.miuhouse.yourcompany.teacher.interactor.interf.IOrderManageInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.presenter.interf.IOrderManagePresenter;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IOrderManageFragment;

/**
 * Created by khb on 2016/7/19.
 */
public class OrderManagePresenter implements IOrderManagePresenter, OnLoadCallBack {

    private IOrderManageInteractor interactor;
    private IOrderManageFragment fragment;

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
    public void onPreLoad() {

    }

    @Override
    public void onLoadSuccess(Object data) {
        OrderManageInteractor.OrderListBean bean = (OrderManageInteractor.OrderListBean) data;
        fragment.refresh(bean);
    }

    @Override
    public void onLoadFailed(String msg) {

    }
}
