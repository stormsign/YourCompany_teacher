package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

/**
 * Created by kings on 7/11/2016.
 */
public class ChangeRecommendActivity extends BaseActivity {
    private String strNiceName;
    private EditText editText;

    @Override
    protected String setTitle() {
        return "老师介绍";
    }

    @Override
    protected String setRight() {
        return "保存";
    }

    @Override
    protected void initViewAndEvents() {

        editText = (EditText) findViewById(R.id.edit_recommend);
        String recommend = getIntent().getStringExtra("recommend");
        editText.setText(recommend);
        if (!Util.isEmpty(recommend)) {
            editText.setSelection(recommend.length());
        }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_change_recomment;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }

    @Override
    public void onRightClick() {
        strNiceName = editText.getText().toString();
        getIntent().putExtra("value", strNiceName);
        this.setResult(RESULT_OK, getIntent());
        finish();

    }
}
