package com.miuhouse.yourcompany.teacher.interactor.interf;

import com.android.volley.Response;

import java.math.BigDecimal;

/**
 * Created by kings on 7/21/2016.
 */
public interface IWithdrawMoney {
    void getWithdrawMoney(String teacherId,String account,String accountName, BigDecimal amount,String accountType, Response.Listener listener, Response.ErrorListener errorListener);
}
