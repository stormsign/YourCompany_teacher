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

    public OrderManagePresenter(IOrderManageFragment fragment) {
        this.interactor = new OrderManageInteractor(this);
    }

    @Override
    public void getAOrders(int page) {

    }

    @Override
    public void getBOrders(int page) {

    }

    @Override
    public void getCOrders(int page) {

    }

    @Override
    public void getDOrders(int page) {

    }

    @Override
    public void onPreLoad(String msg) {

    }

    @Override
    public void onLoadSuccess(Object data) {

    }

    @Override
    public void onLoadFailed(String msg) {

    }
}
