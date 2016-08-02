package com.miuhouse.yourcompany.teacher.view.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.model.PayAccountBean;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.activity.ChooseWithdrawAccountActivity;
import com.miuhouse.yourcompany.teacher.view.ui.activity.EditorWithdrawAccountActivity;

/**
 * Created by kings on 7/22/2016.
 */
public class ChoiceView extends FrameLayout implements Checkable {
    private TextView tvName;
    private TextView tvAccount;
    private RadioButton radioButton;
    public ImageView imgEditor;
    public Activity context;

    public ChoiceView(Activity context) {
        super(context);
        this.context = context;
        View.inflate(context, R.layout.item_choice_paytype, this);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvAccount = (TextView) findViewById(R.id.tv_account);
        radioButton = (RadioButton) findViewById(R.id.radio_choice);
        imgEditor = (ImageView) findViewById(R.id.img_editor);
    }

    public void setTvNameText(String text, String account) {
        tvName.setText(text);
        tvAccount.setText(account);
    }

    public void setEditor(final PayAccountBean payAccountBean) {
        imgEditor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditorWithdrawAccountActivity.class);
                intent.putExtra("payAccount", payAccountBean);
                context.startActivityForResult(intent, ChooseWithdrawAccountActivity.REQUEST);
            }
        });
    }

    @Override
    public void setChecked(boolean checked) {
        radioButton.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return radioButton.isChecked();
    }

    @Override
    public void toggle() {
        radioButton.toggle();
    }
}
