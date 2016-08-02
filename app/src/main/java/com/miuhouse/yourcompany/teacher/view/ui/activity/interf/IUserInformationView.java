package com.miuhouse.yourcompany.teacher.view.ui.activity.interf;

import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.User;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseView;

/**
 * Created by kings on 7/13/2016.
 */
public interface IUserInformationView extends BaseView {
    void UpdateSuccess(BaseBean baseBean);
    void getUserInfo(User user);
}
