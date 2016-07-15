package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.listener.IUserInformationView;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.HeadUrl;
import com.miuhouse.yourcompany.teacher.presenter.UserInformationPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IUserInformationPresenter;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.utils.MyAsyn;
import com.miuhouse.yourcompany.teacher.utils.Values;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.ChoiceAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;
import com.miuhouse.yourcompany.teacher.view.widget.LovelyChoiceDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import com.yalantis.ucrop.UCrop;
//import com.yalantis.ucrop.UCrop;

/**
 * Created by kings on 7/8/2016.
 */
public class UserInformationActivity extends BaseActivity implements View.OnClickListener, IUserInformationView, MyAsyn.AsyncResponse {
    private static final int USER_NAME = 0;
    private static final int USER_UNIVERSITY = 1;
    private static final int USER_MAJOR = 2;
    private static final int USER_RECOMMEND = 3;

    private static final int REQUEST_IMAGE = 4; //相册和头像

    private TextView tvNicename;
    private TextView tvUniversity;
    private TextView tvMajor;
    private TextView tvRecommend;
    private ImageView imgAvatar;

    private String strUserName;
    private String strUniversity;
    private String strMajor;
    private String strRecommend;
    private String strGenders;

    private int gendersPosition;

    private List<String> genders;
    private List<String> grades;
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
        imgAvatar = (ImageView) findViewById(R.id.avatar);
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
                startActivityForResult(intentAvatar, REQUEST_IMAGE);
                break;
            case R.id.relative_nicename:
                Intent intent = new Intent(this, ChangeUserNameActivity.class);
                intent.putExtra("title", "姓名");
                intent.putExtra("isShow", true);
                startActivityForResult(intent, USER_NAME);
                break;
            case R.id.relative_sex:
                genders = Values.getListValue(Values.gendersDemand);
                showSingleChoiceDialog(genders, "性别");
                break;
            case R.id.relative_schollage:
                showMultChoiceDialog();
                break;
            case R.id.relative_grade:

                grades = Values.getListValue(Values.teacherGrades);
                showSingleChoiceDialog(grades, "年级");
                break;
            case R.id.relative_education:

                showSingleChoiceDialog(Arrays.asList(educations), "学历");
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
                startActivityForResult(intentRecommend, USER_RECOMMEND);
                break;
            case R.id.relative_images:
                Intent intentImages = new Intent(this, PhotoAlbumActivity.class);
//                intentImages.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
//                intentImages.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                startActivityForResult(intentImages, REQUEST_IMAGE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        //选择图片返回的数据，然后跳转到裁剪页面
        if (requestCode == REQUEST_IMAGE) {
            L.i("TAG", "onActivityResult");
            if (resultCode == RESULT_OK) {
                List<String> mSelectPath = new ArrayList<>();
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();
                for (String p : mSelectPath) {
                    sb.append(p);
                }
//                mResultText.setText(sb.toString());
                L.i("TAG", "sb.toString()=" + sb.toString());
                File file = new File(sb.toString());

//                UCrop.Options options = new UCrop.Options();
//                options.setToolbarColor(ActivityCompat.getColor(activity, R.color.themeColor));
//                options.setStatusBarColor(ActivityCompat.getColor(activity, R.color.themeColor));
//                options.setActiveWidgetColor(ActivityCompat.getColor(activity, R.color.themeColor));
//                options.setCropFrameColor(ActivityCompat.getColor(activity, R.color.themeColor));
//                File outFile = new File(getCacheDir(), System.currentTimeMillis() + ".jpg");
//                //裁剪后图片的绝对路径
//                String cameraScalePath = outFile.getAbsolutePath();
//                Uri destinationUri = Uri.fromFile(outFile);
//                UCrop.of(Uri.fromFile(file), Uri.fromFile(outFile))
//                        .withAspectRatio(16, 16)
//                        .withMaxResultSize(200, 200).withOptions(options)
//                        .start(this);
            }
        }
//        //裁剪返回的数据
//        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
//            final Uri resultUri = UCrop.getOutput(data);
//            L.i("TAG", "resultUri=" + resultUri);
//            File file = null;
//            try {
//                file = new File(new URI(resultUri.toString()));
//                Glide.with(this).load(resultUri).centerCrop().override(Util.dip2px(this, 50), Util.dip2px(this, 50)).into(imgAvatar);
//                L.i("TAG", "file.getPath=" + file.getPath());
//
//                Bitmap bitmap = Util.getBitmapByPath(file.getPath());
//                String body = Base64.encode(Util.Bitmap2Bytes(bitmap));
//
//                MyAsyn myAsyn = new MyAsyn(this, this, file.getPath(), "pbx/teacherhead_android");
//
//                myAsyn.execute();
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//        } else if (resultCode == UCrop.RESULT_ERROR) {
//            final Throwable cropError = UCrop.getError(data);
//        }
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

    private void showSingleChoiceDialog(List<String> arrays, String title) {
        ArrayAdapter<String> adapter = new ChoiceAdapter(this, arrays, gendersPosition);
        final LovelyChoiceDialog lovelyChoiceDialog = new LovelyChoiceDialog(this);
        lovelyChoiceDialog.setTopColorRes(R.color.backgroundcolor_common).goneIconView().setTitle(title).setItems(adapter, new LovelyChoiceDialog.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(int position, String item) {
                gendersPosition = position;
            }
        }).show();
    }

    private void showMultChoiceDialog() {
        List<String> items = Values.getListValue(Values.majorDemand);
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

    @Override
    public void processFinish(String result) {
        L.i("TAG", "result=" + result);
        Gson gson = new Gson();
        HeadUrl headUrl = gson.fromJson(result, HeadUrl.class);
    }

    @Override
    public void processError() {

    }
}
