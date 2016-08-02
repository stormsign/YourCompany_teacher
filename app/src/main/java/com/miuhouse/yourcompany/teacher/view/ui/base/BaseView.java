package com.miuhouse.yourcompany.teacher.view.ui.base;

/**
 * Created by khb on 2016/6/30.
 */
public interface BaseView {
    void showLoading(String msg);
    void showError(int type);
    void hideLoading();
    void hideError();
    void netError();
    //参数错误
    void onTokenExpired();
}
