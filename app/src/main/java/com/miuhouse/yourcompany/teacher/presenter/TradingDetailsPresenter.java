package com.miuhouse.yourcompany.teacher.presenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.interactor.TradingDetailsinteractor;
import com.miuhouse.yourcompany.teacher.interactor.UserInformationInfo;
import com.miuhouse.yourcompany.teacher.interactor.interf.ITradingDetails;
import com.miuhouse.yourcompany.teacher.interactor.interf.IUserInformation;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.TradingDetailsBean;
import com.miuhouse.yourcompany.teacher.model.User;
import com.miuhouse.yourcompany.teacher.presenter.interf.ITradingDetailsPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IUserInformationPresenter;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.ITradingDetailsView;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IUserInformationView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kings on 7/13/2016.
 */
public class TradingDetailsPresenter implements ITradingDetailsPresenter {
    private ITradingDetailsView tradingDetailsView;
    private ITradingDetails tradingDetails;

    public TradingDetailsPresenter(ITradingDetailsView tradingDetailsView) {
        this.tradingDetailsView = tradingDetailsView;
        tradingDetails = new TradingDetailsinteractor();
    }


    @Override
    public void getTradingDetails(String teacherId, String withdrawId) {
        tradingDetails.getTradingDetails(teacherId, withdrawId, new Response.Listener<TradingDetailsBean>() {
            @Override
            public void onResponse(TradingDetailsBean response) {
                if (response.getCode() == 1) {
                    tradingDetailsView.onTokenExpired();
                } else {
                    tradingDetailsView.getTradingDetails(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tradingDetailsView.netError();
            }
        });
    }
}
