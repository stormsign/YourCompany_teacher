package com.miuhouse.yourcompany.teacher.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.miuhouse.yourcompany.teacher.application.App;

/**
 * Created by kings on 7/11/2016.
 */
public class SPUtils {
    public static final String TOKEN="token";
    private static SharedPreferences sharedPreferences;

    public static void saveData(String key, String value) {
        Context context = App.getContext();
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static String getData(String key, String defaultValue) {
        Context context = App.getContext();
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(key, defaultValue);
    }
}
