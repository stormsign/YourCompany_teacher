package com.miuhouse.yourcompany.teacher.utils;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.application.App;
import com.miuhouse.yourcompany.teacher.db.DictDBTask;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.DictListBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kings on 7/19/2016.
 */
public class DictManager {

    private DictManager() {

    }

    private static class DictManagerBuilder {
        private static DictManager instance = new DictManager();
    }

    public static DictManager getInstance(Context context) {
        return DictManagerBuilder.instance;
    }

    public void init() {
        String url = Constants.URL_VALUE + "dict";
        Map<String, Object> map = new HashMap<>();
        map.put("teacherId", App.getInstance().getTeacherId());
        VolleyManager.getInstance().sendGsonRequest(null, url, map, SPUtils.getData(SPUtils.TOKEN, null), DictListBean.class, new Response.Listener<DictListBean>() {
            @Override
            public void onResponse(DictListBean response) {
                L.i("TAG", "response=" + response.getMsg());
                DictDBTask.add(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //初始化失败，继续使用原先保存的
            }
        });

    }
}
