package com.miuhouse.yourcompany.teacher.presenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.interactor.TradingRecordInfo;
import com.miuhouse.yourcompany.teacher.interactor.interf.ITradingRecordInteractor;
import com.miuhouse.yourcompany.teacher.model.TradingRecordBean;
import com.miuhouse.yourcompany.teacher.model.TradingRecordListBean;
import com.miuhouse.yourcompany.teacher.presenter.interf.ITradingRecordPresenter;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.ITradingRecordView;

/**
 * Created by kings on 7/20/2016.
 */
public class TradingRecordPresenter implements ITradingRecordPresenter {
    private ITradingRecordInteractor tradingRecordInteractor;
    private ITradingRecordView tradingRecordView;

    public TradingRecordPresenter(ITradingRecordView tradingRecordView) {
        this.tradingRecordView = tradingRecordView;
        tradingRecordInteractor = new TradingRecordInfo();
    }

    @Override
    public void getTradingRecordList(String teacherId, int page, int pageSize) {
        tradingRecordView.showLoading("正在加载中...");
        tradingRecordInteractor.getTradingRecordList(teacherId, page, pageSize, new Response.Listener<TradingRecordListBean>() {
            @Override
            public void onResponse(TradingRecordListBean response) {
                if (response.getCode() == 1)
                    tradingRecordView.onTokenExpired();
                else {
                    tradingRecordView.getTradingRecord(response);
                    tradingRecordView.hideLoading();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tradingRecordView.netError();
            }
        });
    }
}
