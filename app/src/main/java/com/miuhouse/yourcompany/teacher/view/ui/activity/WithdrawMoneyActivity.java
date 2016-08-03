package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.db.AccountDBTask;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.WithdrawAccountBean;
import com.miuhouse.yourcompany.teacher.presenter.WithdrawMoneyPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IWithdrawMoneyPresenter;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IWithdrawMoneyView;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseView;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by kings on 7/21/2016.
 */
public class WithdrawMoneyActivity extends BaseActivity implements IWithdrawMoneyView {

    private static final int REQUEST = 1;

    private IWithdrawMoneyPresenter withdrawMoneyPresenter;

    private EditText etWithdrawMoney;
    private RelativeLayout relativeWithdrawAccount;
    private Button btnWithdrawMoney;
    private TextView tvTitle;
    private TextView tvAccount;
    private TextView tvAllWithdraw;

    private String accountId;
    private String accountName;
    private String accountType;
    private String currentAmount = "";
    private double blance;

    @Override
    protected String setTitle() {
        return "提现";
    }

    @Override
    protected String setRight() {
        return null;
    }

    @Override
    protected void initViewAndEvents() {

        blance = getIntent().getDoubleExtra("blance", 00.00);

        tvAllWithdraw = (TextView) findViewById(R.id.tv_all_withdraw);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        etWithdrawMoney = (EditText) findViewById(R.id.et_withdraw_money);
        relativeWithdrawAccount = (RelativeLayout) findViewById(R.id.relative_withdraw_account);
        btnWithdrawMoney = (Button) findViewById(R.id.btn_withdraw_money);
        tvAccount = (TextView) findViewById(R.id.tv_account);

        tvTitle.setText(String.format("全部金额%s元", String.valueOf(blance)));

        etWithdrawMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(currentAmount)) {
                    etWithdrawMoney.removeTextChangedListener(this);
//                    String replaceable = String.format("[%s, \\s.]", NumberFormat.getCurrencyInstance(Locale.CHINA).getCurrency().getSymbol(Locale.CHINA));
//                    String cleanString = s.toString().replaceAll(replaceable, "");
                    String cleanString = s.toString();
//                       cleanString.length()-cleanString.indexOf(".")>2;
                    L.i("TAG", "length=" + String.valueOf(cleanString.length() - cleanString.indexOf(".")));
                    if (cleanString.length() - cleanString.indexOf(".") > 2) {
                        return;
                    }
                    if (cleanString.equals("") || new BigDecimal(cleanString).toString().equals("0")) {
                        etWithdrawMoney.setText(null);
                    } else {
                        L.i("TAG", "cleanString=" + cleanString);
//                        double parsed = Double.parseDouble(cleanString);
//                        String formatted = NumberFormat.getCurrencyInstance(Locale.CHINA).format(parsed/100);
//                        currentAmount = formatted;
                        etWithdrawMoney.setText(cleanString);
                        etWithdrawMoney.setSelection(cleanString.length());
                    }
                    etWithdrawMoney.addTextChangedListener(this);
                }
            }
        });

        relativeWithdrawAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(WithdrawMoneyActivity.this, ChooseWithdrawAccountActivity.class), REQUEST);
            }
        });
        btnWithdrawMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String replaceable = String.format("[%s, \\s.]", NumberFormat.getCurrencyInstance(Locale.CHINA).getCurrency().getSymbol(Locale.CHINA));
                String cleanString = etWithdrawMoney.getText().toString();
                L.i("TAG", "cleanString=" + cleanString);

                withdrawMoneyPresenter.getWithDrawMoney(null, accountId, accountName, new BigDecimal(cleanString), accountType);

            }
        });
        tvAllWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money = NumberFormat.getCurrencyInstance(Locale.CHINA).format((blance));
                etWithdrawMoney.setText(money);
            }
        });
        withdrawMoneyPresenter = new WithdrawMoneyPresenter(this);
        sendRequest();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_withdraw_money;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }

    @Override
    public void getResult(BaseBean baseBean) {
        Toast.makeText(this, baseBean.getMsg(), Toast.LENGTH_LONG).show();
    }

    public void sendRequest() {
        String urlPath = Constants.URL_VALUE + "getAccountDefault";
        Map<String, Object> map = new HashMap<>();
        map.put("teacherId", AccountDBTask.getAccount().getId());
        VolleyManager.getInstance().sendGsonRequest(null, urlPath, map, SPUtils.getData(SPUtils.TOKEN, null), WithdrawAccountBean.class, new Response.Listener<WithdrawAccountBean>() {
            @Override
            public void onResponse(WithdrawAccountBean response) {
                if (response.getAccount() != null) {
                    String payType = null;
                    if (response.getAccount().getAccountType().equals("1")) {
                        payType = "支付宝";
                    } else {
                        payType = "微信";
                    }
                    accountId = response.getAccount().getAccountNo();
                    accountName = response.getAccount().getAccountName();
                    accountType = response.getAccount().getAccountType();
                    tvAccount.setText(payType + "  " + response.getAccount().getAccountNo());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            sendRequest();
        }
    }
}
