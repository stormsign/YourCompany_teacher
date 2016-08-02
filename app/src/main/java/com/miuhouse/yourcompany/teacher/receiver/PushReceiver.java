package com.miuhouse.yourcompany.teacher.receiver;

import android.content.Context;
import android.content.Intent;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;
import com.miuhouse.yourcompany.teacher.view.ui.activity.SysMsgActivity;

import org.json.JSONException;
import org.json.JSONObject;

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
        try {
            JSONObject jObject = new JSONObject(customContentString);
            int type = jObject.getInt("type");

            switch (type){
                case 1:     //订单通知

                    break;
                case 2:     //上课提醒

                    break;
                case 3:      //账户变动， 账户通知
                    int count3 = SPUtils.getData(Constants.UNREAD_PURSEMSG_COUNT, 0);
                    count3 += 1;
                    SPUtils.saveData(Constants.UNREAD_PURSEMSG_COUNT, count3);
                    SPUtils.saveData(Constants.LATEST_PURSEMSG, description);
                    break;
                case 4:     //系统通知
                    int count4 = SPUtils.getData(Constants.UNREAD_SYSMSG_COUNT, 0);
                    count4 += 1;
                    SPUtils.saveData(Constants.UNREAD_SYSMSG_COUNT, count4);
                    SPUtils.saveData(Constants.LATEST_SYSMSG, description);
                    break;
            }

            Intent intent = new Intent(Constants.INTENT_ACTOIN_RECEIVE);
            context.sendBroadcast(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
