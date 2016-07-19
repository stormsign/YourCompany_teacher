package com.miuhouse.yourcompany.teacher.view.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.listener.IUserInformationView;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.User;
import com.miuhouse.yourcompany.teacher.presenter.UserInformationPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IUserInformationPresenter;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.ui.activity.OrdersManageActivity;
import com.miuhouse.yourcompany.teacher.view.ui.activity.UserInformationActivity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseFragment;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.interf.IAccountFragment;

/**
 * Created by khb on 2016/7/6.
 */
public class AccountFragment extends BaseFragment implements IAccountFragment, IUserInformationView {
    private IUserInformationPresenter userInformationPresenter;
    private TextView tvName;
    private ImageView imgAvatar;

    @Override
    public int getFragmentResourceId() {
        return R.layout.fragment_account;
    }

    @Override
    public void getViews(View view) {
//        view.findViewById(R.id.tv_account).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), LoginRegistActivity.class));
//
//            }
//        });
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
        tvName = (TextView) view.findViewById(R.id.tv_name);
        imgAvatar = (ImageView) view.findViewById(R.id.img_avatar);
    }

    @Override
    public void setupView() {
        userInformationPresenter = new UserInformationPresenter(this);
        userInformationPresenter.getUserInfo(null);
    }

    @Override
    public View getOverrideParentView() {
        return null;
    }

    @Override
    public void UpdateSuccess(BaseBean baseBean) {

    }

    @Override
    public void getUserInfo(User user) {
        tvName.setText(user.getTeacherInfo().gettName());
        Glide.with(this).load(user.getTeacherInfo().getHeadUrl()).centerCrop().override(Util.dip2px(getActivity(), 50), Util.dip2px(getActivity(), 50)).into(imgAvatar);

    }
}
