package com.miuhouse.yourcompany.teacher.model;

import java.io.Serializable;

/**
 * Created by kings on 7/8/2016.
 */
public class TeacherInfo implements Serializable {
    private String id;
    private String tName;
    private String mobile;
    private String token;
    private long tokenTime;
    private double balance; //帐号余额

    public void setId(String id) {
        this.id = id;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenTime(long tokenTime) {
        this.tokenTime = tokenTime;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public String gettName() {
        return tName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getToken() {
        return token;
    }

    public long getTokenTime() {
        return tokenTime;
    }

    public double getBalance() {
        return balance;
    }
}
