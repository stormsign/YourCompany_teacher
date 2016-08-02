package com.miuhouse.yourcompany.teacher.interactor;

import com.android.volley.Response;
import com.miuhouse.yourcompany.teacher.application.App;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.interactor.interf.ITradingRecordInteractor;
import com.miuhouse.yourcompany.teacher.model.TradingRecordBean;
import com.miuhouse.yourcompany.teacher.model.TradingRecordListBean;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kings on 7/20/2016.
 */
public class TradingRecordInfo implements ITradingRecordInteractor {
    @Override
    public void getTradingRecordList(String teacherId, int page, int pageSize, Response.Listener listener, Response.ErrorListener errorListener) {
        String urlPath = Constants.URL_VALUE + "withdrawList";
        Map<String, Object> map = new HashMap<>();
        map.put("teacherId", App.getInstance().getTeacherId());
        map.put("page", page);
        map.put("pageSize", pageSize);
        VolleyManager.getInstance().sendGsonRequest(null, urlPath, map, SPUtils.getData(SPUtils.TOKEN, null), TradingRecordListBean.class, listener, errorListener);
    }
}
