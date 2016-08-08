package com.miuhouse.yourcompany.teacher.interactor.interf;

import com.android.volley.Response;
import com.miuhouse.yourcompany.teacher.model.AddressListBean;

/**
 * Created by kings on 8/5/2016.
 */
public interface IAddressInteractor {
    void getAddress(Response.Listener<AddressListBean> listener, Response.ErrorListener errorListener);
}
