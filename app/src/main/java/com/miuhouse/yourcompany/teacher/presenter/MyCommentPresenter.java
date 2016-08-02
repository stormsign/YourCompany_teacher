package com.miuhouse.yourcompany.teacher.presenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.interactor.MyCommentInfo;
import com.miuhouse.yourcompany.teacher.interactor.interf.IMyComment;
import com.miuhouse.yourcompany.teacher.model.MyEvaluate;
import com.miuhouse.yourcompany.teacher.presenter.interf.IMyCommentPresenter;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IMyCommentView;

/**
 * Created by kings on 7/26/2016.
 */
public class MyCommentPresenter implements IMyCommentPresenter {

    private IMyComment myCommentInteractor;
    private IMyCommentView myCommentView;

    public MyCommentPresenter(IMyCommentView myCommentView) {
        this.myCommentView = myCommentView;
        myCommentInteractor = new MyCommentInfo();
    }

    @Override
    public void getMyComment(String teacherId, int page, int pageSize) {
        myCommentInteractor.getMyComment(teacherId, page, pageSize, new Response.Listener<MyEvaluate>() {
            @Override
            public void onResponse(MyEvaluate response) {
                if (response.getCode() == 1) {
                    myCommentView.onTokenExpired();
                } else {
                    myCommentView.getMyCommentBean(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                myCommentView.showError("");

            }
        });
    }


}
