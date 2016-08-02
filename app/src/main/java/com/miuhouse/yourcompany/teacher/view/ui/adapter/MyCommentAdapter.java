package com.miuhouse.yourcompany.teacher.view.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.model.Evaluate;
import com.miuhouse.yourcompany.teacher.model.OrderEntity;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.utils.Values;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseRVAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by khb on 2016/7/19.
 */
public class MyCommentAdapter extends BaseRVAdapter {


    public MyCommentAdapter(Context context, List<Evaluate> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(context).inflate(R.layout.list_item_mycomment, null));
    }

    class CommentHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvType;
        ImageView imgAvatar;
        TextView tvTime;
        TextView time;
        TextView tvContent;
        LinearLayout content;

        public CommentHolder(View itemView) {
            super(itemView);
            content = (LinearLayout) itemView.findViewById(R.id.relative_content);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvType = (TextView) itemView.findViewById(R.id.tv_type);
            imgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            time = (TextView) itemView.findViewById(R.id.time);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        L.i("position :" + position + " holder : " + holder.toString());
        Evaluate evaluate = (Evaluate) list.get(position);
        CommentHolder commentHolder = (CommentHolder) holder;
        commentHolder.tvName.setText(evaluate.getParentName());
        commentHolder.tvContent.setText(evaluate.getEvaluateContent());
        Glide.with(context).load(evaluate.getParentHeadUrl()).override(Util.dip2px(context, 40), Util.dip2px(context, 40)).placeholder(R.mipmap.ic_launcher).centerCrop().into(commentHolder.imgAvatar);
        commentHolder.tvTime.setText(Util.formatTime(evaluate.getEvaluateTime()));
        commentHolder.tvType.setText(Values.getValue(Values.majorDemand, Integer.parseInt(evaluate.getMajorDemand())));
        switch (Integer.parseInt(evaluate.getMajorDemand())) {
            case 1:
                commentHolder.tvType.setTextColor(context.getResources().getColor(R.color.background_order_1));
                break;
            case 2:
                commentHolder.tvType.setTextColor(context.getResources().getColor(R.color.background_order_2));
                break;
            case 3:
                commentHolder.tvType.setTextColor(context.getResources().getColor(R.color.background_order_3));

                break;
        }
    }


}
