package com.miuhouse.yourcompany.teacher.view.ui.activity;

import com.miuhouse.yourcompany.teacher.model.PayAccountBean;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseAddWithdrawAccountActivity;

/**
 * 修改账户
 * Created by kings on 7/21/2016.
 */
public class EditorWithdrawAccountActivity extends BaseAddWithdrawAccountActivity {

    private PayAccountBean payAccountBean;

    @Override
    protected void initViewAndEvents() {
        super.initViewAndEvents();
        payAccountBean = (PayAccountBean) getIntent().getSerializableExtra("payAccount");

        if (payAccountBean.getAccountType().equals("1")) {
            tvOptionLift.setSelected(true);
            tvOptionRight.setSelected(false);
        } else {
            tvOptionLift.setSelected(false);
            tvOptionRight.setSelected(true);
        }

        etName.setText(payAccountBean.getAccountName());
        etAccount.setText(payAccountBean.getAccountNo());

    }


    @Override
    public String getBarTitle() {
        return "修改账户";
    }

    @Override
    public String btnTitle() {
        return "保存";
    }

    @Override
    public String getUrlPath() {
        return Constants.URL_VALUE + "accountUpdate";
    }

    @Override
    public String getAccountId() {
        return payAccountBean.getId();
    }

    @Override
    public boolean isAccountId() {
        return true;
    }
}
