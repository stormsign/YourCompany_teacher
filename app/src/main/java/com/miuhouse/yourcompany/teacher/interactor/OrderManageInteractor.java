package com.miuhouse.yourcompany.teacher.interactor;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.interactor.interf.IOrderManageInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by khb on 2016/7/19.
 */
public class OrderManageInteractor implements IOrderManageInteractor, Response.Listener<OrderManageInteractor.OrderListBean>, Response.ErrorListener {

    private OnLoadCallBack onLoadCallBack;

    public OrderManageInteractor(OnLoadCallBack onLoadCallBack) {
        this.onLoadCallBack = onLoadCallBack;
    }

    @Override
    public void getAOrders(String teacherId, int page) {
        onLoadCallBack.onPreLoad();
        List<String> statusList = new ArrayList<>();
        statusList.add("6");    //完成
        statusList.add("7");    //投诉
        statusList.add("8");    //待退款
        statusList.add("0");    //取消
        String url = Constants.URL_VALUE + "orderList";
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", teacherId);
        params.put("orderStatus", statusList);
        params.put("page", page);
        params.put("pageSize", 15);
        VolleyManager.getInstance().sendGsonRequest(null, url, params,
                "6eca806dffed65f70f6d50a3b435069b", OrderListBean.class,
                this, this);
    }

    @Override
    public void getBOrders(String teacherId, int page) {
        onLoadCallBack.onPreLoad();
        List<String> statusList = new ArrayList<>();
        statusList.add("3");    //待上课
        String url = Constants.URL_VALUE + "orderList";
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", teacherId);
        params.put("orderStatus", statusList);
        params.put("page", page);
        params.put("pageSize", 15);
        VolleyManager.getInstance().sendGsonRequest(null, url, params,
                "6eca806dffed65f70f6d50a3b435069b", OrderListBean.class,
                this, this);
    }

    @Override
    public void getCOrders(String teacherId, int page) {
        onLoadCallBack.onPreLoad();
        List<String> statusList = new ArrayList<>();
        statusList.add("4");    //进行中
        String url = Constants.URL_VALUE + "orderList";
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", teacherId);
        params.put("orderStatus", statusList);
        params.put("page", page);
        params.put("pageSize", 15);
        VolleyManager.getInstance().sendGsonRequest(null, url, params,
                "6eca806dffed65f70f6d50a3b435069b", OrderListBean.class,
                this, this);
    }

    @Override
    public void getDOrders(String teacherId, int page) {
        onLoadCallBack.onPreLoad();
        List<String> statusList = new ArrayList<>();
        statusList.add("5");    //待评价
        String url = Constants.URL_VALUE + "orderList";
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", teacherId);
        params.put("orderStatus", statusList);
        params.put("page", page);
        params.put("pageSize", 15);
        VolleyManager.getInstance().sendGsonRequest(null, url, params,
                "6eca806dffed65f70f6d50a3b435069b", OrderListBean.class,
                this, this);
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
        List<OrderEntity> orderList;

        public List<OrderEntity> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<OrderEntity> orderList) {
            this.orderList = orderList;
        }
    }

}
