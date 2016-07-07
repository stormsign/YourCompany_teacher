package com.miuhouse.yourcompany.teacher.view.ui.activity;

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
import com.miuhouse.yourcompany.teacher.model.User;

import com.miuhouse.yourcompany.teacher.presenter.LoginPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.ILoginPresenter;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.ILoginView;
import com.miuhouse.yourcompany.teacher.view.ui.fragment.ProductTourFragment;
import com.miuhouse.yourcompany.teacher.view.widget.LovelyCustomDialog;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by kings on 7/6/2016.
 */
public class LoginRegistActivity extends AppCompatActivity implements ILoginView {

    static final int NUM_PAGES = 3;

    private EditText etName;
    private EditText etPassword;
    private Button login;
    private ILoginPresenter loginPresenter;
    private TextView tvRegist;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private LinearLayout circles;
    private Button btnLogin;
    private Button btnRegist;
    private ProgressBar mProgressbar;
    private TextView tvReset;
    boolean isOpaque = true;
    private String etNameStr;
    private String etPasswordStr;
    private LovelyCustomDialog mDialog;
    private LinearLayout linearCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setContentView(R.layout.activity_tutorial);
        btnLogin = Button.class.cast(findViewById(R.id.btn_login));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        btnRegist = Button.class.cast(findViewById(R.id.btn_regist));
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = new LovelyCustomDialog(LoginRegistActivity.this, R.style.TintTheme);

                mDialog.setView(R.layout.activity_login)
                        .goneView()
                        .show();

                View view = mDialog.getAddedView();

                mDialog.show();
                initDialogView(view);
                rigstHandle();
            }
        });

        pager = (ViewPager) findViewById(R.id.pager);
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
    }


    public void initDialogView(View view) {
        mProgressbar = (ProgressBar) view.findViewById(R.id.progressbar);
        etName = (EditText) view.findViewById(R.id.edit_user);
        etPassword = (EditText) view.findViewById(R.id.edit_password);
        login = (Button) view.findViewById(R.id.btn_login);
        linearCode = (LinearLayout) view.findViewById(R.id.linear_code);
        tvReset = (TextView) view.findViewById(R.id.tv_reset);
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
                    etPassword.setError("请输入密码");

                    login.setEnabled(false);
                    return;
                } else {
                    if (Util.isEmpty(etNameStr)) {
                        etName.setError("请输入手机号码");
                        login.setEnabled(false);
                        return;
                    }
                    if (!Util.isMobile(etNameStr)) {
                        etName.setError("手机号码格式不对");
                        login.setEnabled(false);
                        return;
                    }
                    login.setEnabled(true);
                }
            }
        });
    }

    //        findViewById(R.id.tv_regist).setOnClickListener(this);
//        btnLogin.setOnClickListener(this);
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
                showProgressBar(true);
            }
        });

    }

    private void rigstHandle() {
        if (linearCode.getVisibility() == View.GONE) {
            linearCode.setVisibility(View.VISIBLE);
        }
        login.setText(R.string.regist);
        tvReset.setVisibility(View.GONE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                showProgressBar(true);
            }
        });

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pager != null) {
            pager.clearOnPageChangeListeners();
        }
    }

    private void buildCircles() {
        circles = LinearLayout.class.cast(findViewById(R.id.circles));

        float scale = getResources().getDisplayMetrics().density;
        int padding = (int) (5 * scale + 0.5f);

        for (int i = 0; i < NUM_PAGES ; i++) {
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
        if (user != null) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
        }
    }

    private void showProgressBar(boolean show) {
        mProgressbar.setVisibility(show ? View.VISIBLE : View.GONE);
        login.setVisibility(show ? View.GONE : View.VISIBLE);
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