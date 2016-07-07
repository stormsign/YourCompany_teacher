package com.miuhouse.yourcompany.teacher.interactor;

import com.miuhouse.yourcompany.teacher.interactor.interf.IOrderListInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.model.BaseBean;

/**
 * Created by khb on 2016/7/7.
 */
public class OrderListInteractor implements IOrderListInteractor {

    private OnLoadCallBack onLoadCallBack;

    public OrderListInteractor(OnLoadCallBack mOnLoadCallBack){
        onLoadCallBack = mOnLoadCallBack;
    }

    @Override
    public void getAllOrders(int page) {

    }

    @Override
    public void getMyOrders(int page) {

    }

    public class OrderListBean extends BaseBean{

    }

}
