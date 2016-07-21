package com.miuhouse.yourcompany.teacher.interactor.interf;

import com.android.volley.Response;

/**
 * Created by kings on 7/20/2016.
 */
public interface ITradingRecordInteractor {
    void getTradingRecordList(String teacherId,int page,int pageSize,Response.Listener listener,Response.ErrorListener errorListener);
}
