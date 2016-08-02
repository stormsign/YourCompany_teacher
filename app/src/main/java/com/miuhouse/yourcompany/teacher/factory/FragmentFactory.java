package com.miuhouse.yourcompany.teacher.factory;

import android.support.v4.app.Fragment;

import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.AccountFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.FragmentA;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.FragmentB;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.FragmentC;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.FragmentD;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.MessagesFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.MyOrdersFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.OrdersFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.OrdersSquareFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by khb on 2016/7/6.
 */
public class FragmentFactory {

    private static Map<Integer, Fragment> map = new HashMap<>();

    public static Fragment getFragment(int position){
        BaseFragment baseFragment = null;
        if (map.containsKey(position)){
            if (map.get(position)!=null){
                baseFragment = (BaseFragment) map.get(position);
            }
        }else {
            switch (position){
                case BaseFragment.MESSAGES:
                    baseFragment = new MessagesFragment();
                    break;
                case BaseFragment.ORDERS:
                    baseFragment = new OrdersFragment();
                    break;
                case BaseFragment.ACCOUNT:
                    baseFragment = new AccountFragment();
                    break;
                case BaseFragment.ORDERSSQUARE:
                    baseFragment = new OrdersSquareFragment();
                    break;
                case BaseFragment.MYORDERS:
                    baseFragment = new MyOrdersFragment();
                    break;
                case BaseFragment.A:
                        baseFragment = new FragmentA();
                        break;
                case BaseFragment.B:
                        baseFragment = new FragmentB();
                        break;
                case BaseFragment.C:
                        baseFragment = new FragmentC();
                        break;
                case BaseFragment.D:
                        baseFragment = new FragmentD();
                        break;
            }
            map.put(position, baseFragment);
        }
        return baseFragment;

    }
}
