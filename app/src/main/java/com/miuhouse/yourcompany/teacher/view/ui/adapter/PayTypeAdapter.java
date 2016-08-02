package com.miuhouse.yourcompany.teacher.view.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.listener.OnListItemClick;
import com.miuhouse.yourcompany.teacher.model.PayAccountBean;
import com.miuhouse.yourcompany.teacher.model.TradingRecordBean;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.ui.activity.ChooseWithdrawAccountActivity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseRVAdapter;
import com.miuhouse.yourcompany.teacher.view.widget.ChoiceView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by kings on 7/20/2016.
 */
public class PayTypeAdapter extends BaseAdapter {

    private Activity context;
    private List<PayAccountBean> list = new ArrayList<>();
    private LayoutInflater inflater;
    private int choicePosition;

    public PayTypeAdapter(Activity context, List list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PayAccountBean payAccountBean = list.get(position);
        final ChoiceView view;
        if (convertView == null) {
            view = new ChoiceView(context);
        } else {
            view = (ChoiceView) convertView;
        }
//        if (payAccountBean.getIsDefault().equals("1")) {
//            L.i("TAG", "isDefault=" + payAccountBean.getIsDefault());
//
//            view.setChecked(true);
//        } else {
//            L.i("TAG", "isDefault=" + payAccountBean.getIsDefault());
//
//            view.setChecked(false);
//        }
        String AccountType = null;


        if (payAccountBean.getAccountType().equals("1")) {
            AccountType = "支付宝";
        } else {
            AccountType = "微信";
        }
        view.setTvNameText(AccountType, payAccountBean.getAccountNo());
        view.setEditor(payAccountBean);
        return view;
    }
}
