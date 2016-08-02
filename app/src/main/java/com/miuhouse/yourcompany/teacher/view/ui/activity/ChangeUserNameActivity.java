package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

import org.w3c.dom.Text;

/**
 * Created by kings on 7/11/2016.
 */
public class ChangeUserNameActivity extends BaseActivity {
    private String strNiceName;
    private EditText editText;
    private boolean isShow;

    @Override
    protected String setTitle() {
        return "更改姓名";
    }

    @Override
    protected String setRight() {
        return "保存";
    }

    @Override
    protected void initViewAndEvents() {
        String title = getIntent().getStringExtra("title");
        isShow = getIntent().getBooleanExtra("isShow", false);
        strNiceName = getIntent().getStringExtra("value");

        setLeftText("更改" + title);

        editText = (EditText) findViewById(R.id.edit_user);
        final TextView tvHind = (TextView) findViewById(R.id.tv_hint);
        editText.setHint(title);
        editText.setText(strNiceName);

        if (isShow)
            tvHind.setVisibility(View.VISIBLE);
        else
            tvHind.setVisibility(View.GONE);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_change_username;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }

    @Override
    public void onRightClick() {
//        super.onRightClick();
        strNiceName = editText.getText().toString();

        getIntent().putExtra("value", strNiceName);
        Log.i("TAG", "nicename=" + strNiceName);
        this.setResult(RESULT_OK, getIntent());
        finish();

    }
}
