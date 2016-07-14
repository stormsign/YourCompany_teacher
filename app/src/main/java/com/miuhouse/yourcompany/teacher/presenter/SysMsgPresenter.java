package com.miuhouse.yourcompany.teacher.presenter;

import com.miuhouse.yourcompany.teacher.interactor.SysMsgInteractor;
import com.miuhouse.yourcompany.teacher.interactor.interf.ISysMsgInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.presenter.interf.ISysMsgPresenter;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.ISysMsgActivity;

/**
 * Created by khb on 2016/7/14.
 */
public class SysMsgPresenter implements ISysMsgPresenter, OnLoadCallBack {
    private ISysMsgActivity activity;
    private ISysMsgInteractor interactor;
    public SysMsgPresenter(ISysMsgActivity activity) {
        this.activity = activity;
        this.interactor = new SysMsgInteractor(this);
    }

    @Override
    public void getMsgs(int page) {
        interactor.getMsgs(page);
    }

    @Override
    public void onPreLoad(String msg) {
        activity.showLoading(msg);
    }

    @Override
    public void onLoadSuccess(Object data) {
        activity.hideLoading();
//        activity.showError("???");
        SysMsgInteractor.SysMsgBean bean = (SysMsgInteractor.SysMsgBean) data;
        activity.refresh(bean);
    }

    @Override
    public void onLoadFailed(String msg) {
        activity.showError(msg);
    }
}
