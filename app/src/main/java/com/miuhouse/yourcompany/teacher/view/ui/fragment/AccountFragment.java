package com.miuhouse.yourcompany.teacher.view.ui.fragment;

import android.content.Intent;
import android.view.View;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.view.ui.activity.LoginRegistActivity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IAccountFragment;

/**
 * Created by khb on 2016/7/6.
 */
public class AccountFragment extends BaseFragment implements IAccountFragment {
    @Override
    public int getFragmentResourceId() {
        return R.layout.fragment_account;
    }

    @Override
    public void getViews(View view) {
        view.findViewById(R.id.tv_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginRegistActivity.class));
            }
        });
    }

    @Override
    public void setupView() {

    }
}
