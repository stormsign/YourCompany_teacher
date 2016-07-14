package com.miuhouse.yourcompany.teacher.interactor;

import com.android.volley.Response;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.interactor.interf.IUserInformation;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.User;
import com.miuhouse.yourcompany.teacher.utils.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kings on 7/13/2016.
 */
public class UserInformationInfo implements IUserInformation {

    @Override
    public void updateUserInformation(String teacherId, String tName, String sex, String college, String profession, String education, String grade, String[] pbxType, String introduction, String headUrl, Response.Listener<BaseBean> listener, Response.ErrorListener errorListener) {
        String urlPath = Constants.URL_VALUE + "teacherUpdate";
        Map<String, Object> map = new HashMap<>();
        map.put("teacherId", "4028b88155c4dd070155c4dd8a340000");
        map.put("tName", tName);
        map.put("sex", sex);
        map.put("college", college);
        map.put("profession", profession);
        map.put("education", education);
        map.put("grade", grade);
        map.put("pbxType",teacherId);
        map.put("introduction", introduction);
        map.put("headUrl", headUrl);
        VolleyManager.getInstance().sendGsonRequest(null, urlPath, map, "6eca806dffed65f70f6d50a3b435069b", BaseBean.class, listener, errorListener);
    }
}
