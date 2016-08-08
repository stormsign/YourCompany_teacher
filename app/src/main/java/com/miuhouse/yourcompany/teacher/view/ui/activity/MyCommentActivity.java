package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.model.Evaluate;
import com.miuhouse.yourcompany.teacher.model.MyEvaluate;
import com.miuhouse.yourcompany.teacher.presenter.MyCommentPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IMyCommentPresenter;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IMyCommentView;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.MyCommentAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kings on 7/25/2016.
 */
public class MyCommentActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, IMyCommentView {

    private SwipeRefreshLayout refresh;
    private RecyclerView recyclerComment;
    private RelativeLayout relativeContent;

    private MyCommentAdapter adapter;
    private List<Evaluate> list = new ArrayList<>();
    private IMyCommentPresenter myCommentPresenter;
    private int page = 1;
    private boolean mIsFirstTimeTouchBottom = true;

    @Override
    protected String setTitle() {
        return "我的评价";
    }

    @Override
    protected String setRight() {
        return null;
    }

    @Override
    protected void initViewAndEvents() {

        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        recyclerComment = (RecyclerView) findViewById(R.id.recycler_my_comment);
        relativeContent = (RelativeLayout)findViewById(R.id.relative_content);
        refresh.setColorSchemeResources(android.R.color.holo_orange_light);
        refresh.setOnRefreshListener(this);
        recyclerComment.setLayoutManager(new LinearLayoutManager(context));

        adapter = new MyCommentAdapter(context, list);
        recyclerComment.setAdapter(adapter);
        myCommentPresenter = new MyCommentPresenter(this);
        myCommentPresenter.getMyComment(null, page, Constants.PAGE_SIZE);
        recyclerComment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;
            private int firstVisibleItem;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();


            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()
                        && firstVisibleItem != 0 && !refresh.isRefreshing()) {
                    page += 1;
                    myCommentPresenter.getMyComment(null, page, Constants.PAGE_SIZE);
                }
            }
        });
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_my_comment;
    }

    @Override
    protected View getOverrideParentView() {
        return relativeContent;
    }

    @Override
    public void onRefresh() {
        L.i("TAG", "onRefresh");
        refresh.setEnabled(false);
        page = 1;
        myCommentPresenter.getMyComment(null, page, Constants.PAGE_SIZE);

    }

    /**
     * 加载结束
     *
     * @param myEvaluate
     */
    @Override
    public void getMyCommentBean(MyEvaluate myEvaluate) {
        if (page == 1) {
            if (list.size() > 0) {
                list.clear();
            }
        }
        L.i("TAG", "myEvaluate=" + myEvaluate.getMsg());
        refresh.setRefreshing(false);
        refresh.setEnabled(true);
        list.addAll(myEvaluate.getEvaluates());

        adapter.notifyDataSetChanged();
    }


}
