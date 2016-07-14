package com.miuhouse.yourcompany.teacher.presenter;

import com.miuhouse.yourcompany.teacher.presenter.interf.IMessagePresenter;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IMessageFragment;

/**
 * Created by khb on 2016/7/14.
 */
public class MessagePresenter implements IMessagePresenter {

    private IMessageFragment messageFragment;

    public MessagePresenter(IMessageFragment messageFragment){
        this.messageFragment = messageFragment;
    }



}
