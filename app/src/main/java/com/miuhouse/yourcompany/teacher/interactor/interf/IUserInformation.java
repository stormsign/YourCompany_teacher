package com.miuhouse.yourcompany.teacher.interactor.interf;

import com.android.volley.Response;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kings on 7/13/2016.
 */
public interface IUserInformation {
    void updateUserInformation(String teacherId, ArrayList<String> images, String tName, int sex, String college, String profession, int education, int grade, List<Integer> pbxType, String introduction, String headUrl, Response.Listener<BaseBean> listener, Response.ErrorListener errorListener);
    void getUserInfo(String teacherId,Response.Listener<User> listener, Response.ErrorListener errorListener);
}
