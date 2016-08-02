package com.miuhouse.yourcompany.teacher.interactor;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.application.App;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.interactor.interf.IOrderInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by khb on 2016/7/21.
 */
public class OrderInteractor implements IOrderInteractor {
    public OrderInteractor() {
    }


    @Override
    public void getInfo(String teacherId, String orderInfoId, final OnLoadCallBack onLoadCallBack) {
        onLoadCallBack.onPreLoad();
        String url = Constants.URL_VALUE + "orderInfo";
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", App.getInstance().getTeacherId());
        params.put("orderInfoId", orderInfoId);
        VolleyManager.getInstance().sendGsonRequest(null, url, params,
                SPUtils.getData(SPUtils.TOKEN, null),
                OrderListBean.class, new Response.Listener<OrderListBean>() {
                    @Override
                    public void onResponse(OrderListBean response) {
                        onLoadCallBack.onLoadSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onLoadCallBack.onLoadFailed(error.toString());
                    }
                });
    }

    @Override
    public void beginClass(String teacherId, String orderInfoId, final OnLoadCallBack onLoadCallBack) {
        onLoadCallBack.onPreLoad();
        String url = Constants.URL_VALUE + "classBegin";
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", teacherId);
        params.put("orderInfoId", orderInfoId);
        VolleyManager.getInstance().sendStringRequest(null, url, params,
                SPUtils.getData(SPUtils.TOKEN, null),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onLoadCallBack.onLoadSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onLoadCallBack.onLoadFailed(error.toString());
                    }
                });
    }

    @Override
    public void grabOrder(String teacherId, String orderInfoId, final OnLoadCallBack onLoadCallBack) {
        if (null != onLoadCallBack) {
            onLoadCallBack.onPreLoad();
        }
        String url = Constants.URL_VALUE + "orderGrab";
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", teacherId);
        params.put("orderId", orderInfoId);
        VolleyManager.getInstance().sendStringRequest(null, url, params,
                SPUtils.getData(SPUtils.TOKEN, null),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        L.i("response:" + response);
                        if (null != onLoadCallBack){
                            onLoadCallBack.onLoadSuccess(response);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (null != onLoadCallBack){
                            onLoadCallBack.onLoadFailed(error.toString());
                        }
                    }
                });
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
