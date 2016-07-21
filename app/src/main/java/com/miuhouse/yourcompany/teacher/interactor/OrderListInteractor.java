package com.miuhouse.yourcompany.teacher.interactor;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.interactor.interf.IOrderListInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by khb on 2016/7/7.
 */
public class OrderListInteractor implements IOrderListInteractor, Response.Listener<OrderListInteractor.OrderListBean>, Response.ErrorListener {

    private OnLoadCallBack onLoadCallBack;

    public OrderListInteractor(OnLoadCallBack mOnLoadCallBack){

        onLoadCallBack = mOnLoadCallBack;
    }

    @Override
    public void getAllOrders(int page) {
        String url  = Constants.URL_VALUE + "orderCanMeet";
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", "4028b88155c4dd070155c4dd8a340000");
        params.put("page", page);
        params.put("pageSize", 15);
        onLoadCallBack.onPreLoad();
        VolleyManager.getInstance()
                .sendGsonRequest(null, url, params, "6eca806dffed65f70f6d50a3b435069b", OrderListBean.class, this, this);

    }

    @Override
    public void getMyOrders(int page) {
        String url  = Constants.URL_VALUE + "myOrderGrab";
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", "4028b88155c4dd070155c4dd8a340000");
//        params.put("orderStatus", )
        params.put("page", page);
        params.put("pageSize", 15);
        onLoadCallBack.onPreLoad();
        VolleyManager.getInstance()
                .sendGsonRequest(null, url, params, "6eca806dffed65f70f6d50a3b435069b", OrderListBean.class, this, this);

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
