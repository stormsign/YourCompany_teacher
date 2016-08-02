package com.miuhouse.yourcompany.teacher.interactor;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.application.App;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.interactor.interf.IOrderManageInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by khb on 2016/7/19.
 */
public class OrderManageInteractor implements IOrderManageInteractor, Response.Listener<OrderManageInteractor.OrderListBean>, Response.ErrorListener {

    private OnLoadCallBack onLoadCallBack;

    public OrderManageInteractor(){

    }

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
        params.put("teacherId", App.getInstance().getTeacherId());
        params.put("orderStatus", statusList);
        params.put("page", page);
        params.put("pageSize", 15);
        VolleyManager.getInstance().sendGsonRequest(null, url, params,
                SPUtils.getData(SPUtils.TOKEN, null), OrderListBean.class,
                this, this);
    }

    @Override
    public void getBOrders(String teacherId, int page) {
        onLoadCallBack.onPreLoad();
        List<String> statusList = new ArrayList<>();
        statusList.add("3");    //待上课
        String url = Constants.URL_VALUE + "orderList";
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", App.getInstance().getTeacherId());
        params.put("orderStatus", statusList);
        params.put("page", page);
        params.put("pageSize", 15);
        VolleyManager.getInstance().sendGsonRequest(null, url, params,
                SPUtils.getData(SPUtils.TOKEN, null), OrderListBean.class,
                this, this);
    }

    @Override
    public void getCOrders(String teacherId, int page) {
        onLoadCallBack.onPreLoad();
        List<String> statusList = new ArrayList<>();
        statusList.add("4");    //进行中
        String url = Constants.URL_VALUE + "orderList";
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", App.getInstance().getTeacherId());
        params.put("orderStatus", statusList);
        params.put("page", page);
        params.put("pageSize", 15);
        VolleyManager.getInstance().sendGsonRequest(null, url, params,
                SPUtils.getData(SPUtils.TOKEN, null), OrderListBean.class,
                this, this);
    }

    @Override
    public void getDOrders(String teacherId, int page) {
        onLoadCallBack.onPreLoad();
        List<String> statusList = new ArrayList<>();
        statusList.add("5");    //待评价
        String url = Constants.URL_VALUE + "orderList";
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", App.getInstance().getTeacherId());
        params.put("orderStatus", statusList);
        params.put("page", page);
        params.put("pageSize", 15);
        VolleyManager.getInstance().sendGsonRequest(null, url, params,
                SPUtils.getData(SPUtils.TOKEN, null), OrderListBean.class,
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

    @Override
    public void beginClass(String teacherId, final String orderInfoId) {
        onLoadCallBack.onPreLoad();
        String url = Constants.URL_VALUE + "classBegin";
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", App.getInstance().getTeacherId());
        params.put("orderInfoId", orderInfoId);
        VolleyManager.getInstance().sendStringRequest(null, url, params,
                SPUtils.getData(SPUtils.TOKEN, null),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            jsonObject.put("orderid", orderInfoId);
                            response = jsonObject.toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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

        public List<OrderEntity> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<OrderEntity> orderList) {
            this.orderList = orderList;
        }
    }

}
