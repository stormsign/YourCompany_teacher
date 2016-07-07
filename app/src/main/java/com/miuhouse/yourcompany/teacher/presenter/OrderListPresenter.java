package com.miuhouse.yourcompany.teacher.presenter;

import com.miuhouse.yourcompany.teacher.interactor.OrderListInteractor;
import com.miuhouse.yourcompany.teacher.interactor.interf.IOrderListInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.presenter.interf.IOrderListPresenter;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IOrdersFragment;

/**
 * Created by khb on 2016/7/7.
 */
public class OrderListPresenter implements IOrderListPresenter, OnLoadCallBack {

    private IOrdersFragment iOrdersFragment;
    private IOrderListInteractor iOrderListInteractor;

    public OrderListPresenter(IOrdersFragment iOrdersFragment){
        this.iOrdersFragment = iOrdersFragment;
        iOrderListInteractor = new OrderListInteractor(this);
    }

    @Override
    public void getAllList(int page) {

    }

    @Override
    public void getMyList(int page) {

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
