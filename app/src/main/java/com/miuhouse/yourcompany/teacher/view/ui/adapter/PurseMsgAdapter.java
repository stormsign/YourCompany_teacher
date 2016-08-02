package com.miuhouse.yourcompany.teacher.view.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.listener.OnListItemClick;
import com.miuhouse.yourcompany.teacher.model.TradingRecordBean;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseRVAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by khb on 2016/7/26.
 */
public class PurseMsgAdapter extends BaseRVAdapter{
    public PurseMsgAdapter(Context context, List<TradingRecordBean> list, OnListItemClick onListItemClick) {
        super(context, list, onListItemClick);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PurseHolder(LayoutInflater.from(context).inflate(R.layout.item_purse, null));
    }

    class PurseHolder extends RecyclerView.ViewHolder{

        TextView topic;
        TextView date;
        TextView time;
        TextView amount;
        TextView account;
        RelativeLayout withdraw;
//        CircularImageViewHome header;
//        LinearLayout moneyArrive;
//        TextView type;

        public PurseHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            topic = (TextView) itemView.findViewById(R.id.topic);
            date = (TextView) itemView.findViewById(R.id.date);
            amount = (TextView) itemView.findViewById(R.id.amount);
            withdraw = (RelativeLayout) itemView.findViewById(R.id.withdraw);
            account = (TextView) itemView.findViewById(R.id.account);
//            moneyArrive = (LinearLayout) itemView.findViewById(R.id.moneyArrive);
//            header = (CircularImageViewHome) itemView.findViewById(R.id.header);
//            type = (TextView) itemView.findViewById(R.id.type);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PurseHolder mholder = (PurseHolder) holder;
        TradingRecordBean record = (TradingRecordBean) list.get(position);
        if (record.getChangeType().equals("1")){    //到账通知
            mholder.withdraw.setVisibility(View.GONE);
//            mholder.moneyArrive.setVisibility(View.VISIBLE);
            mholder.topic.setText("到账通知");
//            mholder.
        }else if (record.getChangeType().equals("2")){     //提现通知
            mholder.withdraw.setVisibility(View.VISIBLE);
//            mholder.moneyArrive.setVisibility(View.GONE);
            mholder.topic.setText("提现成功");
        }
        mholder.amount.setText(record.getAmount().toString());
        setTime(mholder, record.getFeedbackTime());
    }

    private void setTime(PurseHolder mholder, long feedbackTime) {
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy年MM月dd日");
        String strTime = formatTime.format(new Date(feedbackTime));
        String strDate = formatDate.format(new Date(feedbackTime));
        if (Integer.parseInt(formatTime.format("HH")) < 13){
            strTime = "上午 " + strTime;
        }else {
            strTime = "下午 " + strTime;
        }
        mholder.time.setText(strTime);
        mholder.date.setText(strDate);
    }
}
