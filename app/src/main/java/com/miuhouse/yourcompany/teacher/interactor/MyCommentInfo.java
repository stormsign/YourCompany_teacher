package com.miuhouse.yourcompany.teacher.interactor;

import com.android.volley.Response;
import com.miuhouse.yourcompany.teacher.application.App;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.interactor.interf.IMyComment;
import com.miuhouse.yourcompany.teacher.model.MyEvaluate;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kings on 7/26/2016.
 */
public class MyCommentInfo implements IMyComment {
    @Override
    public void getMyComment(String teacherId, int page, int pageSize, Response.Listener listener, Response.ErrorListener errorListener) {
        String urlPaht = Constants.URL_VALUE + "myEvaluate";
        Map<String, Object> map = new HashMap<>();
        map.put("teacherId", App.getInstance().getTeacherId());
        map.put("page", page);
        map.put("pageSize", pageSize);
        VolleyManager.getInstance().sendGsonRequest(null, urlPaht, map, SPUtils.getData(SPUtils.TOKEN, null), MyEvaluate.class, listener, errorListener);
    }
}
