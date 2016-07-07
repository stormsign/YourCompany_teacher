package com.miuhouse.yourcompany.teacher.view.ui.fragment.interf;

import com.miuhouse.yourcompany.teacher.interactor.OrderListInteractor;

/**
 * Created by khb on 2016/7/6.
 */
public interface IOrdersFragment {
//    void setupView();
    void refresh(OrderListInteractor.OrderListBean data);
    void goToOrderDetail();
    void changeList();
}
