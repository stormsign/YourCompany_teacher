package com.miuhouse.yourcompany.teacher.presenter;

import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.presenter.interf.IMessagePresenter;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IMessageFragment;

/**
 * Created by khb on 2016/7/14.
 */
public class MessagePresenter implements IMessagePresenter, OnLoadCallBack{

    private IMessageFragment messageFragment;

    public MessagePresenter(IMessageFragment messageFragment){
        this.messageFragment = messageFragment;
    }


    @Override
    public void getLatestMsg() {
        int count = SPUtils.getData(Constants.UNREAD_SYSMSG_COUNT, 0);
        String latestMsg = SPUtils.getData(Constants.LATEST_SYSMSG, null);
        messageFragment.setTop(count, latestMsg);
    }

    @Override
    public void getMsgList(int page) {

    }

    @Override
    public void onPreLoad(String msg) {

    }

    @Override
    public void onLoadSuccess(Object data) {

    }

    @Override
    public void onLoadFailed(String msg) {

    }
}
