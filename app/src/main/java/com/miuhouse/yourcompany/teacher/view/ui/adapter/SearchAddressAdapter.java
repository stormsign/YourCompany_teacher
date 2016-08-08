package com.miuhouse.yourcompany.teacher.view.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.help.Tip;
import com.bumptech.glide.Glide;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.model.Folder;
import com.miuhouse.yourcompany.teacher.model.MyTip;
import com.miuhouse.yourcompany.teacher.utils.L;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kings on 8/5/2016.
 */
public class SearchAddressAdapter extends ArrayAdapter<Tip> {
    private Context mContext;
    private LayoutInflater mInflater;

//    private List<String> mList = new ArrayList<>();

    public SearchAddressAdapter(Context context) {
        super(context, R.layout.route_inputs);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//    /**
//     * 设置数据集
//     *
//     * @param folders
//     */
//    public void setData(List<String> folders) {
//        if (folders != null && folders.size() > 0) {
////            mList = folders;
//        } else {
//            mList.clear();
//        }
//        notifyDataSetChanged();
//    }

//    @Override
//    public int getCount() {
//        return mList.size();
//    }

//    @Override
//    public Tip getItem(int position) {
//        return null;
//    }

//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.route_inputs, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.address.setText(mList.get(position).getAddress());
        Tip tip = getItem(position);
        L.i("TAG", "tip=" + tip);
        if (tip != null) {
            holder.address.setText(tip.getAddress());

        }
        return convertView;
    }

    class ViewHolder {

        TextView address;


        ViewHolder(View view) {

            address = (TextView) view.findViewById(R.id.online_user_list_item_textview);
            view.setTag(this);
        }

    }
}