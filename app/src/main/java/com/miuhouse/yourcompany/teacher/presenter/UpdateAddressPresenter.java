package com.miuhouse.yourcompany.teacher.presenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.interactor.UpdateAddressInfo;
import com.miuhouse.yourcompany.teacher.interactor.interf.IUpdateAddressInteractor;
import com.miuhouse.yourcompany.teacher.model.AddressBean;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.presenter.interf.IUpdateAddPresenter;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IUpdateAddressView;
import com.miuhouse.yourcompany.teacher.view.widget.ViewOverrideManager;

/**
 * Created by kings on 8/4/2016.
 */
public class UpdateAddressPresenter implements IUpdateAddPresenter {

    private IUpdateAddressInteractor updateAddressInter;
    private IUpdateAddressView updateAddressView;

    public UpdateAddressPresenter(IUpdateAddressView updateAddressView) {
        updateAddressInter = new UpdateAddressInfo();
        this.updateAddressView = updateAddressView;
    }

    @Override
    public void updateAddress(AddressBean address) {
        updateAddressInter.addAddress(address, new Response.Listener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                updateAddressView.UpdateSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                updateAddressView.hideError();
            }
        });
    }

    @Override
    public void delAddress(String addressInfoId) {
        updateAddressInter.delAddress(addressInfoId, new Response.Listener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                updateAddressView.delAddressSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                updateAddressView.showError(ViewOverrideManager.NO_NETWORK);
            }
        });
    }
}
