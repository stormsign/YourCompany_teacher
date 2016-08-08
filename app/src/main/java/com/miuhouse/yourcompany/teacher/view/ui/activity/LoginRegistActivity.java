package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.application.App;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.User;
import com.miuhouse.yourcompany.teacher.presenter.LoginPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.ILoginPresenter;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.utils.MyCount;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.ILoginView;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.ProductTourFragment;
import com.miuhouse.yourcompany.teacher.view.widget.LovelyCustomDialog;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by kings on 7/6/2016.
 */
public class LoginRegistActivity extends AppCompatActivity implements ILoginView {

    //用来区别是注册还是修改密码
    public static final int TYPE_MARK_RESET = 1;
    public static final int TYPE_MARK_REGIST = 0;

    static final int NUM_PAGES = 3;

    private EditText etName;
    private EditText etPassword;
    private Button login;
    private TextView tvRegist;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private LinearLayout circles;
    private Button btnLogin;
    private Button btnRegist;
    private ProgressBar mProgressbar;
    private TextView tvReset;
    private TextView tvCode;
    private LinearLayout linearCode;
    private EditText etCode;

    private ILoginPresenter loginPresenter;
    boolean isOpaque = true;

    private String etNameStr;
    private String etPasswordStr;
    private String vCode;

    private LovelyCustomDialog mDialog;
    private EventHandler eventHandler;
    private boolean isMsg = false;
    private MyCount mc;
    //用来区别是注册还是修改密码
    private int typeMark = TYPE_MARK_REGIST;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i("LOGIN ACTIVITY");
        if ((getIntent().getFlags()&Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
        int code = getIntent().getIntExtra("code", 0);
        if (code != 1 && App.getInstance().isLogin()) {
//            ActivityManager.getInstance().finishAll();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setContentView(R.layout.activity_tutorial);

        btnLogin = Button.class.cast(findViewById(R.id.btn_login));
        btnRegist = Button.class.cast(findViewById(R.id.btn_regist));
        pager = (ViewPager) findViewById(R.id.pager);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTextViewStr();
                mDialog = new LovelyCustomDialog(LoginRegistActivity.this, R.style.TintTheme);
                mDialog.setView(R.layout.activity_login)
                        .goneView()
                        .show();
                View view = mDialog.getAddedView();
                mDialog.show();
                initDialogView(view);
                LoginHandle();
            }
        });

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTextViewStr();

                mDialog = new LovelyCustomDialog(LoginRegistActivity.this, R.style.TintTheme);

                mDialog.setView(R.layout.activity_login)
                        .goneView()
                        .show();
                View view = mDialog.getAddedView();
                mDialog.show();
                initDialogView(view);
                rigstHandle(TYPE_MARK_REGIST);
            }
        });

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setPageTransformer(true, new CrossfadePageTransformer());
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position == NUM_PAGES - 2 && positionOffset > 0) {
                    if (isOpaque) {
                        pager.setBackgroundColor(Color.TRANSPARENT);
                        isOpaque = false;
                    }
                } else {
                    if (!isOpaque) {
                        pager.setBackgroundColor(getResources().getColor(R.color.primary_material_light));
                        isOpaque = true;
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        loginPresenter = new LoginPresenter(this);

        buildCircles();
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
                                L.i("TAG", "doRegist");
                                loginPresenter.doRegist(typeMark, etNameStr, etPasswordStr);
                            }
                        });
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        isMsg = true;
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
                            showProgressBar(false);
                            Toast.makeText(LoginRegistActivity.this, "验证码验证失败", Toast.LENGTH_LONG).show();
                        }
                    });

                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
        SMSSDK.getSupportedCountries();
    }

    public void initDialogView(View view) {
        mProgressbar = (ProgressBar) view.findViewById(R.id.progressbar);
        etName = (EditText) view.findViewById(R.id.edit_user);
        etPassword = (EditText) view.findViewById(R.id.edit_password);
        login = (Button) view.findViewById(R.id.btn_login);
        linearCode = (LinearLayout) view.findViewById(R.id.linear_code);
        tvReset = (TextView) view.findViewById(R.id.tv_reset);
        tvCode = (TextView) view.findViewById(R.id.tv_code);
        etCode = (EditText) view.findViewById(R.id.edit_code);

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etNameStr = s.toString();
                if (Util.isEmpty(etNameStr)) {
                    login.setEnabled(false);
                } else {
                    if (Util.isMobile(etNameStr)) {
                        if (Util.isEmpty(etPasswordStr)) {
                            login.setEnabled(false);

                        } else {
                            login.setEnabled(true);

                        }
                    } else {
                        login.setEnabled(false);

                    }


                }
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etPasswordStr = s.toString();
                if (Util.isEmpty(etPasswordStr.toString())) {
                    etPassword.setError("密码不能为空");
                    login.setEnabled(false);
                    return;
                } else {
                    if (Util.isEmpty(etNameStr)) {
                        etName.setError("手机号码不能为空");
                        etName.findFocus();
                        login.setEnabled(false);
                        return;
                    }
                    if (!Util.isMobile(etNameStr)) {
                        etName.setError("手机号码格式不对哦");
                        etName.findFocus();
                        login.setEnabled(false);
                        return;
                    }
                    login.setEnabled(true);
                }
            }
        });

    }


    private void LoginHandle() {
        if (linearCode.getVisibility() == View.VISIBLE) {
            linearCode.setVisibility(View.GONE);
        }
        tvReset.setVisibility(View.VISIBLE);
        login.setText(R.string.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                clearTextViewStr();

                mDialog = new LovelyCustomDialog(LoginRegistActivity.this, R.style.TintTheme);

                mDialog.setView(R.layout.activity_login)
                        .goneView()
                        .show();
                View view = mDialog.getAddedView();
                initDialogView(view);
                rigstHandle(TYPE_MARK_RESET);
            }
        });

    }

    private void rigstHandle(final int typeMark) {
        this.typeMark = typeMark;
        if (linearCode.getVisibility() == View.GONE) {
            linearCode.setVisibility(View.VISIBLE);
        }
        if (typeMark == TYPE_MARK_REGIST)
            login.setText(R.string.regist);
        else
            login.setText("修改密码");
        tvReset.setVisibility(View.GONE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regsit();
            }
        });
        tvCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode();
            }
        });

        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                vCode = s.toString();
                if (Util.isEmpty(vCode)) {
                    etCode.setError("验证码不能为空");
                    etCode.findFocus();
                    login.setEnabled(false);
                    return;
                } else {
                    if (Util.isEmpty(etNameStr)) {
                        etName.setError("手机号码不能为空");
                        etName.findFocus();
                        login.setEnabled(false);
                        return;
                    }
                    if (!Util.isMobile(etNameStr)) {
                        etName.setError("手机号码格式不对哦");
                        etName.findFocus();
                        login.setEnabled(false);
                        return;
                    }
                    if (Util.isEmpty(etPasswordStr)) {
                        etPassword.setError("密码不能为空");
                        etPassword.findFocus();
                        login.setEnabled(false);
                        return;
                    }
                    login.setEnabled(true);

                }
            }
        });
    }

    private void clearTextViewStr() {
        etNameStr = null;
        etPasswordStr = null;
        vCode = null;
    }

    private void sendCode() {
        if (Util.isEmpty(etNameStr)) {
            etName.setError("请输入手机号码");
            etName.requestFocus();
            return;
        }
        if (!Util.isMobile(etNameStr)) {
            etName.setError("手机号码格式不对");
            etName.requestFocus();
            return;
        }
        SMSSDK.getVerificationCode("86", etNameStr);
        if (mc == null) {
            mc = new MyCount(60000, 1000, tvCode, this); // 第一参数是总的时间，第二个是间隔时间 都是毫秒为单位
        }
        mc.start();
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
        showProgressBar(true);
        loginPresenter.doLogin(etName.getText().toString(), etPassword.getText().toString());
    }

    private void regsit() {

        if (!Util.hasInternet()) {
            Toast.makeText(this, R.string.tip_no_internet, Toast.LENGTH_LONG).show();
        }

        etPasswordStr = etPassword.getText().toString().trim();
        etNameStr = etName.getText().toString().trim();
        vCode = etCode.getText().toString();

        if (Util.isEmpty(etNameStr)) {
            etName.setError("请输入手机号码");
            etName.requestFocus();
            return;
        }
        if (!Util.isMobile(etNameStr)) {
            etName.setError("手机号码格式不对");
            etName.requestFocus();
            return;
        }
        if (Util.isEmpty(etPasswordStr)) {
            etPassword.setError("请输入密码");
            etPassword.requestFocus();
            return;
        }
        if (Util.isEmpty(vCode)) {
            etCode.setError("请输入验证码");
            return;
        }

        showProgressBar(true);
        SMSSDK.submitVerificationCode(Constants.SMSSDK_COUNTRYCODE, etNameStr, vCode);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pager != null) {
            pager.clearOnPageChangeListeners();
        }
        if (mDialog != null)
            mDialog.dismiss();
    }

    private void buildCircles() {
        circles = LinearLayout.class.cast(findViewById(R.id.circles));

        float scale = getResources().getDisplayMetrics().density;
        int padding = (int) (5 * scale + 0.5f);

        for (int i = 0; i < NUM_PAGES; i++) {
            ImageView circle = new ImageView(this);
            circle.setImageResource(R.mipmap.ic_swipe_indicator_white_18dp);
            circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            circle.setAdjustViewBounds(true);
            circle.setPadding(padding, 0, padding, 0);
            circles.addView(circle);
        }

        setIndicator(0);
    }

    private void setIndicator(int index) {
        if (index < NUM_PAGES) {
            for (int i = 0; i < NUM_PAGES; i++) {
                ImageView circle = (ImageView) circles.getChildAt(i);
                if (i == index) {
                    circle.setColorFilter(getResources().getColor(R.color.text_selected));
                } else {
                    circle.setColorFilter(getResources().getColor(android.R.color.transparent));
                }
            }
        }
    }

    private void endTutorial() {
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }

    @Override
    public void showLoginSuccess(User user) {
        showProgressBar(false);
        mDialog.dismiss();
        if (user != null && user.getCode() == 0) {
//            ActivityManager.getInstance().finishAll();
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, user.getMsg(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, user.getMsg(), Toast.LENGTH_LONG).show();
        }
        finish();
    }

    @Override
    public void showRegistSuccess(BaseBean baseBean) {

        if (baseBean.getCode() == 0) {
            login();
        } else {
            showProgressBar(false);
            Toast.makeText(this, baseBean.getMsg(), Toast.LENGTH_LONG).show();
        }
    }

    private void showProgressBar(boolean show) {
        mProgressbar.setVisibility(show ? View.VISIBLE : View.GONE);
        login.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void showError(int type) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void hideError() {

    }

    @Override
    public void netError() {
        showProgressBar(false);
    }

    @Override
    public void onTokenExpired() {

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ProductTourFragment tp = null;
            switch (position) {
                case 0:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment01);
                    break;
                case 1:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment02);
                    break;
                case 2:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment03);
                    break;
                case 3:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment5);
                    break;
            }

            return tp;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public class CrossfadePageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();

            View backgroundView = page.findViewById(R.id.welcome_fragment);
            View text_head = page.findViewById(R.id.heading);
            View text_content = page.findViewById(R.id.content);
            View welcomeImage01 = page.findViewById(R.id.welcome_01);
            View welcomeImage02 = page.findViewById(R.id.welcome_02);
            View welcomeImage03 = page.findViewById(R.id.welcome_03);

            if (0 <= position && position < 1) {
                ViewHelper.setTranslationX(page, pageWidth * -position);
            }
            if (-1 < position && position < 0) {
                ViewHelper.setTranslationX(page, pageWidth * -position);
            }

            if (position <= -1.0f || position >= 1.0f) {
            } else if (position == 0.0f) {
            } else {
                if (backgroundView != null) {
                    ViewHelper.setAlpha(backgroundView, 1.0f - Math.abs(position));

                }

                if (text_head != null) {
                    ViewHelper.setTranslationX(text_head, pageWidth * position);
                    ViewHelper.setAlpha(text_head, 1.0f - Math.abs(position));
                }

                if (text_content != null) {
                    ViewHelper.setTranslationX(text_content, pageWidth * position);
                    ViewHelper.setAlpha(text_content, 1.0f - Math.abs(position));
                }

                if (welcomeImage01 != null) {
                    ViewHelper.setTranslationX(welcomeImage01, (float) (pageWidth / 2 * position));
                    ViewHelper.setAlpha(welcomeImage01, 1.0f - Math.abs(position));
                }

                if (welcomeImage02 != null) {
                    ViewHelper.setTranslationX(welcomeImage02, (float) (pageWidth / 2 * position));
                    ViewHelper.setAlpha(welcomeImage02, 1.0f - Math.abs(position));
                }

                if (welcomeImage03 != null) {
                    ViewHelper.setTranslationX(welcomeImage03, (float) (pageWidth / 2 * position));
                    ViewHelper.setAlpha(welcomeImage03, 1.0f - Math.abs(position));
                }
            }
        }
    }

}