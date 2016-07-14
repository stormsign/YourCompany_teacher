package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.listener.IUserInformationView;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.presenter.UserInformationPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IUserInformationPresenter;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.utils.Values;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.ChoiceAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;
import com.miuhouse.yourcompany.teacher.view.widget.LovelyChoiceDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by kings on 7/8/2016.
 */
public class UserInformationActivity extends BaseActivity implements View.OnClickListener, IUserInformationView {
    private static final int USER_NAME = 0;
    private static final int USER_UNIVERSITY = 1;
    private static final int USER_MAJOR = 2;
    private static final int USER_RECOMMEND = 3;
    private static final int USER_AVATAR = 4;
    private static final int USER_IMAGES = 5;

    private TextView tvNicename;
    private TextView tvUniversity;
    private TextView tvMajor;
    private TextView tvRecommend;

    private String strUserName;
    private String strUniversity;
    private String strMajor;
    private String strRecommend;
    private String strGenders;

    private int gendersPosition;

    private String[] genders = {"男", "女"};
    private String[] grades;
    private String[] educations = {"专科", "本科", "硕士", "博士"};

    private IUserInformationPresenter userInformationPresenter;

    @Override
    protected String setTitle() {
        return "我的资料";
    }

    @Override
    protected String setRight() {
        return "提交";
    }

    @Override
    protected void initViewAndEvents() {
        findViewById(R.id.relative_nicename).setOnClickListener(this);
        findViewById(R.id.relative_sex).setOnClickListener(this);
        findViewById(R.id.relative_schollage).setOnClickListener(this);
        findViewById(R.id.relative_grade).setOnClickListener(this);
        findViewById(R.id.relative_education).setOnClickListener(this);
        findViewById(R.id.relative_university).setOnClickListener(this);
        findViewById(R.id.relative_major).setOnClickListener(this);
        findViewById(R.id.relative_recommend).setOnClickListener(this);
        findViewById(R.id.relative_avatar).setOnClickListener(this);
        findViewById(R.id.relative_images).setOnClickListener(this);

        tvNicename = (TextView) findViewById(R.id.tv_nicename);
        tvUniversity = (TextView) findViewById(R.id.tv_university);
        tvMajor = (TextView) findViewById(R.id.tv_major);
        tvRecommend = (TextView) findViewById(R.id.tv_recomment);
        userInformationPresenter = new UserInformationPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_information;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }

    /**
     * 点击提交
     */
    @Override
    public void onRightClick() {
        userInformationPresenter.doChangeUserInformation(null, strUserName, "男", strUniversity, strMajor, "本科", "大一", null, strRecommend, null);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.relative_avatar:
                Intent intentAvatar = new Intent(this, MultiImageSelectorActivity.class);
                intentAvatar.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
                intentAvatar.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                startActivityForResult(intentAvatar, USER_AVATAR);
//                mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, 9);
//                mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
//                isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);
                break;
            case R.id.relative_nicename:
                Intent intent = new Intent(this, ChangeUserNameActivity.class);
                intent.putExtra("title", "姓名");
                startActivityForResult(intent, USER_NAME);
                break;
            case R.id.relative_sex:

                showSingleChoiceDialog(genders, "性别");
                break;
            case R.id.relative_schollage:
                showMultChoiceDialog();
                break;
            case R.id.relative_grade:
                grades = Values.getArrayValue(Values.teacherGrades);
                showSingleChoiceDialog(grades, "年级");
                break;
            case R.id.relative_education:

                Object[] star = Values.stars.keySet().toArray();
                showSingleChoiceDialog(educations, "学历");
                break;
            case R.id.relative_university:

                Intent intentUniversity = new Intent(this, ChangeUserNameActivity.class);
                intentUniversity.putExtra("title", "院校");
                startActivityForResult(intentUniversity, USER_UNIVERSITY);
                break;
            case R.id.relative_major:

                Intent intentMajor = new Intent(this, ChangeUserNameActivity.class);
                intentMajor.putExtra("title", "专业");
                startActivityForResult(intentMajor, USER_MAJOR);
                break;
            case R.id.relative_recommend:
                Intent intentRecommend = new Intent(this, ChangeRecommendActivity.class);
                startActivity(intentRecommend);
                startActivityForResult(intentRecommend, USER_RECOMMEND);
                break;
            case R.id.relative_images:
                Intent intentImages = new Intent(this, MultiImageSelectorActivity.class);
                intentImages.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                intentImages.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                startActivityForResult(intentImages, USER_IMAGES);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case USER_NAME:
                    strUserName = data.getStringExtra("value");
                    tvNicename.setText(strUserName);
                    break;
                case USER_UNIVERSITY:
                    strUniversity = data.getStringExtra("value");
                    tvUniversity.setText(strUniversity);
                    break;
                case USER_MAJOR:
                    strMajor = data.getStringExtra("value");
                    tvMajor.setText(strMajor);
                    break;
                case USER_RECOMMEND:
                    strRecommend = data.getStringExtra("value");
                    tvRecommend.setText(strRecommend);
                    break;
            }
        }
    }

    private void showSingleChoiceDialog(String[] strs, String title) {
        ArrayAdapter<String> adapter = new ChoiceAdapter(this, Arrays.asList(strs), gendersPosition);
        final LovelyChoiceDialog lovelyChoiceDialog = new LovelyChoiceDialog(this);
        lovelyChoiceDialog.setTopColorRes(R.color.backgroundcolor_common).goneIconView().setTitle(title).setItems(adapter, new LovelyChoiceDialog.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(int position, String item) {
                gendersPosition = position;
            }
        }).show();
    }

    private void showMultChoiceDialog() {
        String[] items = {"陪伴", "作业辅导", "兴趣爱好"};
        new LovelyChoiceDialog(this, R.style.CheckBoxTintTheme)
                .setTitle("上课类型").goneIconView()
//                .setInstanceStateHandler(ID_MULTI_CHOICE_DIALOG, saveStateHandler)
                .setItemsMultiChoice(items, new LovelyChoiceDialog.OnItemsSelectedListener<String>() {
                    @Override
                    public void onItemsSelected(List<Integer> positions, List<String> items) {
                        L.i("TAG", "items=" + items.size());
//
                    }
                })
//                .setConfirmButtonText(R.string.confirm)
//                .setSavedInstanceState(savedInstanceState)
                .show();
    }

    /**
     * 修改成功
     *
     * @param baseBean
     */
    @Override
    public void UpdateSuccess(BaseBean baseBean) {

    }
}
