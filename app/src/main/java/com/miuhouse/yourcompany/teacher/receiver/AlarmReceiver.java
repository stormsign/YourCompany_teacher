package com.miuhouse.yourcompany.teacher.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.activity.LoginRegistActivity;

/**
 * Created by khb on 2016/8/8.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "alarm alarm alarm", Toast.LENGTH_LONG).show();
        String orderid = intent.getStringExtra("orderid");
        L.i("alarm orderid " + orderid);
        showNotification(context);
    }

    private void showNotification(Context context) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.guanyu_logo);
        builder.setContentTitle("ContentTitle");
        builder.setContentText("ContentText");
//        builder.setSubText("subtext");
//        Notification notification = builder.build();
        builder.setAutoCancel(true);
        builder.setTicker("ticker");
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.guanyu_logo));
        builder.setContentIntent(PendingIntent.getActivity(context
                , 1
                , new Intent(context, LoginRegistActivity.class)
                , PendingIntent.FLAG_CANCEL_CURRENT));
        Notification notification = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.getNotification();
        } else {
            notification = builder.build();
        }
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.defaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_LIGHTS
                | Notification.DEFAULT_VIBRATE;
        nm.notify(190, notification);

    }
}
