package com.miuhouse.yourcompany.teacher.interactor;

import com.android.volley.Response;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.interactor.interf.IGetUser;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.User;
import com.miuhouse.yourcompany.teacher.utils.AesUtil;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;
import com.miuhouse.yourcompany.teacher.view.ui.activity.LoginRegistActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * 耗时操作
 * Created by kings on 7/1/2016.
 */
public class GetUserInfo implements IGetUser {
    @Override
    public void getUserInfo(String name, String password, Response.Listener<User> listener, Response.ErrorListener errorListener) {

        String urlPath = Constants.URL_VALUE + "login";
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", name);
        try {
            map.put("password", AesUtil.Encrypt(password, AesUtil.cKey));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        map.put("type", 4);
        map.put("deviceType", SPUtils.getData(SPUtils.DEVICE_TYPE, null));
        VolleyManager.getInstance().sendGsonRequest("login", urlPath, map, User.class, listener, errorListener);
    }

    @Override
    public void getRegistInfo(int typeMark, String name, String password, Response.Listener<BaseBean> listener, Response.ErrorListener errorListener) {

        String urlPath = null;
        if (typeMark == LoginRegistActivity.TYPE_MARK_REGIST) {
            urlPath = Constants.URL_VALUE + "regist";
        } else {
            urlPath = Constants.URL_VALUE + "reset";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", name);
        try {
            map.put("password", AesUtil.Encrypt(password, AesUtil.cKey));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        map.put("type", 4);
        map.put("deviceType", SPUtils.getData(SPUtils.DEVICE_TYPE, null));
        VolleyManager.getInstance().sendGsonRequest("register", urlPath, map, BaseBean.class, listener, errorListener);
    }
}
