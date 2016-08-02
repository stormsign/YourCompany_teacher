package com.miuhouse.yourcompany.teacher.model;

/**
 * Created by kings on 7/22/2016.
 */
public class WithdrawAccountBean extends BaseBean {
    private PayAccountBean account;

    public PayAccountBean getAccount() {
        return account;
    }

    public void setAccount(PayAccountBean account) {
        this.account = account;
    }
}
