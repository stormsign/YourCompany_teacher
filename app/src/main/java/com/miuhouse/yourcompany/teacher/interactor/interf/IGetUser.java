package com.miuhouse.yourcompany.teacher.interactor.interf;

import com.android.volley.Response;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.User;

/**
 * Created by kings on 7/1/2016.
 */
public interface IGetUser {
    void getUserInfo(String name,String password,Response.Listener<User> listener,Response.ErrorListener errorListener);
    void getRegistInfo(String name,String password,Response.Listener<BaseBean> listener,Response.ErrorListener errorListener);
}
