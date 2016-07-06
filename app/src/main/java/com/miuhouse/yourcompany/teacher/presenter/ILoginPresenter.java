package com.miuhouse.yourcompany.teacher.presenter;

/**
 * Created by kings on 7/1/2016.
 */
public interface ILoginPresenter {
    void clear();
    void doLogin(String name,String passwd);
    void doRegist(String name,String password);
}
