package com.miuhouse.yourcompany.teacher.interactor.interf;

/**
 * Created by khb on 2016/7/19.
 */
public interface IOrderManageInteractor {

    void getAOrders(String teacherId, int page);
    void getBOrders(String teacherId, int page);
    void getCOrders(String teacherId, int page);
    void getDOrders(String teacherId, int page);

}
