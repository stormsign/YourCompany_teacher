package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.User;
import com.miuhouse.yourcompany.teacher.presenter.UserInformationPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IUserInformationPresenter;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.utils.MyCount;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IUserInformationView;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by kings on 7/27/2016.
 */
public class ReplacePhoneActivity extends BaseActivity implements IUserInformationView {

    private MyCount mc;
    private EventHandler eventHandler;
    private IUserInformationPresenter userInformationPresenter;

    private TextView tvCurrentPhone;
    private EditText etReplacePhone;
    private EditText etCode;
    private TextView tvGetVcode;

    private String phone;
    private String currentPhone;

    @Override
    protected String setTitle() {
        return "更改手机号";
    }

    @Override
    protected String setRight() {
        return "保存";
    }

    @Override
    protected void initViewAndEvents() {

        phone = getIntent().getStringExtra("phone");

        userInformationPresenter = new UserInformationPresenter(this);

        tvCurrentPhone = (TextView) findViewById(R.id.tv_current);
        etReplacePhone = (EditText) findViewById(R.id.et_replace_phone);
        etCode = (EditText) findViewById(R.id.et_code);
        tvGetVcode = (TextView) findViewById(R.id.tv_get_vcode);
        if (phone != null) {
            phone = phone.replace(phone.substring(3, 7), "****");
        }
        tvCurrentPhone.setText(phone);

        etReplacePhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvGetVcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode();
            }
        });
        initSms();
    }

    private void initSms() {
        SMSSDK.initSDK(this, Constants.SMSSDK_APP_KEY, Constants.SMSSDK_APP_SECRET);
        eventHandler = new EventHandler() {
            @Override
            public void onRegister() {
                super.onRegister();
            }

            @Override
            public void onUnregister() {
                super.onUnregister();
            }

            @Override
            public void beforeEvent(int i, Object o) {
                super.beforeEvent(i, o);
            }

            @Override
            public void afterEvent(int event, int result, Object data) {
                Log.w("TAG", "event = " + event + "  result = " + result);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Map<String, Object> map = (HashMap<String, Object>) data;
                        Log.w("TAG", "EVENT_SUBMIT_VERIFICATION_CODE " + map.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                userInformationPresenter.updatePhone(null, currentPhone);
                            }
                        });
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
//                        isMsg = true;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                            showToast(getResources().getString(R.string.verifycode_sent));
                            }
                        });
                        Log.w("TAG", "get code success");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                        ArrayList<HashMap<String, Object>> countryList = (ArrayList<HashMap<String, Object>>) data;
                        Log.d("TAG", "countryList = " + countryList.toString());
                    }
                } else {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(activity, "验证码验证失败", Toast.LENGTH_LONG).show();
//                        progress.dismissAllowingStateLoss();
                        }
                    });

                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
        SMSSDK.getSupportedCountries();
    }

    public void sendCode() {
        currentPhone = etReplacePhone.getText().toString();
        L.i("TAG", "currentPhone=" + currentPhone);
        if (Util.isEmpty(currentPhone)) {
            etReplacePhone.setError("手机号不能为空");
            etReplacePhone.requestFocus();
            return;
        }
        if (!Util.isMobile(currentPhone)) {
            etReplacePhone.setError("手机号格式不对");
            etReplacePhone.requestFocus();
            return;
        }
        SMSSDK.getVerificationCode("86", currentPhone);

        if (mc == null) {
            mc = new MyCount(60000, 1000, tvGetVcode, this); // 第一参数是总的时间，第二个是间隔时间 都是毫秒为单位
        }
        mc.start();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_replear_phone;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }

    @Override
    public void onRightClick() {
        String vCode = etCode.getText().toString().trim();
        if (Util.isEmpty(vCode)) {
            etCode.setError("验证码为空");
            return;
        }
        SMSSDK.submitVerificationCode(Constants.SMSSDK_COUNTRYCODE, currentPhone, vCode);
    }

    @Override
    public void UpdateSuccess(BaseBean baseBean) {
        Toast.makeText(this, baseBean.getMsg(), Toast.LENGTH_LONG);
        if (baseBean.getCode() == 0) {
            tvCurrentPhone.setText(currentPhone);
        }
    }

    /**
     * 为空
     *
     * @param user
     */
    @Override
    public void getUserInfo(User user) {

    }
}
