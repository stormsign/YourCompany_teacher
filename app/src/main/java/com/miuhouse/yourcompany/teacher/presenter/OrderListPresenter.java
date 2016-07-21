package com.miuhouse.yourcompany.teacher.presenter;

import com.miuhouse.yourcompany.teacher.interactor.OrderListInteractor;
import com.miuhouse.yourcompany.teacher.interactor.interf.IOrderListInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.presenter.interf.IOrderListPresenter;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IOrdersListFragment;

/**
 * Created by khb on 2016/7/7.
 */
public class OrderListPresenter implements IOrderListPresenter, OnLoadCallBack {

    private IOrdersListFragment iOrdersFragment;
    private IOrderListInteractor iOrderListInteractor;

    public OrderListPresenter(IOrdersListFragment iOrdersFragment){
        this.iOrdersFragment = iOrdersFragment;
        iOrderListInteractor = new OrderListInteractor(this);
    }

    @Override
    public void getAllList(int page) {
        iOrderListInteractor.getAllOrders(page);
    }

    @Override
    public void getMyList(int page) {
        iOrderListInteractor.getMyOrders(page);
    }


    @Override
    public void onPreLoad() {
//        iOrdersFragment.hideError();
        iOrdersFragment.showLoading(null);
    }

    @Override
    public void onLoadSuccess(Object data) {
        iOrdersFragment.hideLoading();
        OrderListInteractor.OrderListBean bean = (OrderListInteractor.OrderListBean) data;
        if (null != bean.getOrderList()
                && bean.getOrderList().size()>0) {
            iOrdersFragment.refresh(bean);
        }else {
            iOrdersFragment.showError("没有数据");
        }
    }

    @Override
    public void onLoadFailed(String msg) {
        L.i("error : "+msg);
        iOrdersFragment.showError(msg);
    }
}
