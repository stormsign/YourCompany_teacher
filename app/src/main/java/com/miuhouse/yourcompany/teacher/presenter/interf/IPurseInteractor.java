package com.miuhouse.yourcompany.teacher.presenter.interf;

import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;

/**
 * Created by khb on 2016/7/26.
 */
public interface IPurseInteractor {
    void getPurseMsgs(String teacherId, int page, OnLoadCallBack onLoadCallBack);
}
