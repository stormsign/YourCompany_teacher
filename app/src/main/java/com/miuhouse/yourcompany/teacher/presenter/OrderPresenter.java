package com.miuhouse.yourcompany.teacher.presenter;

import com.miuhouse.yourcompany.teacher.factory.FragmentFactory;
import com.miuhouse.yourcompany.teacher.interactor.OrderInteractor;
import com.miuhouse.yourcompany.teacher.interactor.interf.IOrderInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.presenter.interf.IOrderPresenter;
import com.miuhouse.yourcompany.teacher.view.ui.activity.OrderActivity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.OrdersFragment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by khb on 2016/8/3.
 */
public class OrderPresenter implements IOrderPresenter {
    private OrderActivity activity;
    private IOrderInteractor interactor;
    public OrderPresenter(OrderActivity activity) {
        this.activity = activity;
        interactor = new OrderInteractor();
    }

    @Override
    public void grabOrder(String teacherId, String orderId) {
        interactor.grabOrder(teacherId, orderId, new OnLoadCallBack() {
            @Override
            public void onPreLoad() {
                activity.showLoading(null);
            }

            @Override
            public void onLoadSuccess(Object data) {
                String resp = (String) data;
                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    int code = jsonObject.getInt("code");
                    if (code == 0){
                        ((OrdersFragment)FragmentFactory.getFragment(BaseFragment.ORDERS)).changeListToggle(false);
                        activity.finish();

                    }else{
//                        activity.showError(ViewOverrideManager.NO_NETWORK);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                activity.hideLoading();
            }

            @Override
            public void onLoadFailed(String msg) {

            }
        });
    }
}
