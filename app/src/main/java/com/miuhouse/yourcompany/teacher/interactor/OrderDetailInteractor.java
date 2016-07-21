package com.miuhouse.yourcompany.teacher.interactor;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.interactor.interf.IOrderDetailInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.utils.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by khb on 2016/7/21.
 */
public class OrderDetailInteractor implements IOrderDetailInteractor, Response.Listener<OrderDetailInteractor.OrderListBean>, Response.ErrorListener {
    private OnLoadCallBack onLoadCallBack;

    public OrderDetailInteractor(OnLoadCallBack onLoadCallBack) {
        this.onLoadCallBack = onLoadCallBack;
    }


    @Override
    public void getInfo(String teacherId, String orderInfoId) {
        onLoadCallBack.onPreLoad();
        String url = Constants.URL_VALUE + "ordreInfo";
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", teacherId);
        params.put("orderInfoId", orderInfoId);
        VolleyManager.getInstance().sendGsonRequest(null, url, params,
                "6eca806dffed65f70f6d50a3b435069b",
                OrderListBean.class, this, this);
    }

    @Override
    public void onResponse(OrderListBean response) {
        onLoadCallBack.onLoadSuccess(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        onLoadCallBack.onLoadFailed(error.toString());
    }

    public class OrderListBean extends BaseBean{
        OrderEntity orderList;

        public OrderEntity getOrderList() {
            return orderList;
        }

        public void setOrderList(OrderEntity orderList) {
            this.orderList = orderList;
        }
    }

}