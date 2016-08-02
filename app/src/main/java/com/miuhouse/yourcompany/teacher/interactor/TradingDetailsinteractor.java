package com.miuhouse.yourcompany.teacher.interactor;

import com.android.volley.Response;
import com.miuhouse.yourcompany.teacher.application.App;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.interactor.interf.ITradingDetails;
import com.miuhouse.yourcompany.teacher.model.TradingDetailsBean;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kings on 7/21/2016.
 */
public class TradingDetailsinteractor implements ITradingDetails {
    @Override
    public void getTradingDetails(String teacherId, String withdrawId, Response.Listener listener, Response.ErrorListener errorListener) {
        String urlPath = Constants.URL_VALUE + "withdrawInfo";
        Map<String, Object> map = new HashMap<>();
        map.put("teacherId", App.getInstance().getTeacherId());
        map.put("withdrawId", withdrawId);
        VolleyManager.getInstance().sendGsonRequest(null, urlPath, map, SPUtils.getData(SPUtils.TOKEN, null), TradingDetailsBean.class, listener, errorListener);
    }
}
