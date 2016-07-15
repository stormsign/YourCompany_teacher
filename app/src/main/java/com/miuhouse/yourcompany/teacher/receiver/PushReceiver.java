package com.miuhouse.yourcompany.teacher.receiver;

import android.content.Context;
import android.content.Intent;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;
import com.miuhouse.yourcompany.teacher.view.ui.activity.SysMsgActivity;

import java.util.List;

/**
 * Created by khb on 2016/7/14.
 */
public class PushReceiver extends PushMessageReceiver{
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
    public void onUnbind(Context context, int errorCode, String requestId) {

    }

    @Override
    public void onSetTags(Context context, int errorCode,
                          List<String> sucessTags, List<String> failTags, String requestId) {

    }

    @Override
    public void onDelTags(Context context, int errorCode,
                          List<String> sucessTags, List<String> failTags, String requestId) {

    }

    @Override
    public void onListTags(Context context, int errorCode, List<String> tags,
                           String requestId) {

    }

    @Override
    public void onMessage(Context context, String message,
                          String customContentString) {

    }

    @Override
    public void onNotificationClicked(Context context, String title,
                                      String description, String customContentString) {
        Intent intent = new Intent();
        intent.setClass(context.getApplicationContext(), SysMsgActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    public void onNotificationArrived(Context context, String title,
                                      String description, String customContentString) {
        String notifyString = "通知到达 onNotificationArrived  title=\"" + title
                + "\" description=\"" + description + "\" customContent="
                + customContentString;
        L.i(notifyString);
        int count = SPUtils.getData(Constants.UNREAD_SYSMSG_COUNT, 0);
        count += 1;
        SPUtils.saveData(Constants.UNREAD_SYSMSG_COUNT, count);
        SPUtils.saveData(Constants.LATEST_SYSMSG, description);

        Intent intent = new Intent(Constants.INTENT_ACTOIN_RECEIVE);
        context.sendBroadcast(intent);
    }
}
