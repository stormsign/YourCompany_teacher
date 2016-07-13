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
public class UserInformationActivity extends BaseActivity implements View.OnClickListener {
    private static final int USER_NAME = 0;
    private TextView tvNicename;
    private String strUserName;
    private int gendersPosition;
    private String[] genders = {"男", "女"};
    private String[] grades = {"大一", "大二", "大三", "大四"};
    private String[] educations = {"专科", "本科", "硕士", "博士"};

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
        tvNicename = (TextView) findViewById(R.id.tv_nicename);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_information;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.relative_nicename:
                startActivityForResult(new Intent(this, ChangeUserNameActivity.class), USER_NAME);
//                startActivityForResult(new Intent(this, ChangeUserNameActivity.class));
                break;
            case R.id.relative_sex:
                showSingleChoiceDialog(genders, "性别");
                break;
            case R.id.relative_schollage:
                showMultChoiceDialog();
                break;
            case R.id.relative_grade:
//                star.iterator().
//                Values.stars.
//                Values.stars
                showSingleChoiceDialog(grades, "年级");
                break;
            case R.id.relative_education:
                Object[]  star =Values.stars.keySet().toArray();
                showSingleChoiceDialog(educations, "学历");
                break;
            case R.id.relative_university:
                startActivityForResult(new Intent(this, ChangeUserNameActivity.class), USER_NAME);

                break;
            case R.id.relative_major:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case USER_NAME:
                    L.i("TAG", "data=" + data);

                    strUserName = data.getStringExtra("nicename");
                    L.i("TAG", "strUserName=" + strUserName);
                    tvNicename.setText(strUserName);
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
                L.i("TAG", "position=" + position);
            }
        }).show();
    }

    private void showMultChoiceDialog() {
        String[] items = {"陪伴", "作业辅导", "兴趣爱好"};
        new LovelyChoiceDialog(this, R.style.CheckBoxTintTheme)
//                .setTopColorRes(R.color.darkRed)
                .setTitle("上课类型").goneIconView()
//                .setIcon(R.drawable.ic_food_white_36dp)
//                .setInstanceStateHandler(ID_MULTI_CHOICE_DIALOG, saveStateHandler)
                .setItemsMultiChoice(items, new LovelyChoiceDialog.OnItemsSelectedListener<String>() {
                    @Override
                    public void onItemsSelected(List<Integer> positions, List<String> items) {
                        L.i("TAG", "items=" + items.size());
//                        Toast.makeText(UserInformationActivity.this,
//                                getString(R.string.you_ordered, TextUtils.join("\n", items)),
//                                Toast.LENGTH_SHORT)
//                                .show();
                    }
                })
//                .setConfirmButtonText(R.string.confirm)
//                .setSavedInstanceState(savedInstanceState)
                .show();
    }
}
