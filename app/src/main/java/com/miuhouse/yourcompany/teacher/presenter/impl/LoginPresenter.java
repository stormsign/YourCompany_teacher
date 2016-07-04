package com.miuhouse.yourcompany.teacher.presenter.impl;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.interactor.GetUserInfo;
import com.miuhouse.yourcompany.teacher.interactor.interf.IGetUser;
import com.miuhouse.yourcompany.teacher.model.User;
import com.miuhouse.yourcompany.teacher.presenter.ILoginPresenter;
import com.miuhouse.yourcompany.teacher.view.ui.activity.view.ILoginView;

/**
 * 程序逻辑在Presenter里实现
 * Created by kings on 7/1/2016.
 */
public class LoginPresenter implements ILoginPresenter {
    private ILoginView iLoginView;
    private IGetUser getUser;


    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
         getUser = new GetUserInfo();
    }

    @Override
    public void clear() {

    }

    //登录
    @Override
    public void doLogin(String name, String passwd) {
        getUser.getUserInfo(name, passwd, new Response.Listener<User>() {
            @Override
            public void onResponse(User response) {
                Log.i("TAG", "user=" + response.getName());
                iLoginView.showLoginSuccess(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
