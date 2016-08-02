package com.miuhouse.yourcompany.teacher.view.ui.base;

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

import java.util.HashMap;
import java.util.Map;

/**
 * 添加账户
 * Created by kings on 7/21/2016.
 */
public abstract class BaseAddWithdrawAccountActivity extends BaseActivity {

    public EditText etName;
    public EditText etAccount;
    public TextView tvOptionLift;
    public TextView tvOptionRight;
    public Button btnAdd;

    @Override
    protected String setTitle() {
        return getBarTitle();
    }

    @Override
    protected String setRight() {
        return null;
    }

    @Override
    protected void initViewAndEvents() {
        etName = (EditText) findViewById(R.id.et_name);
        tvOptionLift = (TextView) findViewById(R.id.tv_option_left);
        tvOptionRight = (TextView) findViewById(R.id.tv_option_right);
        etAccount = (EditText) findViewById(R.id.et_account);
        btnAdd = (Button) findViewById(R.id.btn_add);

        btnAdd.setText(btnTitle());



        tvOptionLift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOptionLift.setSelected(true);
                tvOptionRight.setSelected(false);

            }
        });
        tvOptionRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOptionLift.setSelected(false);
                tvOptionRight.setSelected(true);

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });
    }

    private void sendRequest() {
        String strName = etName.getText().toString();
        String strAccount = etAccount.getText().toString();
        int payType = 1;
        if (Util.isEmpty(strName)) {
            etName.setError("请输入账户姓名");
            etName.requestFocus();
            return;
        }
        if (Util.isEmpty(strAccount)) {
            etAccount.setError("请输入提现账户");
            etAccount.requestFocus();
            return;
        }
        if (tvOptionLift.isSelected()) {
            payType = 1;
        } else {
            payType = 2;
        }
        String urlPath = getUrlPath();
        Map<String, Object> map = new HashMap<>();
        map.put("teacherId", "4028b88155c4dd070155c4dd8a340000");
        if (isAccountId()) {
            map.put("id", getAccountId());
        }
        map.put("accountType", payType);
        map.put("accountNo", strAccount);
        map.put("accountName", strName);
        VolleyManager.getInstance().sendGsonRequest(null, urlPath, map, "6eca806dffed65f70f6d50a3b435069b", BaseBean.class, new Response.Listener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                Toast.makeText(context, response.getMsg(), Toast.LENGTH_LONG).show();
                setResult(RESULT_OK);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_add_withdraw_account;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }

    /**
     * @return
     */
    public abstract String getBarTitle();

    public abstract String btnTitle();

    public abstract String getUrlPath();

    public abstract String getAccountId();

    public abstract boolean isAccountId();
}
