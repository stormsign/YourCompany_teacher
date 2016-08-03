package com.miuhouse.yourcompany.teacher.presenter;

import com.miuhouse.yourcompany.teacher.factory.FragmentFactory;
import com.miuhouse.yourcompany.teacher.interactor.OrderInteractor;
import com.miuhouse.yourcompany.teacher.interactor.OrderListInteractor;
import com.miuhouse.yourcompany.teacher.interactor.interf.IOrderListInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.presenter.interf.IOrderListPresenter;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IOrdersListFragment;
import com.miuhouse.yourcompany.teacher.view.widget.ViewOverrideManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by khb on 2016/7/7.
 */
public class OrderListPresenter implements IOrderListPresenter {

    private IOrdersListFragment iOrdersFragment;
    private IOrderListInteractor iOrderListInteractor;
    private OrderInteractor orderInteractor;

    public OrderListPresenter(IOrdersListFragment iOrdersFragment){
        this.iOrdersFragment = iOrdersFragment;
        iOrderListInteractor = new OrderListInteractor();
        orderInteractor = new OrderInteractor();
    }

    @Override
    public void getAllList(final int page) {
        iOrderListInteractor.getAllOrders(page, new OnLoadCallBack() {
            @Override
            public void onPreLoad() {
                iOrdersFragment.showLoading(null);
            }

            @Override
            public void onLoadSuccess(Object data) {
                iOrdersFragment.hideLoading();
                OrderListInteractor.OrderListBean bean = (OrderListInteractor.OrderListBean) data;
                if (null != bean) {
                    if (bean.getCode() == 0){
                        if (null != bean.getOrderList()
                                && bean.getOrderList().size()>0) {
                            iOrdersFragment.refresh(bean);
                        }else {
                            if (page > 1){
                                L.i("没有更多");
                            }else {
                                iOrdersFragment.showError(ViewOverrideManager.NO_STUDENT);
                            }
                        }
                    }else if (bean.getCode() == 1) {
                        iOrdersFragment.onTokenExpired();
                    }else if (bean.getCode() == 2){
                        iOrdersFragment.showError(-1);
                    }
                }else {
                    iOrdersFragment.showError(ViewOverrideManager.NO_NETWORK);
                }
            }

            @Override
            public void onLoadFailed(String msg) {
                iOrdersFragment.hideLoading();
                iOrdersFragment.showError(ViewOverrideManager.NO_NETWORK);
            }
        });
    }

    @Override
    public void getMyList(final int page) {
        iOrderListInteractor.getMyOrders(page, new OnLoadCallBack() {
            @Override
            public void onPreLoad() {
                iOrdersFragment.showLoading(null);
            }

            @Override
            public void onLoadSuccess(Object data) {
                iOrdersFragment.hideLoading();
                OrderListInteractor.OrderListBean bean = (OrderListInteractor.OrderListBean) data;
                if (null != bean) {
                    if (bean.getCode() == 0){
                        if (null != bean.getOrderList()
                                && bean.getOrderList().size()>0) {
                            iOrdersFragment.refresh(bean);
                        }else {
                            if (page > 1){
                                L.i("没有更多");
                            }else {
                                iOrdersFragment.showError(ViewOverrideManager.NO_STUDENT);
                            }
                        }
                    }else if (bean.getCode() == 1) {
                        iOrdersFragment.onTokenExpired();
                    }else if (bean.getCode() == 2){
                        iOrdersFragment.showError(-1);
                    }
                }else {
                    iOrdersFragment.showError(ViewOverrideManager.NO_NETWORK);
                }
            }

            @Override
            public void onLoadFailed(String msg) {
                iOrdersFragment.hideLoading();
                iOrdersFragment.showError(ViewOverrideManager.NO_NETWORK);
            }
        });
    }

    @Override
    public void grabOrder(String teacherId, String orderId) {
        orderInteractor.grabOrder(teacherId, orderId, new OnLoadCallBack() {
            @Override
            public void onPreLoad() {
                iOrdersFragment.showSecondLoading();
            }

            @Override
            public void onLoadSuccess(Object data) {
                String resp = (String) data;
                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    int code = jsonObject.getInt("code");
                    if (code == 0){
                        FragmentFactory.getFragment(BaseFragment.MYORDERS).onResume();
                        getAllList(1);
                        iOrdersFragment.changeListToggle();
                    }else if (code == 3){

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                iOrdersFragment.hideLoading();
            }

            @Override
            public void onLoadFailed(String msg) {
                iOrdersFragment.hideLoading();
                iOrdersFragment.showError(ViewOverrideManager.NO_NETWORK);
            }
        });
    }

//    @Override
//    public void onPreLoad() {
////        iOrdersFragment.hideError();
//        iOrdersFragment.showLoading(null);
//    }
//
//    @Override
//    public void onLoadSuccess(Object data) {
//        iOrdersFragment.hideLoading();
//        OrderListInteractor.OrderListBean bean = (OrderListInteractor.OrderListBean) data;
//        if (null != bean) {
//            if (bean.getCode() == 0){
//                if (null != bean.getOrderList()
//                        && bean.getOrderList().size()>0) {
//                    iOrdersFragment.refresh(bean);
//                }else {
//                    iOrdersFragment.showError(ViewOverrideManager.NO_STUDENT);
//                }
//            }else if (bean.getCode() == 1) {
//                iOrdersFragment.onTokenExpired();
//            }else if (bean.getCode() == 2){
//                iOrdersFragment.showError(-1);
//            }
//        }else {
//            iOrdersFragment.showError(ViewOverrideManager.NO_NETWORK);
//        }
//    }
//
//    @Override
//    public void onLoadFailed(String msg) {
//        L.i("error : " + msg);
////        iOrdersFragment.showError(msg);
//        iOrdersFragment.hideLoading();
//        iOrdersFragment.showError(ViewOverrideManager.NO_NETWORK);
//    }
}
