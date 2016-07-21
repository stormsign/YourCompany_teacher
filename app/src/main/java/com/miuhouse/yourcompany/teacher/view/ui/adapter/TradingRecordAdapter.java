package com.miuhouse.yourcompany.teacher.view.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.listener.OnListItemClick;
import com.miuhouse.yourcompany.teacher.model.TradingRecordBean;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseRVAdapter;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by kings on 7/20/2016.
 */
public class TradingRecordAdapter extends BaseRVAdapter {


    public TradingRecordAdapter(Context context, List list, OnListItemClick onListItemClick) {
        super(context, list, onListItemClick);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TradingRecordHolder(LayoutInflater.from(context).inflate(R.layout.item_trading_record, null));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TradingRecordBean tradingRecordBean = (TradingRecordBean) list.get(position);
        TradingRecordHolder tradingRecordHolder = (TradingRecordHolder) holder;
        if (tradingRecordBean.getChangeType().equals("1")) {
            tradingRecordHolder.tvMoney.setText("+" + NumberFormat.getCurrencyInstance(Locale.CHINA).format((tradingRecordBean.getAmount())));
            tradingRecordHolder.tvTitle.setText("订单完成金额");
            tradingRecordHolder.imgIcon.setImageResource(R.mipmap.qianbao_ico_shoukuan);
        } else {
            tradingRecordHolder.tvMoney.setText("-" + NumberFormat.getCurrencyInstance(Locale.CHINA).format((tradingRecordBean.getAmount())));
            tradingRecordHolder.tvTitle.setText("提现金额");
            tradingRecordHolder.imgIcon.setImageResource(R.mipmap.qianbao_ico_tikuan);

        }
        tradingRecordHolder.tvDate.setText(Util.formatTime(tradingRecordBean.getFeedbackTime()));
        tradingRecordHolder.relativeContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListItemClick != null) {
                    onListItemClick.onItemClick(tradingRecordBean);
                }
            }
        });
    }

    static class TradingRecordHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private TextView tvTitle;
        private TextView tvMoney;
        private ImageView imgIcon;
        private RelativeLayout relativeContent;

        public TradingRecordHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_money);
            imgIcon = (ImageView) itemView.findViewById(R.id.img_icon);
            relativeContent = (RelativeLayout) itemView.findViewById(R.id.relative_content);
        }
    }
}
