package com.miuhouse.yourcompany.teacher.view.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.activity.LoginRegistActivity;
import com.miuhouse.yourcompany.teacher.view.widget.ViewOverrideManager;

/**
 * Created by khb on 2016/7/6.
 */
public abstract class BaseFragment extends Fragment implements BaseView {

    private View view;
    public Context context;

    public static final int MESSAGES = 0;
    public static final int ORDERS = 1;
    public static final int ACCOUNT = 2;
    public static final int ORDERSSQUARE = 3;
    public static final int MYORDERS = 4;
    public static final int A = 5;
    public static final int B = 6;
    public static final int C = 7;
    public static final int D = 8;
    public ViewOverrideManager viewOverrideManager;
    private boolean tag = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getFragmentResourceId() != 0) {
            view = inflater.inflate(getFragmentResourceId(), null);
            getViews(view);
            return view;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
        //        设置异常页面管理
        viewOverrideManager = new ViewOverrideManager(getOverrideParentView());
    }

    public abstract int getFragmentResourceId();

    public abstract void getViews(View view);

    public abstract void setupView();

    public abstract View getOverrideParentView();


    @Override
    public void showLoading(String msg) {
        if (null == viewOverrideManager){
            viewOverrideManager = new ViewOverrideManager(getOverrideParentView());
        }
        viewOverrideManager.showLoading();
    }

    @Override
    public void showError(int type) {
        if (null == viewOverrideManager){
            viewOverrideManager = new ViewOverrideManager(getOverrideParentView());
        }
//        viewOverrideManager.showLoading(type);
    }

    @Override
    public void hideLoading() {
        if (null == viewOverrideManager){
            viewOverrideManager = new ViewOverrideManager(getOverrideParentView());
        }
        viewOverrideManager.restoreView();
    }

    @Override
    public void hideError() {
        if (null == viewOverrideManager){
            viewOverrideManager = new ViewOverrideManager(getOverrideParentView());
        }
        viewOverrideManager.restoreView();
    }

    @Override
    public void netError() {
        if (null == viewOverrideManager){
            viewOverrideManager = new ViewOverrideManager(getOverrideParentView());
        }
    }

    @Override
    public void onTokenExpired() {
        L.i("TAG", "getActivity=" + getActivity());
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), LoginRegistActivity.class);
            intent.putExtra("code", 1);
            startActivity(intent);
            getActivity().finish();
        }


    }
}
