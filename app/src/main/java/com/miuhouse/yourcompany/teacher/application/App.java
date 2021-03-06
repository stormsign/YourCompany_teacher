package com.miuhouse.yourcompany.teacher.application;

import android.app.Application;
import android.content.Context;

import com.miuhouse.yourcompany.teacher.db.AccountDBTask;
import com.miuhouse.yourcompany.teacher.model.TeacherInfo;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;

/**
 * Created by khb on 2016/5/13.
 */
public class App extends Application {

    public static Context applicationContext;
    private boolean login;
    private String teacherId;
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        this.applicationContext = getApplicationContext();
        instance = this;
//        Constants.IMEI_VALUE = Util.getIMEI(this);
        Constants.IMEI_VALUE = "863175020757478";
        initLogin();
    }

    private void initLogin() {
        TeacherInfo info = AccountDBTask.getAccount();
        if (info != null) {
            login = true;
            teacherId = info.getId();
        } else {
            login = false;
        }
    }

    public static Context getContext() {
        return applicationContext;
    }

    public static App getInstance() {
        return instance;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getTeacherId() {
        if (teacherId == null) {
            teacherId = AccountDBTask.getAccount().getId();
        }
        return teacherId;
    }

    public void logout() {
        SPUtils.saveData(SPUtils.TOKEN, null);
        AccountDBTask.clear();
        teacherId = null;
        login = false;
    }
}
