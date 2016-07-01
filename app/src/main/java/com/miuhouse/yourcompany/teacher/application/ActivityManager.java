package com.miuhouse.yourcompany.teacher.application;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/** 管理Activity的出入栈管理类
 * Created by khb on 2016/7/1.
 */
public class ActivityManager {

    private Context context;
    private static Stack<Activity> activityStack = null;

    private static class Builder{
        private final static ActivityManager INSTANCE = new ActivityManager(App.getContext());
    }

    public static ActivityManager getInstance(){
        return Builder.INSTANCE;
    }

    private ActivityManager(Context context){
        this.context = context;
    }

    /**
     * 一个Activity入栈
     * @param activity
     */
    public void pushActivity(Activity activity){
        if (null==activityStack){
            activityStack = new Stack<>();
        }
        if (null!=activity) {
            activityStack.add(activity);
        }
    }

    /**
     * 一个Activity出栈
     * @param activity
     */
    public void popActivity(Activity activity){
        if (null!=activity) {
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 获取栈顶Activity
     * @return
     */
    public Activity getTopActivity(){
        if (null != activityStack && !activityStack.empty()){
            return activityStack.lastElement();
        }
        return null;
    }

    /**
     * 退出栈内所有Activity
     */
    public void finishAll(){
        if (null == activityStack){
            return ;
        }
        while (activityStack.size()>0){
            activityStack.remove(activityStack.lastElement());
        }
    }
}
