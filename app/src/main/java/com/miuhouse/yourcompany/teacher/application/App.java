package com.miuhouse.yourcompany.teacher.application;

import android.app.Application;
import android.content.Context;

import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.Util;

/**
 * Created by khb on 2016/5/13.
 */
public class App extends Application {

    public static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.applicationContext = getApplicationContext();
        Constants.IMEI_VALUE = Util.getIMEI(this);

    }

    public static Context getContext(){
        return applicationContext;
    }
}
