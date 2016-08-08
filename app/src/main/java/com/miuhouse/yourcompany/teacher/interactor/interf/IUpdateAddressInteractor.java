package com.miuhouse.yourcompany.teacher.interactor.interf;

import com.android.volley.Response;
import com.miuhouse.yourcompany.teacher.model.AddressBean;
import com.miuhouse.yourcompany.teacher.model.BaseBean;


/**
 * Created by kings on 8/4/2016.
 */
public interface IUpdateAddressInteractor {
    void addAddress(AddressBean address, Response.Listener listener, Response.ErrorListener errorListener);

    void delAddress(String addressInfoId, Response.Listener<BaseBean> listener, Response.ErrorListener errorListener);
}
