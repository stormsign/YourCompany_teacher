package com.miuhouse.yourcompany.teacher.interactor;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.application.App;
import com.miuhouse.yourcompany.teacher.db.AccountDBTask;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.interactor.interf.IOrderListInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by khb on 2016/7/7.
 */
public class OrderListInteractor implements IOrderListInteractor {

    public OrderListInteractor(){
    }

    @Override
    public void getAllOrders(int page, final OnLoadCallBack onLoadCallBack) {
        String url  = Constants.URL_VALUE + "orderCanMeet";
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", AccountDBTask.getAccount().getId());
        params.put("page", page);
        params.put("pageSize", 15);
        onLoadCallBack.onPreLoad();
        VolleyManager.getInstance()
                .sendGsonRequest(null, url, params, SPUtils.getData(SPUtils.TOKEN, null), OrderListBean.class,
                        new Response.Listener<OrderListBean>() {
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
    public void getMyOrders(int page, final OnLoadCallBack onLoadCallBack) {
        String url  = Constants.URL_VALUE + "myOrderGrab";
        Map<String, Object> params = new HashMap<>();
        if (AccountDBTask.getAccount() == null|| AccountDBTask.getAccount().getId() == null)
            return;
        params.put("teacherId", App.getInstance().getTeacherId());
//        params.put("orderStatus", )
        params.put("page", page);
        params.put("pageSize", 15);
        onLoadCallBack.onPreLoad();
        VolleyManager.getInstance()
                .sendGsonRequest(null, url, params, SPUtils.getData(SPUtils.TOKEN, null), OrderListBean.class,
                        new Response.Listener<OrderListBean>() {
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

    public class OrderListBean extends BaseBean{
        List<OrderEntity> orderList;
        long count;

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        public List<OrderEntity> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<OrderEntity> orderList) {
            this.orderList = orderList;
        }
    }


}
