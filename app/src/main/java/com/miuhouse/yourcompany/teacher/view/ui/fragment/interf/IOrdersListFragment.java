package com.miuhouse.yourcompany.teacher.view.ui.fragment.interf;

import com.miuhouse.yourcompany.teacher.interactor.OrderListInteractor;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseView;

/**
 * Created by khb on 2016/7/6.
 */
public interface IOrdersListFragment extends BaseView{
//    void setupView();
    void refresh(OrderListInteractor.OrderListBean data);
//    void goToOrderDetail(OrderEntity orderEntity);
//    void changeListToggle(boolean isAllList);
    void remove();
}
