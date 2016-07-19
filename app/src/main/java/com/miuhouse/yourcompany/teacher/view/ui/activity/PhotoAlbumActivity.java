package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.UpdateImageAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by kings on 7/15/2016.
 */
public class PhotoAlbumActivity extends BaseActivity implements UpdateImageAdapter.OnDelectClickListener {
    private static final int REQUEST_IMAGE = 3;
    public static final String EXTRA_RESULT = "select_result";

    private ArrayList<String> mSelectPath;
    private GridView mGridView;
    private UpdateImageAdapter adapter;
    private ArrayList<String> imageList = new ArrayList<>();

    @Override
    protected String setTitle() {
        return "相册";
    }

    @Override
    protected String setRight() {
        return "保存";
    }

    @Override
    protected void initViewAndEvents() {
        mGridView = (GridView) findViewById(R.id.gv_tousu);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (imageList.size() == position)
                    intetnMultiImageSelector();
            }
        });

        imageList.addAll(getIntent().getStringArrayListExtra("images"));
        adapter = new UpdateImageAdapter(this, imageList);
        adapter.setOnDelectClickListener(this);
        adapter.setShape(true);
        mGridView.setAdapter(adapter);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_user_photo_album;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }

    private void intetnMultiImageSelector() {
        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
        int maxNum = 9;
        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
        if (mSelectPath != null && mSelectPath.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                imageList.addAll(mSelectPath);
                adapter.addData(mSelectPath);
            }
        }
    }

    @Override
    public void onRightClick() {
        result();
    }

    @Override
    public void onBackClick() {
        finish();
    }

    private void result() {
        if (adapter.getImageUrls().size() > 0) {
            L.i("TAG", "list=" + adapter.getImageUrls().size());
            Intent intent = new Intent();
            intent.putStringArrayListExtra(EXTRA_RESULT, adapter.getImageUrls());
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public void onDelectClick(int position) {
        imageList.remove(position);
    }
}
