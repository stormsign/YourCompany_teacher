package com.miuhouse.yourcompany.teacher.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by kings on 7/21/2016.
 */
public class RecordBean implements Serializable {

    private String pName; //家长姓名
    private String headUrl; //头像
    private BigDecimal amount; // 交易金额
    private long date;
    private String type;

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
