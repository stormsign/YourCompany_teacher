package com.miuhouse.yourcompany.teacher.interactor.interf;

import com.android.volley.Response;
import com.miuhouse.yourcompany.teacher.model.MyEvaluate;

/**
 * Created by kings on 7/26/2016.
 */
public interface IMyComment {
     void getMyComment(String teacherId,int page,int pageSize,Response.Listener<MyEvaluate> listener,Response.ErrorListener errorListener);
}
