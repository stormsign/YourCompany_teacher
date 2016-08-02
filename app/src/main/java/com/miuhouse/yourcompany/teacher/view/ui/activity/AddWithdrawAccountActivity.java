package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseAddWithdrawAccountActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加账户
 * Created by kings on 7/21/2016.
 */
public class AddWithdrawAccountActivity extends BaseAddWithdrawAccountActivity {


    @Override
    protected void initViewAndEvents() {
        super.initViewAndEvents();

        tvOptionLift.setSelected(true);
        tvOptionRight.setSelected(false);

    }


    @Override
    public String getBarTitle() {
        return "新增账户";
    }

    @Override
    public String btnTitle() {
        return "添加";
    }

    @Override
    public String getUrlPath() {
        return Constants.URL_VALUE + "account";
    }

    @Override
    public String getAccountId() {
        return null;
    }

    @Override
    public boolean isAccountId() {
        return false;
    }
}
