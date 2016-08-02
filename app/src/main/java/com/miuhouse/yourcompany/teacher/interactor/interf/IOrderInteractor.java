package com.miuhouse.yourcompany.teacher.interactor.interf;

import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;

/**
 * Created by khb on 2016/7/21.
 */
public interface IOrderInteractor {

    void getInfo(String teacherId, String orderInfoId, OnLoadCallBack onLoadCallBack);
    void beginClass(String teacherId, String orderInfoId, OnLoadCallBack onLoadCallBack);
    void grabOrder(String teacherId, String orderInfoId, OnLoadCallBack onLoadCallBack);
}
