package com.miuhouse.yourcompany.teacher.factory;

import android.support.v4.app.Fragment;

import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.AccountFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.MessagesFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.OrdersFragment;

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
                case BaseFragment.MESSAGESFRAGMENT:
                    baseFragment = new MessagesFragment();
                    break;
                case BaseFragment.ORDERSFRAGMENT:
                    baseFragment = new OrdersFragment();
                    break;
                case BaseFragment.ACCOUNTFRAGMENT:
                    baseFragment = new AccountFragment();
                    break;
            }
            map.put(position, baseFragment);
        }
        return baseFragment;

    }
}
