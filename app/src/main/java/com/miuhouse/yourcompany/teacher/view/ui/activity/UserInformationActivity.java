package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.db.DictDBTask;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.HeadUrl;
import com.miuhouse.yourcompany.teacher.model.TeacherInfo;
import com.miuhouse.yourcompany.teacher.model.User;
import com.miuhouse.yourcompany.teacher.presenter.UserInformationPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IUserInformationPresenter;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.utils.MyAsyn;
import com.miuhouse.yourcompany.teacher.utils.Util;
import com.miuhouse.yourcompany.teacher.utils.Values;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IUserInformationView;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.ChoiceAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;
import com.miuhouse.yourcompany.teacher.view.widget.LovelyChoiceDialog;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCrop;

/**
 * Created by kings on 7/8/2016.
 */
public class UserInformationActivity extends BaseActivity implements View.OnClickListener, IUserInformationView, MyAsyn.AsyncResponse {

    private static final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage";
    private static final int USER_NAME = 0;
    private static final int USER_UNIVERSITY = 1;
    private static final int USER_MAJOR = 2;
    private static final int USER_RECOMMEND = 3;
    private static final int REQUEST_IMAGE = 4; //头像

    private static final int USER_GENDERS = 5;
    private static final int USER_GRADES = 6;
    private static final int USER_EDUCATIONS = 7;
    private static final int REQUEST_ALBUM = 8;

    private TextView tvNicename;
    private TextView tvUniversity;
    private TextView tvMajor;
    private TextView tvRecommend;
    private TextView tvGenders;
    private TextView tvGrades;
    private TextView tvEducations;
    private ImageView imgAvatar;
    private TextView tvPbxType;
    private ScrollView content;
    private ProgressBar progressBar;

    private String strUserName;
    private String strUniversity;
    private String strMajor;//专业
    private String strRecommend;
    private String strGenders;
    private String strGrades;
    private String strEducations;
    private String strHeadUrl;

    //保存成功返回给AccountFragment的用户名和头像地址
    private String strRequestName;
    private String strRequestHeadUrl;

    private int gendersPosition;

    private List<String> genders; //性别
    private List<String> grades;  //年级
    private List<String> educations;  //学历
    private ArrayList<String> albumList = new ArrayList<>();
    private ArrayList<String> pbxTypeList = new ArrayList<>();

    private IUserInformationPresenter userInformationPresenter;

    private TeacherInfo teacherInfo;

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

        content = (ScrollView) findViewById(R.id.content);
        tvNicename = (TextView) findViewById(R.id.tv_nicename);
        tvUniversity = (TextView) findViewById(R.id.tv_university);
        tvMajor = (TextView) findViewById(R.id.tv_major);
        tvRecommend = (TextView) findViewById(R.id.tv_recomment);
        tvGenders = (TextView) findViewById(R.id.tv_sex);
        tvGrades = (TextView) findViewById(R.id.tv_grade);
        tvEducations = (TextView) findViewById(R.id.tv_education);
        tvPbxType = (TextView) findViewById(R.id.tv_pbxtype);
        imgAvatar = (ImageView) findViewById(R.id.avatar);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        userInformationPresenter = new UserInformationPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_information;
    }

    @Override
    protected View getOverrideParentView() {
        return content;
    }

    /**
     * 点击提交
     */
    @Override
    public void onRightClick() {
        progressBar.setVisibility(View.VISIBLE);
        int intGenders = Values.getKey(Values.gendersDemand, strGenders);
        int intEducations = DictDBTask.getDcValue("college_grade", strEducations);

        int intGrades = Values.getKey(Values.teacherGrades, strGrades);
        List<Integer> list = new ArrayList<>();
        for (String str : pbxTypeList) {
            if (Values.getKey(Values.majorDemand, str) == 0) {
                list.add(Integer.parseInt(str));
            } else {
                list.add(Values.getKey(Values.majorDemand, str));
            }
        }
        userInformationPresenter.doChangeUserInformation(null, albumList, strUserName, intGenders, strUniversity, strMajor, intEducations, intGrades, list, strRecommend, strHeadUrl);
    }

    /**
     * 获取用户资料
     */
    public void getUserInfo() {
        userInformationPresenter.getUserInfo(null);
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
                intent.putExtra("value", strUserName);
                intent.putExtra("isShow", true);
                startActivityForResult(intent, USER_NAME);
                break;
            case R.id.relative_sex:
                genders = Values.getListValue(Values.gendersDemand);
                showSingleChoiceDialog(strGenders, genders, "性别", USER_GENDERS);
                break;
            case R.id.relative_schollage:
                //类型选择
                showMultChoiceDialog();
                break;
            case R.id.relative_grade:
                grades = Values.getListValue(Values.teacherGrades);
                for (String str : grades) {
                    L.i("TAG", "grades=" + str);
                }
                showSingleChoiceDialog(strGrades, grades, "年级", USER_GRADES);
                break;
            case R.id.relative_education:
                educations = DictDBTask.getDcNameList("college_grade");
                showSingleChoiceDialog(strEducations, educations, "学历", USER_EDUCATIONS);
                break;
            case R.id.relative_university:
                Intent intentUniversity = new Intent(this, ChangeUserNameActivity.class);
                intentUniversity.putExtra("title", "院校");
                intentUniversity.putExtra("value", strUniversity);
                startActivityForResult(intentUniversity, USER_UNIVERSITY);
                break;
            case R.id.relative_major:
                Intent intentMajor = new Intent(this, ChangeUserNameActivity.class);
                intentMajor.putExtra("title", "专业");
                intentMajor.putExtra("value", strMajor);
                startActivityForResult(intentMajor, USER_MAJOR);
                break;
            case R.id.relative_recommend:
                Intent intentRecommend = new Intent(this, ChangeRecommendActivity.class);
                intentRecommend.putExtra("recommend", strRecommend);
                startActivityForResult(intentRecommend, USER_RECOMMEND);
                break;
            case R.id.relative_images:
                Intent intentImages = new Intent(this, PhotoAlbumActivity.class);
                if (teacherInfo.getImages() != null)
                    intentImages.putStringArrayListExtra("images", teacherInfo.getImages());
                startActivityForResult(intentImages, REQUEST_ALBUM);
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
                Uri sourceUri = Uri.fromFile(file);
                String destinationFileName = System.currentTimeMillis() + ".png";
                UCrop.Options oPtions = new UCrop.Options();
                oPtions.setCropFrameColor(getResources().getColor(R.color.themeColor));
                oPtions.setActiveWidgetColor(getResources().getColor(R.color.themeColor));
                oPtions.setStatusBarColor(getResources().getColor(R.color.themeColor));
                oPtions.setToolbarColor(getResources().getColor(R.color.themeColor));
                UCrop.of(sourceUri, Uri.fromFile(new File(getCacheDir(), destinationFileName))).withOptions(oPtions)
                        .withAspectRatio(9, 9)
                        .withMaxResultSize(Util.dip2px(this, 50), Util.dip2px(this, 50))
                        .start(this);
            }
        }
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            final Uri resultUri = UCrop.getOutput(data);
            try {
                File file = new File(new URI(resultUri.toString()));
                Glide.with(this).load(resultUri).centerCrop().override(Util.dip2px(this, 50), Util.dip2px(this, 50)).into(imgAvatar);
                new MyAsyn(this, this, file, "pbx/teacherhead_android").execute();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
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
                case REQUEST_ALBUM:

                    if (data.getStringArrayListExtra(PhotoAlbumActivity.EXTRA_RESULT).size() > 0) {
                        albumList.clear();
                    }
                    albumList.addAll(data.getStringArrayListExtra(PhotoAlbumActivity.EXTRA_RESULT));
                    break;
            }
        }

    }

    private void showSingleChoiceDialog(String selectedStr, List<String> arrays, String title, final int index) {
        ArrayAdapter<String> adapter = new ChoiceAdapter(this, arrays, selectedStr);
        final LovelyChoiceDialog lovelyChoiceDialog = new LovelyChoiceDialog(this);
        lovelyChoiceDialog.setTopColorRes(R.color.backgroundcolor_common).goneIconView().setTitle(title).setItems(adapter, new LovelyChoiceDialog.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(int position, String item) {
                if (index == USER_GENDERS) {
                    strGenders = item;
                    tvGenders.setText(strGenders);
                } else if (index == USER_GRADES) {
                    strGrades = item;
                    tvGrades.setText(strGrades);
                } else if (index == USER_EDUCATIONS) {
                    strEducations = item;
                    tvEducations.setText(strEducations);
                }
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
                        pbxTypeList.clear();
                        pbxTypeList.addAll(items);
                        tvPbxType.setText(getPdxTypeTwo(pbxTypeList));
//
                    }
                })
                .show();
    }

    /**
     * 修改成功
     *
     * @param baseBean
     */
    @Override
    public void UpdateSuccess(BaseBean baseBean) {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }

        Toast.makeText(this, baseBean.getMsg(), Toast.LENGTH_LONG).show();
        if (baseBean.getCode() == 0) {
            strRequestName = tvNicename.getText().toString();
            strRequestHeadUrl = strHeadUrl;
        }
        onBackClick();
    }

    /**
     * 用户资料的返回方法
     * 给控件赋值
     *
     * @param user
     */
    @Override
    public void getUserInfo(User user) {

        teacherInfo = user.getTeacherInfo();
        if (!Util.isEmpty(teacherInfo.getEducation()))
            strEducations = DictDBTask.getDcName("college_grade", Integer.parseInt(teacherInfo.getEducation()));
        if (!Util.isEmpty(teacherInfo.getSex()))
            strGenders = Values.getValue(Values.gendersDemand, Integer.parseInt(teacherInfo.getSex()));
        if (!Util.isEmpty(teacherInfo.getGrade()))
            strGrades = Values.getValue(Values.teacherGrades, Integer.parseInt(teacherInfo.getGrade()));

        strRecommend = teacherInfo.getIntroduction();
        tvRecommend.setText(strRecommend);
        strMajor = teacherInfo.getProfession();
        tvMajor.setText(strMajor);
        strUniversity = teacherInfo.getCollege();
        tvUniversity.setText(strUniversity);
        tvEducations.setText(strEducations);
        tvGenders.setText(strGenders);
        tvGrades.setText(strGrades);
        strUserName = teacherInfo.gettName();
        tvNicename.setText(strUserName);
        strHeadUrl = teacherInfo.getHeadUrl();
        Glide.with(this).load(strHeadUrl).centerCrop().override(Util.dip2px(this, 50), Util.dip2px(this, 50)).into(imgAvatar);
        if (teacherInfo.getPbxType()!=null)
        pbxTypeList.addAll(teacherInfo.getPbxType());
        tvPbxType.setText(getPdxType(pbxTypeList));
        if (teacherInfo.getImages() != null)
            albumList.addAll(teacherInfo.getImages());
    }

    private String getPdxType(List<String> pdxTypeList) {

        StringBuffer result = new StringBuffer();
        for (String str : pdxTypeList) {
            String value = Values.getValue(Values.majorDemand, Integer.parseInt(str));
            result.append(value + " ");
        }
        return result.toString();
    }

    private String getPdxTypeTwo(List<String> pdxTypeList) {

        StringBuffer result = new StringBuffer();
        for (String str : pdxTypeList) {
            result.append(str + " ");
        }
        return result.toString();
    }

    /**
     * 头像上传返回图片路径
     *
     * @param result
     */
    @Override
    public void processFinish(String result) {
        Gson gson = new Gson();
        HeadUrl headUrl = gson.fromJson(result, HeadUrl.class);
        if (headUrl != null && headUrl.getImages().size() > 0) {
            strHeadUrl = headUrl.getImages().get(0);
        }
        L.i("TAG", "strHeadUrl=" + strHeadUrl);
    }

    @Override
    public void processError() {

    }

    @Override
    public void showLoading(String msg) {
//        super.showLoading(msg);
        viewOverrideManager.showLoading(msg, true);
    }

    @Override
    public void request() {
        getUserInfo();
    }

    @Override
    public void onBackClick() {
        Intent intent = new Intent();
        intent.putExtra("name", strRequestName);
        intent.putExtra("headUrl", strRequestHeadUrl);
        setResult(RESULT_OK, intent);
        finish();
    }

//    private void showProgressDialog() {
//        progressDialog = new ProgressDialog(this);
//        progressDialog.show();
//    }
}
