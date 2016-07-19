package com.miuhouse.yourcompany.teacher.interactor;

import com.miuhouse.yourcompany.teacher.interactor.interf.IOrderManageInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;

/**
 * Created by khb on 2016/7/19.
 */
public class OrderManageInteractor implements IOrderManageInteractor {

    private OnLoadCallBack onLoadCallBack;

    public OrderManageInteractor(OnLoadCallBack onLoadCallBack) {
        this.onLoadCallBack = onLoadCallBack;
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
}
