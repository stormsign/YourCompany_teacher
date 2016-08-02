package com.miuhouse.yourcompany.teacher.model;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kings on 7/22/2016.
 */
public class PayAccountListBean extends BaseBean {

    private List<PayAccountBean> accounts = new ArrayList<>();

    public List<PayAccountBean> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<PayAccountBean> accounts) {
        this.accounts = accounts;
    }
}
