package com.miuhouse.yourcompany.teacher.interactor;

import com.android.volley.Response;
import com.miuhouse.yourcompany.teacher.application.App;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.interactor.interf.IUserInformation;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.User;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kings on 7/13/2016.
 */
public class UserInformationInfo implements IUserInformation {

    @Override
    public void updateUserInformation(String teacherId, ArrayList<String> images, String tName, int sex, String college, String profession, int education, int grade, List<Integer> pbxType, String introduction, String headUrl, Response.Listener<BaseBean> listener, Response.ErrorListener errorListener) {
        String urlPath = Constants.URL_VALUE + "teacherUpdate";
        Map<String, Object> map = new HashMap<>();
        map.put("teacherId", App.getInstance().getTeacherId());
        map.put("tName", tName);
        map.put("sex", sex);
        map.put("college", college);
        map.put("profession", profession);
        map.put("education", education);
        map.put("grade", grade);
        map.put("pbxType", teacherId);
        map.put("introduction", introduction);
        map.put("headUrl", headUrl);
        map.put("pbxType", pbxType);
        map.put("images", images);
        L.i("images=" + (String) images.toString());
        L.i("images=" + images.toArray());
        VolleyManager.getInstance().sendGsonRequest(null, urlPath, map, SPUtils.getData(SPUtils.TOKEN, null), BaseBean.class, listener, errorListener);
    }

    @Override
    public void getUserInfo(String teacherId, Response.Listener<User> listener, Response.ErrorListener errorListener) {
        String urlPath = Constants.URL_VALUE + "teacherInfo";
        Map<String, Object> map = new HashMap<>();
        map.put("teacherId", App.getInstance().getTeacherId());
        L.i("TAG", "token=" + SPUtils.getData(SPUtils.TOKEN, null));
        L.i("TAG","teacherId="+App.getInstance().getTeacherId());
        VolleyManager.getInstance().sendGsonRequest(null, urlPath, map, SPUtils.getData(SPUtils.TOKEN, null), User.class, listener, errorListener);

    }

    @Override
    public void updateUserPhone(String teacherId, String mobile, Response.Listener<BaseBean> listener, Response.ErrorListener errorListener) {
        String urlPath = Constants.URL_VALUE + "updateMobile";
        Map<String, Object> map = new HashMap<>();
        map.put("teacherId", App.getInstance().getTeacherId());
        map.put("mobile", mobile);
        VolleyManager.getInstance().sendGsonRequest(null, urlPath, map, SPUtils.getData(SPUtils.TOKEN, null), BaseBean.class, listener, errorListener);
    }
}
