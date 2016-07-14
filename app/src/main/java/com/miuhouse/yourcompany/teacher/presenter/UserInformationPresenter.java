package com.miuhouse.yourcompany.teacher.presenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.interactor.UserInformationInfo;
import com.miuhouse.yourcompany.teacher.interactor.interf.IUserInformation;
import com.miuhouse.yourcompany.teacher.listener.IUserInformationView;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.presenter.interf.IUserInformationPresenter;

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
    public void doChangeUserInformation(String teacherId, String tName, String sex, String college, String profession, String education, String grade, String[] pbxType, String introduction, String headUrl) {
        userInformation.updateUserInformation(teacherId, tName, sex, college, profession, education, grade, pbxType, introduction, headUrl, new Response.Listener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
