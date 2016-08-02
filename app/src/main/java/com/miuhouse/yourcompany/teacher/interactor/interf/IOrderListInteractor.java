package com.miuhouse.yourcompany.teacher.interactor.interf;

import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;

/**
 * Created by khb on 2016/7/7.
 */
public interface IOrderListInteractor {

    void getAllOrders(int page, OnLoadCallBack onLoadCallBack);
    void getMyOrders(int page, OnLoadCallBack onLoadCallBack);
}
