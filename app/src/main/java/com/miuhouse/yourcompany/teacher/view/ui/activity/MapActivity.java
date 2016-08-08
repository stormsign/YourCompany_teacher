package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.model.NaviPara;
import com.amap.api.maps2d.overlay.PoiOverlay;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.model.MyTip;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.adapter.SearchAddressAdapter;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kings on 8/3/2016.
 */
public class MapActivity extends BaseActivity implements View.OnClickListener, AMap.OnMarkerClickListener, AMap.InfoWindowAdapter, TextWatcher,
        PoiSearch.OnPoiSearchListener, Inputtips.InputtipsListener, LocationSource, AMapLocationListener {

    private AMap aMap;
    //    private EditText locationEdit;
    private String keyWord = "";// 要输入的poi搜索关键字
    //    private EditText editCity;// 要输入的城市名字或者城市区号
    private AutoCompleteTextView searchText;// 输入搜索关键字
    private ProgressDialog progDialog = null;// 搜索时进度条

    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private String city;
    private String province;
    private String district;
    private String street;
    private String houseNumber;
    private double lon;
    private double lat;
    private String address;
    private List<String> listString;
    private List<Tip> tipLists = new ArrayList<>();

    @Override
    protected String setTitle() {
        return "选择地址";
    }

    @Override
    protected String setRight() {
        return "保存";
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map)).getMap();
            setUpMap();
        }
    }

    @Override
    protected void initViewAndEvents() {
        init();
    }

    /**
     * 设置页面监听
     */
    private void setUpMap() {
//        Button searButton = (Button) findViewById(R.id.searchButton);
//        locationEdit = (EditText) findViewById(R.id.location_edit);
//        searButton.setOnClickListener(this);
//        Button nextButton = (Button) findViewById(R.id.nextButton);
//        nextButton.setOnClickListener(this);
        searchText = (AutoCompleteTextView) findViewById(R.id.keyWord);
        searchText.addTextChangedListener(this);// 添加文本输入框监听事件
//        editCity = (EditText) findViewById(R.id.city);
        aMap.setOnMarkerClickListener(this);// 添加点击marker监听事件
        aMap.setInfoWindowAdapter(this);// 添加显示infowindow监听事件

        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        searchText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                L.i("TAG", "位置=" + tipLists.get(position).getAddress());
                street = tipLists.get(position).getAddress();
                lat = tipLists.get(position).getPoint().getLatitude();
                lon = tipLists.get(position).getPoint().getLongitude();
            }
        });
//      searchText.addTextChangedListener(new );
    }


    /**
     * 点击搜索按钮
     */
    public void searchButton() {
        keyWord = checkEditText(searchText);
//        keyWord = locationEdit.getText().toString();
        if ("".equals(keyWord)) {
//            ToastUtil.show(PoiKeywordSearchActivity.this, "请输入搜索关键字");
            return;
        } else {
            doSearchQuery();
        }
    }

    /**
     * 判断edittext是否null
     */
    public String checkEditText(EditText editText) {
        if (editText != null && editText.getText() != null
                && !(editText.getText().toString().trim().equals(""))) {
            return editText.getText().toString().trim();
        } else {
            return "";
        }
    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在搜索:\n" + keyWord);
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        showProgressDialog();// 显示进度框
        city = city != null ? city : "深圳";
        currentPage = 0;
        query = new PoiSearch.Query(keyWord, "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        query.setCityLimit(true);
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.searchButton:
//                searchButton();
//                break;
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
//        View view = getLayoutInflater().inflate(R.layout.poikeywordsearch_uri, null);
//        TextView title = (TextView) view.findViewById(R.id.title);
//        title.setText(marker.getTitle());
//
//        TextView snippet = (TextView) view.findViewById(R.id.snippet);
//        snippet.setText(marker.getSnippet());
//        ImageButton button = (ImageButton) view
//                .findViewById(R.id.start_amap_app);
//        // 调起高德地图app
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startAMapNavi(marker);
//            }
//        });
        return null;
    }

    /**
     * 调起高德地图导航功能，如果没安装高德地图，会进入异常，可以在异常中处理，调起高德地图app的下载页面
     */
    public void startAMapNavi(Marker marker) {
        // 构造导航参数
        NaviPara naviPara = new NaviPara();
        // 设置终点位置
        naviPara.setTargetPoint(marker.getPosition());
        // 设置导航策略，这里是避免拥堵
        naviPara.setNaviStyle(AMapUtils.DRIVING_AVOID_CONGESTION);

        // 调起高德地图导航
        try {
            AMapUtils.openAMapNavi(naviPara, getApplicationContext());
        } catch (com.amap.api.maps2d.AMapException e) {

            // 如果没安装会进入异常，调起下载页面
            AMapUtils.getLatestAMapApp(getApplicationContext());

        }

    }

    /**
     * 判断高德地图app是否已经安装
     */
    public boolean getAppIn() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = this.getPackageManager().getPackageInfo(
                    "com.autonavi.minimap", 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        // 本手机没有安装高德地图app
        if (packageInfo != null) {
            return true;
        }
        // 本手机成功安装有高德地图app
        else {
            return false;
        }
    }

    /**
     * 获取当前app的应用名字
     */
    public String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(
                    getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName = (String) packageManager
                .getApplicationLabel(applicationInfo);
        return applicationName;
    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
//        ToastUtil.show(PoiKeywordSearchActivity.this, infomation);

    }

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        L.i("TAG", "tipList=" + tipList.size());
        if (rCode == 1000) {// 正确返回
            listString = new ArrayList<String>();
            tipLists.addAll(tipList);
            for (int i = 0; i < tipList.size(); i++) {


                listString.add(tipList.get(i).getAddress());
//                listString.add(tipList.get(i).getDistrict());
//                listString.add(tipList.get(i).get);
//                listString.add(tipList.get(i).getPoint().getLatitude());


                L.i("TAG", "name=" + tipList.get(i).getName());
                L.i("TAG", "getAddress=" + tipList.get(i).getAddress());
                L.i("TAG", "getDistrict=" + tipList.get(i).getDistrict());


            }

            ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                    getApplicationContext(),
                    R.layout.route_inputs, listString);
//            SearchAddressAdapter adapter = new SearchAddressAdapter(this);
//            for (Tip tip : tipList) {
////                MyTip myTip = tip
//                adapter.add(tip);
//            }
            searchText.setAdapter(aAdapter);

            aAdapter.notifyDataSetChanged();

        } else {
//            ToastUtil.showerror(PoiKeywordSearchActivity.this, rCode);
        }


//    }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return false;
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        dissmissProgressDialog();// 隐藏对话框

        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    L.i("TAG", "poiItems=" + poiItems.get(0).getLatLonPoint().getLatitude());
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
                        aMap.clear();// 清理之前的图标
                        PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
//                        ToastUtil.show(PoiKeywordSearchActivity.this,
//                                R.string.no_result);
                    }
                }
            } else {
//                ToastUtil.show(PoiKeywordSearchActivity.this,
//                        R.string.no_result);
            }
        } else {
//            ToastUtil.showerror(PoiKeywordSearchActivity.this, rCode);
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        if (!IsEmptyOrNullString(newText)) {
            city = city != null ? city : "深圳";
            InputtipsQuery inputquery = new InputtipsQuery(newText, "深圳");
            Inputtips inputTips = new Inputtips(MapActivity.this, inputquery);
            inputTips.setInputtipsListener(this);
            inputTips.requestInputtipsAsyn();
        }
    }

    public boolean IsEmptyOrNullString(String s) {
        return (s == null) || (s.trim().length() == 0);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation); //
                city = aMapLocation.getCity();
//                aMapLocation.getAddress();
//                aMapLocation.getLocationDetail();
                province = aMapLocation.getProvince();
//                aMapLocation.getDistrict();
                district = aMapLocation.getDistrict();
                street = aMapLocation.getStreet();
                lon = aMapLocation.getLongitude();
                lat = aMapLocation.getLatitude();
                address = aMapLocation.getAddress();
                houseNumber = aMapLocation.getStreetNum();
//       L.i("TAG","streetNum="+aMapLocation.get);
                StringBuffer sf = new StringBuffer();
                sf.append(province).append(city).append(district).append(street).append(houseNumber);
                searchText.setHint(sf.toString());
                deactivate();
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                L.i("TAG", "errText=" + errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();

            mlocationClient.setLocationListener(this);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onRightClick() {
        if (lon == 0 || lat == 0) {
            Toast.makeText(this, "获取地址失败", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("city", city);
        intent.putExtra("province", province);
        intent.putExtra("district", district);
        intent.putExtra("street", street);
        intent.putExtra("lon", lon);
        intent.putExtra("lat", lat);
        intent.putExtra("houseNumber", houseNumber);
        setResult(RESULT_OK, intent);
        finish();
    }
}
