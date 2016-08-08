package com.miuhouse.yourcompany.teacher.presenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.interactor.UserInformationInfo;
import com.miuhouse.yourcompany.teacher.interactor.interf.IUserInformation;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.User;
import com.miuhouse.yourcompany.teacher.presenter.interf.IUserInformationPresenter;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IUserInformationView;
import com.miuhouse.yourcompany.teacher.view.widget.ViewOverrideManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kings on 7/13/2016.
 */
public class UserInformationPresenter implements IUserInformationPresenter {
    private IUserInformationView userInformationView;
    private IUserInformation userInformation;

    public UserInformationPresenter(IUserInformationView userInformationView) {
        this.userInformationView = userInformationView;
        userInformation = new UserInformationInfo();
    }


    @Override
    public void doChangeUserInformation(String teacherId, ArrayList<String> images, String tName, int sex, String college, String profession, int education, int grade, List<Integer> pbxType, String introduction, String headUrl) {
        userInformation.updateUserInformation(teacherId, images, tName, sex, college, profession, education, grade, pbxType, introduction, headUrl, new Response.Listener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                userInformationView.UpdateSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    public void getUserInfo(String teacherId) {
        userInformationView.showLoading("正在加载中...");
        userInformation.getUserInfo(teacherId, new Response.Listener<User>() {
            @Override
            public void onResponse(User response) {
                if (response.getCode() == 1) {
                    userInformationView.onTokenExpired();
                } else {
                    userInformationView.getUserInfo(response);
                    userInformationView.hideLoading();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                userInformationView.showError(ViewOverrideManager.NO_NETWORK);
            }
        });
    }

    @Override
    public void updatePhone(String teacherId, String mobile) {
        userInformation.updateUserPhone(teacherId, mobile, new Response.Listener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                userInformationView.UpdateSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                userInformationView.showError(ViewOverrideManager.NO_NETWORK);
            }
        });
    }
}
