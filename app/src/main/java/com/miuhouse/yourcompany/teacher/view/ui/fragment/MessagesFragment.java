package com.miuhouse.yourcompany.teacher.view.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.listener.OnListItemClick;
import com.miuhouse.yourcompany.teacher.model.MsgEntity;
import com.miuhouse.yourcompany.teacher.presenter.MessagePresenter;
import com.miuhouse.yourcompany.teacher.view.ui.activity.SysMsgActivity;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.MsgAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IMessageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khb on 2016/7/6.
 */
public class MessagesFragment extends BaseFragment implements IMessageFragment, OnListItemClick {

    private SwipeRefreshLayout refresh;
    private RecyclerView msgList;
    private List<MsgEntity> list;

    private int page = 1;
    private MsgAdapter adapter;
    private MessagePresenter messagePresenter;
    private TextView unread;
    private int unreadCount = 0;
    private RelativeLayout sysMsg;

    @Override
    public int getFragmentResourceId() {
        return R.layout.fragment_message;
    }

    @Override
    public void getViews(View view) {
        messagePresenter = new MessagePresenter(this);
        list = new ArrayList<>();
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        msgList = (RecyclerView) view.findViewById(R.id.msgList);
        unread = (TextView) view.findViewById(R.id.unread);
        sysMsg = (RelativeLayout) view.findViewById(R.id.sysMsg);
    }

    @Override
    public void setupView() {
        refresh.setColorSchemeResources(R.color.themeColor);
        adapter = new MsgAdapter(context, list, this);
        msgList.setAdapter(adapter);
        msgList.setLayoutManager(new LinearLayoutManager(context));
        msgList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;
            private int firstVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()
                        && firstVisibleItem != 0) {
                    page += 1;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        });
        sysMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SysMsgActivity.class));
            }
        });
        if (unreadCount <=0){
//            unread.setText("");
            unread.setVisibility(View.GONE);
        }
    }

    @Override
    public View getOverrideParentView() {
        return null;
    }

    @Override
    public void refresh() {
        for (int i=0;i<10; i++){
            list.add(new MsgEntity());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void instantAdd() {

    }

    @Override
    public void onItemClick(Object data) {

    }
}
