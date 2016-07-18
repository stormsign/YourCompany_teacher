package com.miuhouse.yourcompany.teacher.view.ui.fragment.interf;

import com.miuhouse.yourcompany.teacher.view.ui.base.BaseView;

/**
 * Created by khb on 2016/7/6.
 */
public interface IMessageFragment extends BaseView{

    void refresh();
    void instantAdd();
    void setTop(int count, String msg);
}
