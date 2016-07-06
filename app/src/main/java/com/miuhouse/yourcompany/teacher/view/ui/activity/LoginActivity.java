package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.model.User;
import com.miuhouse.yourcompany.teacher.presenter.ILoginPresenter;
import com.miuhouse.yourcompany.teacher.presenter.impl.LoginPresenter;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.ILoginView;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseView;

/**
 * Created by kings on 7/1/2016.
 */
public class LoginActivity extends BaseActivity implements ILoginView, View.OnClickListener {

    private EditText etName;
    private EditText etPassword;
    private Button btnLogin;
    private ILoginPresenter loginPresenter;
    private TextView tvRegist;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//       setContentView(R.layout.activity_login);
//    }

    @Override
    protected String setTitle() {
        return "登录";
    }

    @Override
    protected String setRight() {
        return null;
    }

    @Override
    protected void initViewAndEvents() {
        etName = (EditText) findViewById(R.id.edit_user);
        etPassword = (EditText) findViewById(R.id.edit_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        findViewById(R.id.tv_regist).setOnClickListener(this);
        loginPresenter = new LoginPresenter(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }

    @Override
    public void showLoginSuccess(User user) {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_regist:
                regist();
                break;
        }
        ;

    }

    private void login() {
        if (!Util.hasInternet()) {
            Toast.makeText(this, R.string.tip_no_internet, Toast.LENGTH_LONG).show();
        }
        String strPassword = etPassword.getText().toString().trim();
        String strUser = etName.getText().toString().trim();
        if (Util.isEmpty(strUser)) {
            etName.setError("请输入手机号码");
            etName.requestFocus();
            return;
        }
        if (!Util.isMobile(strUser)) {
            etName.setError("手机号码格式不对");
            etName.requestFocus();
            return;
        }
        if (Util.isEmpty(strPassword)) {
            etPassword.setError("请输入密码");
            etPassword.requestFocus();
            return;
        }
        loginPresenter.doLogin(etName.getText().toString(), etPassword.getText().toString());
    }

    private void regist() {
        String strPassword = etPassword.getText().toString().trim();
        String strUser = etName.getText().toString().trim();
        if (Util.isEmpty(strUser)) {
            etName.setError("请输入手机号码");
            etName.requestFocus();
            return;
        }
        if (!Util.isMobile(strUser)) {
            etName.setError("手机号码格式不对");
            etName.requestFocus();
            return;
        }
        if (Util.isEmpty(strPassword)) {
            etPassword.setError("请输入密码");
            etPassword.requestFocus();
            return;
        }
        loginPresenter.doRegist(etName.getText().toString(), etPassword.getText().toString());
    }

}
