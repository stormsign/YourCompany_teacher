package com.miuhouse.yourcompany.teacher.view.ui.fragment.interf;

import com.miuhouse.yourcompany.teacher.interactor.OrderManageInteractor;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseView;

/**
 * Created by khb on 2016/7/19.
 */
public interface IOrderManageFragment extends BaseView{

    void refresh(OrderManageInteractor.OrderListBean bean);

}
