package com.miuhouse.yourcompany.teacher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.miuhouse.yourcompany.teacher.listener.OnReceiveListener;

/**
 * Created by khb on 2016/7/15.
 */
public class MyPushReceiver extends BroadcastReceiver {

    private OnReceiveListener listener;

    public MyPushReceiver(OnReceiveListener listener){
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        listener.onReceive(context, intent);
    }
}
