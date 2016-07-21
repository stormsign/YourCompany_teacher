package com.miuhouse.yourcompany.teacher.interactor;

import com.android.volley.Response;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.interactor.interf.ITradingRecordInteractor;
import com.miuhouse.yourcompany.teacher.model.TradingRecordBean;
import com.miuhouse.yourcompany.teacher.model.TradingRecordListBean;
import com.miuhouse.yourcompany.teacher.utils.Constants;

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
        map.put("teacherId", "4028b88155c4dd070155c4dd8a340000");
        map.put("page", page);
        map.put("pageSize", pageSize);
        VolleyManager.getInstance().sendGsonRequest(null, urlPath, map, "6eca806dffed65f70f6d50a3b435069b", TradingRecordListBean.class, listener, errorListener);
    }
}
