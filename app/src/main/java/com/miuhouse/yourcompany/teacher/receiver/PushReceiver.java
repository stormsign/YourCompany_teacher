package com.miuhouse.yourcompany.teacher.receiver;

import android.content.Context;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.miuhouse.yourcompany.teacher.utils.L;

import java.util.List;

/**
 * Created by khb on 2016/7/14.
 */
public class PushReceiver extends PushMessageReceiver {
    @Override
    public void onBind(Context context, int errorCode, String appid, String userId, String channelId, String requestId) {
        String responseString = "onBind errorCode=" + errorCode + " appid=" + appid + " userId=" + userId + " channelId=" + channelId + " requestId=" + requestId;
        L.i("TAG", responseString);
//        if (channelId != null) {
//            SettingHelper.setEditor(context, "channelId", Long.parseLong(channelId));
//        }
//        SettingHelper.setEditor(context, "userId", userId);
//        if (userId != null && channelId != null && SettingUtility.firstStartBaidu()) {
//            update(channelId, userId, context);
//        }
    }

    @Override
    public void onUnbind(Context context, int i, String s) {

    }

    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {

    }

    @Override
    public void onMessage(Context context, String s, String s1) {

    }

    @Override
    public void onNotificationClicked(Context context, String s, String s1, String s2) {

    }

    @Override
    public void onNotificationArrived(Context context, String s, String s1, String s2) {

    }
}
