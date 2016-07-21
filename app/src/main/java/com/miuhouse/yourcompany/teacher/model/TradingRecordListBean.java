package com.miuhouse.yourcompany.teacher.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典表
 * Created by kings on 7/19/2016.
 */
public class TradingRecordListBean extends BaseBean {

    public List<TradingRecordBean> withdraw = new ArrayList<>();

    public List<TradingRecordBean> getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(List<TradingRecordBean> withdraw) {
        this.withdraw = withdraw;
    }
}
