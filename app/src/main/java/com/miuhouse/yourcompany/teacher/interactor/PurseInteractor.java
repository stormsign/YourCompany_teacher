package com.miuhouse.yourcompany.teacher.interactor;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.model.TradingRecordListBean;
import com.miuhouse.yourcompany.teacher.presenter.interf.IPurseInteractor;
import com.miuhouse.yourcompany.teacher.presenter.interf.IPursePresenter;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by khb on 2016/7/26.
 */
public class PurseInteractor implements IPurseInteractor {
    private IPursePresenter presenter;
//    private OnLoadCallBack onLoadCallBack;
    public PurseInteractor(IPursePresenter pursePresenter) {
        this.presenter = pursePresenter;
    }

    @Override
    public void getPurseMsgs(String teacherId, int page, final OnLoadCallBack onLoadCallBack) {
        onLoadCallBack.onPreLoad();
        String url = Constants.URL_VALUE + "withdrawList";
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", teacherId);
        params.put("page", page);
        params.put("pageSize", 10);
        VolleyManager.getInstance().sendGsonRequest(null, url, params,
                SPUtils.getData(SPUtils.TOKEN, null),
                TradingRecordListBean.class,
                new Response.Listener<TradingRecordListBean>() {
                    @Override
                    public void onResponse(TradingRecordListBean tradingRecordListBean) {
                        onLoadCallBack.onLoadSuccess(tradingRecordListBean);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        onLoadCallBack.onLoadFailed(volleyError.toString());
                    }
                });

    }

}
