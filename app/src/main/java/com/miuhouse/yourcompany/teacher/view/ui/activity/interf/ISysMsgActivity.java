package com.miuhouse.yourcompany.teacher.view.ui.activity.interf;

import com.miuhouse.yourcompany.teacher.interactor.SysMsgInteractor;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseView;

/**
 * Created by khb on 2016/7/14.
 */
public interface ISysMsgActivity extends BaseView{
    void refresh(SysMsgInteractor.SysMsgBean bean);
}
