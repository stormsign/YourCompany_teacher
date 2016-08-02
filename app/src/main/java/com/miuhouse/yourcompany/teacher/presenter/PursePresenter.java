package com.miuhouse.yourcompany.teacher.presenter;

import com.miuhouse.yourcompany.teacher.interactor.PurseInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.model.TradingRecordListBean;
import com.miuhouse.yourcompany.teacher.presenter.interf.IPurseInteractor;
import com.miuhouse.yourcompany.teacher.presenter.interf.IPursePresenter;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IPurseMsgActivity;
import com.miuhouse.yourcompany.teacher.view.widget.ViewOverrideManager;

/**
 * Created by khb on 2016/7/26.
 */
public class PursePresenter implements IPursePresenter {
    private IPurseMsgActivity activity;
    private IPurseInteractor interactor;
    public PursePresenter(IPurseMsgActivity purseMsgActivity) {
        this.activity = purseMsgActivity;
        this.interactor = new PurseInteractor(this);

    }


    @Override
    public void getPurseMsgs(String teacherId, int page) {
        interactor.getPurseMsgs(teacherId, page, new OnLoadCallBack() {
            @Override
            public void onPreLoad() {
                activity.showLoading(null);
            }

            @Override
            public void onLoadSuccess(Object data) {
                activity.hideLoading();
                if (null != data){
                    TradingRecordListBean bean = (TradingRecordListBean) data;
                    if (null != bean.getWithdraw()
                            && bean.getWithdraw().size()>0){
                        activity.refresh(bean);
                    }else {
                        activity.showError(ViewOverrideManager.NO_MSG);
                    }
                }else{
                    activity.showError(ViewOverrideManager.NO_NETWORK);
                }

            }

            @Override
            public void onLoadFailed(String msg) {
                activity.hideLoading();
                activity.showError(ViewOverrideManager.NO_NETWORK);
            }
        });
    }
}
