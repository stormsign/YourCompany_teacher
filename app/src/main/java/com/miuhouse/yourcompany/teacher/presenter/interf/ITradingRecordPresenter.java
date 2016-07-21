package com.miuhouse.yourcompany.teacher.presenter.interf;

import com.android.volley.Response;

/**
 * Created by kings on 7/20/2016.
 */
public interface ITradingRecordPresenter {
    void getTradingRecordList(String teacherId,int page,int pageSize);
}
