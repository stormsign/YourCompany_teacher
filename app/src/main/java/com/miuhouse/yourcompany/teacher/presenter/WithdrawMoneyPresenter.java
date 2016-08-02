package com.miuhouse.yourcompany.teacher.presenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.interactor.WithdrawMoneyInfo;
import com.miuhouse.yourcompany.teacher.interactor.interf.IWithdrawMoney;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.presenter.interf.IWithdrawMoneyPresenter;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IWithdrawMoneyView;

import java.math.BigDecimal;

/**
 * Created by kings on 7/21/2016.
 */
public class WithdrawMoneyPresenter implements IWithdrawMoneyPresenter {

    private IWithdrawMoney withdrawMoneyInteractor;
    private IWithdrawMoneyView withdrawMoneyView;

    public WithdrawMoneyPresenter(IWithdrawMoneyView withdrawMoneyView) {
        withdrawMoneyInteractor = new WithdrawMoneyInfo();
        this.withdrawMoneyView = withdrawMoneyView;
    }

    @Override
    public void getWithDrawMoney(String teacherId, String account, String accountName, BigDecimal amount, String accountType) {
        withdrawMoneyInteractor.getWithdrawMoney(teacherId, account, accountName, amount, accountType, new Response.Listener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (response.getCode() == 1) {
                    withdrawMoneyView.onTokenExpired();
                } else {
                    withdrawMoneyView.getResult(response);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
