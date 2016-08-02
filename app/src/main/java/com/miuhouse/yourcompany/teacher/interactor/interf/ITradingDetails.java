package com.miuhouse.yourcompany.teacher.interactor.interf;

import com.android.volley.Response;

/**
 * Created by kings on 7/21/2016.
 */
public interface ITradingDetails {
    void getTradingDetails(String teacherId,String withdrawId,Response.Listener listener,Response.ErrorListener errorListener);
 }
