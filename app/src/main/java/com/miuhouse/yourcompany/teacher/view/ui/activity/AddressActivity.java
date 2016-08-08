package com.miuhouse.yourcompany.teacher.view.ui.activity;

import android.content.Intent;
import android.location.Address;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.miuhouse.yourcompany.teacher.R;
import com.miuhouse.yourcompany.teacher.model.AddressBean;
import com.miuhouse.yourcompany.teacher.model.AddressListBean;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.presenter.AddressPresenter;
import com.miuhouse.yourcompany.teacher.presenter.UpdateAddressPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IAddressPresenter;
import com.miuhouse.yourcompany.teacher.presenter.interf.IUpdateAddPresenter;
import com.miuhouse.yourcompany.teacher.utils.L;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IAddressView;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IUpdateAddressView;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseActivity;
import com.miuhouse.yourcompany.teacher.view.widget.LovelyStandardDialog;
import com.miuhouse.yourcompany.teacher.view.widget.MyListView;
import com.miuhouse.yourcompany.teacher.view.widget.ViewOverrideManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址管理
 * Created by kings on 8/3/2016.
 */
public class AddressActivity extends BaseActivity implements IAddressView, IUpdateAddressView {

    private static final int REQUEST = 1;
    private IAddressPresenter addressPresenter;

    private ListView mlistV;
    private View view;
    private TextView tvDefaultAddress;
    private LinearLayout linearContent;
    //修改
    private IUpdateAddPresenter updateAddPresenter;
    private List<String> list;
    private List<AddressBean> addressList = new ArrayList<>();
    private int delPostion;
    private ArrayAdapter<String> adapter;


    @Override
    protected String setTitle() {
        return "地址管理";
    }

    @Override
    protected String setRight() {
        return "添加地址";
    }

    @Override
    protected void initViewAndEvents() {
        mlistV = (ListView) findViewById(R.id.lv_address);
        view = this.getLayoutInflater().inflate(R.layout.list_head_layout, null);
        tvDefaultAddress = (TextView) view.findViewById(R.id.tv_default_address);
        linearContent = (LinearLayout) findViewById(R.id.linear_content);
        mlistV.addHeaderView(view);

        mlistV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    return;
                }
                AddressBean addressBean = addressList.get(position - 1);
                addressBean.setIsDefault("1");
                updateAddPresenter.updateAddress(addressBean);
//                finish();
            }
        });
        mlistV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    showDelDialog(position);
                }
                return true;
            }
        });
        addressPresenter = new AddressPresenter(this);
        updateAddPresenter = new UpdateAddressPresenter(this);
        addressPresenter.getAddressList();
    }

    public void showDelDialog(final int position) {
        new LovelyStandardDialog(this)
                .setButtonsColorRes(R.color.darkDeepOrange)
                .setMessage("确定删除这条地址吗？").goneView()
                .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delPostion = position - 1;
                        updateAddPresenter.delAddress(addressList.get(position - 1).getId());
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    protected View getOverrideParentView() {
        return linearContent;
    }

    @Override
    public void onRightClick() {
        startActivityForResult(new Intent(this, ChooseAddressActivity.class), REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST) {
            addressPresenter.getAddressList();
        }
    }

    @Override
    public void result(AddressListBean addressListBean) {
        list = new ArrayList<>();
        addressList.addAll(addressListBean.getAddressList());
        for (int i = 0; i < addressListBean.getAddressList().size(); i++) {
            AddressBean addressBean = addressListBean.getAddressList().get(i);
            StringBuffer address = new StringBuffer();
            if (addressBean.getProvince() != null) {
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
            if (addressBean.getIsDefault().equals("1")) {
                tvDefaultAddress.setText(address);
            }
            list.add(address.toString());
        }
        adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                R.layout.route_inputs, list);
        mlistV.setAdapter(adapter);

    }

    @Override
    public void UpdateSuccess(BaseBean baseBean) {
        if (baseBean.getCode() == 0) {
            Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void delAddressSuccess(BaseBean baseBean) {
        if (baseBean.getCode() == 0) {
            list.remove(delPostion);
            addressList.remove(delPostion);
            adapter.notifyDataSetChanged();
        }

        Toast.makeText(this, baseBean.getMsg(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(int type) {

        if (type == ViewOverrideManager.NO_ADDRESS) {
            viewOverrideManager.showLoading(type, new ViewOverrideManager.OnExceptionalClick() {
                @Override
                public void onExceptionalClick() {
                    startActivityForResult(new Intent(AddressActivity.this, ChooseAddressActivity.class), REQUEST);

                }
            });
        }
    }


}
