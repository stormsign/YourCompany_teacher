package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IOrdersManageActivity;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.MainPageAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.FragmentA;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.FragmentB;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.FragmentC;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.FragmentD;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.FragmentE;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.FragmentF;
import com.miuhouse.yourcompany.teacher.view.widget.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khb on 2016/7/18.
 */
public class OrdersManageActivity extends BaseActivity implements IOrdersManageActivity {

    private ViewPager orderpager;
    private ViewPagerIndicator orderIndicator;

    @Override
    protected String setTitle() {
        return "订单管理";
    }

    @Override
    protected String setRight() {
        return null;
    }

    @Override
    protected void initViewAndEvents() {
        List<String> textList = new ArrayList<>();
        textList.add("全部");
        textList.add("待上课");
        textList.add("待进行");
        textList.add("待评价");
        textList.add("已结束");
        textList.add("已取消");
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentA());
        fragmentList.add(new FragmentB());
        fragmentList.add(new FragmentC());
        fragmentList.add(new FragmentD());
        fragmentList.add(new FragmentE());
        fragmentList.add(new FragmentF());
        orderIndicator = (ViewPagerIndicator) findViewById(R.id.orderIndicator);
        orderpager = (ViewPager) findViewById(R.id.orderpager);
        orderpager.setAdapter(new MainPageAdapter(getSupportFragmentManager(), fragmentList));
        orderIndicator.setTabItemTitles(textList);
        orderIndicator.setViewPager(orderpager, 0);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_ordersmanage;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }
}
