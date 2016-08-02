package com.miuhouse.yourcompany.teacher.view.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.User;
import com.miuhouse.yourcompany.teacher.presenter.UserInformationPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IUserInformationPresenter;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.ui.activity.AccountBlanceActivity;
import com.miuhouse.yourcompany.teacher.view.ui.activity.MyCommentActivity;
import com.miuhouse.yourcompany.teacher.view.ui.activity.OrdersManageActivity;
import com.miuhouse.yourcompany.teacher.view.ui.activity.SettingActivity;
import com.miuhouse.yourcompany.teacher.view.ui.activity.UserInformationActivity;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IUserInformationView;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IAccountFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by khb on 2016/7/6.
 */
public class AccountFragment extends BaseFragment implements IAccountFragment, IUserInformationView, View.OnClickListener {
    private IUserInformationPresenter userInformationPresenter;
    private TextView tvName;
    private ImageView imgAvatar;
    private TextView tvBalance;
    private SwitchCompat switchcompatAppointment;
    private LinearLayout contentLinear;

    private double balance;
    private String phone;

    @Override
    public int getFragmentResourceId() {
        return R.layout.fragment_account;
    }

    @Override
    public void getViews(View view) {

        view.findViewById(R.id.tv_information).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserInformationActivity.class));

            }
        });
        view.findViewById(R.id.relative_nicename).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, OrdersManageActivity.class));
            }
        });

        view.findViewById(R.id.relative_money).setOnClickListener(this);
        view.findViewById(R.id.relative_comment).setOnClickListener(this);
        view.findViewById(R.id.relative_setting).setOnClickListener(this);

        contentLinear = (LinearLayout) view.findViewById(R.id.content);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvBalance = (TextView) view.findViewById(R.id.tv_money);
        switchcompatAppointment = (SwitchCompat) view.findViewById(R.id.switchcompat_appointment);
        imgAvatar = (ImageView) view.findViewById(R.id.img_avatar);
//        view.findViewById(R.id.tv_title_money).setOnClickListener(this);
        switchcompatAppointment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int index = 0;
                if (isChecked) {
                    index = 1;
                } else {
                    index = 0;
                }
                request(String.valueOf(index));
            }
        });
    }

    public void request(String isChecked) {
        String url = Constants.URL_VALUE + "orderSwitch";
        Map<String, Object> map = new HashMap<>();
        map.put("teacherId", "4028b88155c4dd070155c4dd8a340000");
        map.put("orderSwitch", isChecked);
        VolleyManager.getInstance().sendGsonRequest(null, url, map, "6eca806dffed65f70f6d50a3b435069b", BaseBean.class, new Response.Listener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                L.i("TAG", "response=" + response.getMsg());
                showSnackbar(switchcompatAppointment, response.getMsg());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //初始化失败，继续使用原先保存的
            }
        });
    }

    @Override
    public void setupView() {


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userInformationPresenter = new UserInformationPresenter(this);
        userInformationPresenter.getUserInfo(null);
    }

    @Override
    public View getOverrideParentView() {
        return contentLinear;
    }

    @Override
    public void UpdateSuccess(BaseBean baseBean) {
    }

    @Override
    public void getUserInfo(User user) {
        tvName.setText(user.getTeacherInfo().gettName());
        balance = user.getTeacherInfo().getBalance();
        Glide.with(this).load(user.getTeacherInfo().getHeadUrl()).centerCrop().override(Util.dip2px(getActivity(), 50), Util.dip2px(getActivity(), 50)).into(imgAvatar);
        tvBalance.setText(String.valueOf(balance) + "元");
        phone = user.getTeacherInfo().getMobile();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tv_title_money:
//                break;
            case R.id.relative_money:
                Intent intent = new Intent(getActivity(), AccountBlanceActivity.class);
                intent.putExtra("blance", balance);
                startActivity(intent);
                break;
            case R.id.relative_comment:

                startActivity(new Intent(getActivity(), MyCommentActivity.class));
                break;
            case R.id.relative_setting:
                Intent intentSetting = new Intent(getActivity(), SettingActivity.class);
                intentSetting.putExtra("phone", phone);
                startActivity(intentSetting);
                break;
        }
    }

    private void showSnackbar(final View inputLayout, final String msg) {
        inputLayout.setEnabled(false);
        final Snackbar snackbar = Snackbar.make(inputLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        snackbar.setAction("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                inputLayout.setEnabled(true);

            }
        });
    }

    @Override
    public void showLoading(String msg) {
//        super.showLoading(msg);
        L.i("TAG", "msg=" + msg);
        viewOverrideManager.showLoading(msg, true);
    }
}
