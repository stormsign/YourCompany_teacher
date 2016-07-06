package com.miuhouse.yourcompany.teacher.view.ui.fragment;

import android.view.View;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IMessageFragment;

/**
 * Created by khb on 2016/7/6.
 */
public class MessagesFragment extends BaseFragment implements IMessageFragment {
    @Override
    public int getFragmentResourceId() {
        return R.layout.fragment_message;
    }

    @Override
    public void getViews(View view) {

    }

    @Override
    public void setupView() {

    }
}
