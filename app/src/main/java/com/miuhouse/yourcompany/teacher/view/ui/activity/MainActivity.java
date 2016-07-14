package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RelativeLayout;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.android.pushservice.PushSettings;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.factory.FragmentFactory;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.MainPageAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.widget.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    private RelativeLayout content;
    private ViewPagerIndicator mPages;
    private List<Integer> imgResList = null;
    private List<String> titleList = null;

    @Override
    protected void onSaveInstanceState(Bundle outState) {}

    @Override
    protected void initTitle() {
//        super.initTitle();
    }

    @Override
    protected String setTitle() {
        return "主页";
    }

    @Override
    protected String setRight() {
        return "按钮";
    }

    @Override
    protected void initViewAndEvents() {
        content = (RelativeLayout) findViewById(R.id.content);
        imgResList = new ArrayList<>();
        titleList = new ArrayList<>();
        imgResList.add(R.mipmap.home_msg_n);
        imgResList.add(R.mipmap.home_orderlist_n);
        imgResList.add(R.mipmap.home_account_n);

        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(FragmentFactory.getFragment(BaseFragment.MESSAGES));
        fragmentList.add(FragmentFactory.getFragment(BaseFragment.ORDERS));
        fragmentList.add(FragmentFactory.getFragment(BaseFragment.ACCOUNT));

        mPages = (ViewPagerIndicator) findViewById(R.id.pageIndicator);
        ViewPager pager = (ViewPager) findViewById(R.id.pages);
        MainPageAdapter adapter = new MainPageAdapter(getSupportFragmentManager(), fragmentList);
        mPages.setTabItemImgs(imgResList);
        pager.setAdapter(adapter);
        mPages.setViewPager(pager, 1);
//        mPages.setTabItemTitles(titleList);
        PushSettings.enableDebugMode(context, true);
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, Constants.PUSH_APIKEY);
    }




    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected View getOverrideParentView() {
        return content;
    }

    @Override
    public void onBackClick() {
        L.i("back or finish!!!");
        showError("dd");
    }

    @Override
    public void onRightClick() {
        L.i("right!!!");
//        hideLoading();
        startActivity(new Intent(MainActivity.this, LoginRegistActivity.class));

//        showNotification();
    }

    private void showNotification() {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.asy);
        builder.setContentTitle("ContentTitle");
        builder.setContentText("ContentText");
//        builder.setSubText("subtext");
//        Notification notification = builder.build();
        builder.setAutoCancel(true);
        builder.setTicker("ticker");
//        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ico_map_radio_bus_pressd));
        builder.setContentIntent(PendingIntent.getActivity(this
                , 1
                , new Intent(this, LoginRegistActivity.class)
                , PendingIntent.FLAG_CANCEL_CURRENT));
        Notification notification = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.getNotification();
        }else{
            notification = builder.build();
        }
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.defaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_LIGHTS
                | Notification.DEFAULT_VIBRATE;
        nm.notify(190, notification);



    }

}
