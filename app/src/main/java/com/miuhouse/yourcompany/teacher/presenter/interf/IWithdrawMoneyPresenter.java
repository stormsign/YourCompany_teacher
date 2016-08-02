package com.miuhouse.yourcompany.teacher.presenter.interf;

import java.math.BigDecimal;

/**
 * Created by kings on 7/21/2016.
 */
public interface IWithdrawMoneyPresenter {
    void getWithDrawMoney(String teacherId,String account ,String accountName,BigDecimal amount,String accountType);
}
