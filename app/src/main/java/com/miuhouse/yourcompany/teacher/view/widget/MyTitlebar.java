package com.miuhouse.yourcompany.teacher.view.widget;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.miuhouse.yourcompany.teacher.R;


/**
 * Created by khb on 2016/6/30.
 */
public class MyTitlebar extends FrameLayout {

    private Context context;
    private TextView title;
    private TextView right;
    private Toolbar titlebar;

    public MyTitlebar(Context context) {
        this(context, null);
    }

    public MyTitlebar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public MyTitlebar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_mytitlebar, null);
        titlebar = (Toolbar) view.findViewById(R.id.titlebar);
        title = (TextView) view.findViewById(R.id.title);
        right = (TextView) view.findViewById(R.id.right);
        right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRightClick();
            }
        });
        addView(view);
    }

    public void setTitle(String titleText){
        title.setText(titleText);
    }

    public void setRightButtonText(String text){
        right.setText(text);
    }

    public void onRightClick(){

    }

}
