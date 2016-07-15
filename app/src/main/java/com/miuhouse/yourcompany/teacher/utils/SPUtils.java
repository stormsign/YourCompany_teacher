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

    //写入
    public static void saveData(String key, String value) {
        Context context = App.getContext();
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(key, value).commit();
    }

    //读出
    public static String getData(String key, String defValue) {
        Context context = App.getContext();
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(key, defValue);
    }

    //写入
    public static void saveData(String key, int value) {
        Context context = App.getContext();
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putInt(key, value).commit();
    }

    //读出
    public static int getData(String key, int defValue) {
        Context context = App.getContext();
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getInt(key, defValue);
    }

    //写入
    public static void saveData(String key, boolean value) {
        Context context = App.getContext();
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    //读出
    public static Boolean getData(String key, boolean defValue) {
        Context context = App.getContext();
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getBoolean(key, defValue);
    }

    //写入
    public static void saveData(String key, long value) {
        Context context = App.getContext();
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putLong(key, value).commit();
    }

    //读出
    public static long getData(String key, long defValue) {
        Context context = App.getContext();
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getLong(key, defValue);
    }
}
