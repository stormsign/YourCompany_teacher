package com.miuhouse.yourcompany.teacher.presenter.interf;

/**
 * Created by khb on 2016/7/19.
 */
public interface IOrderManagePresenter {

    void getAOrders(String teacherId, int page);
    void getBOrders(String teacherId, int page);
    void getCOrders(String teacherId, int page);
    void getDOrders(String teacherId, int page);
    void beginClass(String teacherId, String orderId);
}
