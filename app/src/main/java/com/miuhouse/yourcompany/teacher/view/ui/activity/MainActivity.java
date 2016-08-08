package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.android.pushservice.PushSettings;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.factory.FragmentFactory;
import com.miuhouse.yourcompany.teacher.listener.OnReceiveListener;
import com.miuhouse.yourcompany.teacher.receiver.MyPushReceiver;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.DictManager;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.MainPageAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.MessagesFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.OrdersFragment;
import com.miuhouse.yourcompany.teacher.view.widget.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends BaseActivity implements OnReceiveListener {

    private RelativeLayout content;
    private ViewPagerIndicator mPages;
    private List<Integer> imgResList = null;
    private List<String> titleList = null;
    private ViewPager pager;
    private MyPushReceiver receiver;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

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

        final List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(FragmentFactory.getFragment(BaseFragment.MESSAGES));
        fragmentList.add(FragmentFactory.getFragment(BaseFragment.ORDERS));
        fragmentList.add(FragmentFactory.getFragment(BaseFragment.ACCOUNT));

        mPages = (ViewPagerIndicator) findViewById(R.id.pageIndicator);
        pager = (ViewPager) findViewById(R.id.pages);
        MainPageAdapter adapter = new MainPageAdapter(getSupportFragmentManager(), fragmentList);
        mPages.setTabItemImgs(imgResList);
        pager.setAdapter(adapter);
        mPages.setViewPager(pager, 1);

        PushSettings.enableDebugMode(context, true);
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, Constants.PUSH_APIKEY);

        receiver = new MyPushReceiver(this);
        IntentFilter filter = new IntentFilter(Constants.INTENT_ACTOIN_RECEIVE);
        registerReceiver(receiver, filter);

        DictManager.getInstance(this).init();


        Intent intent = new Intent("CLASS_ALARM_ACTION");
        intent.putExtra("orderid", "orderidxxx");
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        am.set(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pi);


        PendingIntent pi2 = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        calendar.add(Calendar.SECOND, 5);
        am.set(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pi2);

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
//        showError();
    }

    @Override
    public void onRightClick() {
        L.i("right!!!");
//        hideLoading();
//        startActivity(new Intent(MainActivity.this, LoginRegistActivity.class));

//        showNotification();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int type = intent.getIntExtra("type", 0);
        if (type == 0){
            return ;
        }
        if (type == 1){
            (FragmentFactory.getFragment(BaseFragment.MYORDERS)).onResume();
            ((OrdersFragment) FragmentFactory.getFragment(BaseFragment.ORDERS)).changeListToggle(false);
        }else if (type == 3 ||
                type == 4) {
            ((MessagesFragment) FragmentFactory.getFragment(BaseFragment.MESSAGES)).refreshTop();
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
