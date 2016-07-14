package com.miuhouse.yourcompany.teacher.interactor.interf;

import com.android.volley.Response;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.User;

/**
 * Created by kings on 7/13/2016.
 */
public interface IUserInformation {
    void updateUserInformation(String teacherId, String tName, String sex, String college, String profession, String education, String grade, String[] pbxType, String introduction, String headUrl,Response.Listener<BaseBean> listener, Response.ErrorListener errorListener);
}
