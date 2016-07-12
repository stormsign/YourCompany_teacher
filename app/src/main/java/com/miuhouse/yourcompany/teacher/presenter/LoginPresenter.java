package com.miuhouse.yourcompany.teacher.presenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.application.App;
import com.miuhouse.yourcompany.teacher.db.AccountDBTask;
import com.miuhouse.yourcompany.teacher.interactor.GetUserInfo;
import com.miuhouse.yourcompany.teacher.interactor.interf.IGetUser;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.User;
import com.miuhouse.yourcompany.teacher.presenter.interf.ILoginPresenter;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.ILoginView;

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
//                L.i("TAG", "user=" + response.getName());
                if (response != null && response.getTeacherInfo() != null) {
                    if (response.getTeacherInfo().getToken() != null) {
                        SPUtils.saveData(SPUtils.TOKEN, response.getTeacherInfo().getToken());
                    }
                    AccountDBTask.saveUserBean(response.getTeacherInfo());
                    App.getInstance().setLogin(true);
                }

                iLoginView.showLoginSuccess(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    public void doRegist(String name, String password) {
        getUser.getRegistInfo(name, password, new Response.Listener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                L.i("TAG", "user=" + response);
                iLoginView.showRegistSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.i("TAG", "error=" + error);
            }
        });
    }
}
