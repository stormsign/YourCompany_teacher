package com.miuhouse.yourcompany.teacher.presenter.interf;

/**
 * Created by khb on 2016/7/7.
 */
public interface IOrderListPresenter {
//    void initViews();
    void getAllList(int page);
    void getMyList(int page);
    void grabOrder(String teacherId, String orderId);
}
