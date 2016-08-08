package com.miuhouse.yourcompany.teacher.presenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.interactor.AddressListInteractor;
import com.miuhouse.yourcompany.teacher.interactor.interf.IAddressInteractor;
import com.miuhouse.yourcompany.teacher.model.AddressListBean;
import com.miuhouse.yourcompany.teacher.presenter.interf.IAddressPresenter;
import com.miuhouse.yourcompany.teacher.view.ui.activity.interf.IAddressView;
import com.miuhouse.yourcompany.teacher.view.widget.ViewOverrideManager;

/**
 * Created by kings on 8/5/2016.
 */
public class AddressPresenter implements IAddressPresenter {

    private IAddressInteractor addressInteractor;
    private IAddressView addressView;

    public AddressPresenter(IAddressView addressView) {
        this.addressView = addressView;
        addressInteractor = new AddressListInteractor();
    }

    @Override
    public void getAddressList() {
//        addressView.showLoading(null);
        addressInteractor.getAddress(new Response.Listener<AddressListBean>() {
            @Override
            public void onResponse(AddressListBean response) {
                addressView.hideLoading();
                if (response.getAddressList().size() == 0) {
                    addressView.showError(ViewOverrideManager.NO_ADDRESS);
                    return;
                }
                addressView.result(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                addressView.hideLoading();
                addressView.showError(ViewOverrideManager.NO_NETWORK);
            }
        });
    }
}
