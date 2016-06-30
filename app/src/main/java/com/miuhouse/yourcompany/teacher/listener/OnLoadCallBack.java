package com.miuhouse.yourcompany.teacher.listener;

/**
 * Created by khb on 2016/5/10.
 */
public interface OnLoadCallBack<T> {
    void onPreLoad(String msg);
    void onLoadSuccess(T data);
    void onLoadFailed(String msg);
}
