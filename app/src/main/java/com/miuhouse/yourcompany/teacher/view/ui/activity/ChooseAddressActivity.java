package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.model.AddressBean;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.presenter.UpdateAddressPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IUpdateAddPresenter;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IUpdateAddressView;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;

/**
 * Created by kings on 8/3/2016.
 */
public class ChooseAddressActivity extends BaseActivity implements IUpdateAddressView {

    private static final int REQUEST = 1;

    private TextView tvAddress;
    private CheckBox checkboxIsDefault;

    private String city;
    private String province;
    private String street;
    private String district;
    private double lon;
    private double lat;
    private AddressBean addressBean;

    private IUpdateAddPresenter updateAddPresenter;

    @Override
    protected String setTitle() {
        return "选择地址";
    }

    @Override
    protected String setRight() {
        return "保存";
    }

    @Override
    protected void initViewAndEvents() {

        tvAddress = (TextView) findViewById(R.id.tv_address);
        checkboxIsDefault = (CheckBox) findViewById(R.id.checkbox_isDefault);

        findViewById(R.id.linear_choose_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ChooseAddressActivity.this, MapActivity.class), REQUEST);
            }
        });
        updateAddPresenter = new UpdateAddressPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_choose_address;
    }

    @Override
    protected View getOverrideParentView() {
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST) {
            city = data.getStringExtra("city");
            province = data.getStringExtra("province");
            street = data.getStringExtra("street");
            district = data.getStringExtra("district");
            lon = data.getDoubleExtra("lon", 0);
            lat = data.getDoubleExtra("lat", 0);
            String houseNumber = data.getStringExtra("houseNumber");
//            String address = data.getStringExtra("address");
//            String address = null;

            addressBean = new AddressBean();
            addressBean.setProvince(province);
            addressBean.setCity(city);
            addressBean.setDistrict(district);
            addressBean.setLat(lat);
            addressBean.setLon(lon);
            addressBean.setStreet(street);
            addressBean.setHouseNumber(houseNumber);
            StringBuffer address = new StringBuffer();
            if (province != null) {
                address.append(addressBean.getProvince());
            }
            if (addressBean.getCity() != null) {
                address.append(addressBean.getCity());
            }
            if (addressBean.getDistrict() != null) {
                address.append(addressBean.getDistrict());
            }
            if (addressBean.getStreet() != null) {
                address.append(addressBean.getStreet());
            }
            if (addressBean.getHouseNumber() != null) {
                address.append(addressBean.getHouseNumber());
            }
            tvAddress.setText(address.toString());

        }
    }

    @Override
    public void onRightClick() {
        if (addressBean != null) {
            L.i("TAG", "isChecked=" + checkboxIsDefault.isChecked());
            if (checkboxIsDefault.isChecked()) {
                addressBean.setIsDefault("1");
            } else {
                addressBean.setIsDefault("0");
            }
            updateAddPresenter.updateAddress(addressBean);
        }
    }

    @Override
    public void UpdateSuccess(BaseBean baseBean) {
        if (baseBean.getCode() == 0) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void delAddressSuccess(BaseBean baseBean) {

    }
}
