package com.miuhouse.yourcompany.teacher.view.ui.fragment.interf;

import com.miuhouse.yourcompany.teacher.view.ui.base.BaseView;

/**
 * Created by khb on 2016/7/6.
 */
public interface IMessageFragment extends BaseView{

    void refreshList();
    void refreshTop();
    void instantAdd();
    void setTop(int sysCount, String sysMsg, int purseCount, String purseMsg);
}
