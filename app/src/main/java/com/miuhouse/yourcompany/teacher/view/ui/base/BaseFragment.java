package com.miuhouse.yourcompany.teacher.view.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by khb on 2016/7/6.
 */
public abstract class BaseFragment extends Fragment implements BaseView {

    private View view;
    public Context context;

    public static final int MESSAGESFRAGMENT = 0;
    public static final int ORDERSFRAGMENT = 1;
    public static final int ACCOUNTFRAGMENT = 2;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getFragmentResourceId()!=0) {
            view = inflater.inflate(getFragmentResourceId(), null);
            getViews(view);
            return view;
        }else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
    }

    public abstract int getFragmentResourceId();
    public abstract void getViews(View view);
    public abstract void setupView();

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void hideError() {

    }

    @Override
    public void netError() {

    }
}
