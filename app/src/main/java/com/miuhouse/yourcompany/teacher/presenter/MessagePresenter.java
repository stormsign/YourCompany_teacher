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
    public void getTopMsgs() {
        int sysCount = SPUtils.getData(Constants.UNREAD_SYSMSG_COUNT, 0);
        String latestSysMsg = SPUtils.getData(Constants.LATEST_SYSMSG, null);
        int purseCount = SPUtils.getData(Constants.UNREAD_PURSEMSG_COUNT, 0);
        String latestPurseMsg = SPUtils.getData(Constants.LATEST_PURSEMSG, null);
        messageFragment.setTop(sysCount, latestSysMsg, purseCount, latestPurseMsg);
    }

    @Override
    public void getMsgList(int page) {

    }

    @Override
    public void onPreLoad() {

    }

    @Override
    public void onLoadSuccess(Object data) {

    }

    @Override
    public void onLoadFailed(String msg) {

    }
}
