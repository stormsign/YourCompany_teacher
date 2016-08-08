package com.miuhouse.yourcompany.teacher.interactor;

import com.android.volley.Response;
import com.miuhouse.yourcompany.teacher.application.App;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.interactor.interf.IAddressInteractor;
import com.miuhouse.yourcompany.teacher.model.AddressListBean;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kings on 8/5/2016.
 */
public class AddressListInteractor implements IAddressInteractor {
    @Override
    public void getAddress(Response.Listener<AddressListBean> listener, Response.ErrorListener errorListener) {
        String urlPath = Constants.URL_VALUE + "addressList";
        Map<String, Object> map = new HashMap<>();
        map.put("teacherId", App.getInstance().getTeacherId());
        VolleyManager.getInstance().sendGsonRequest(null, urlPath, map, SPUtils.getData(SPUtils.TOKEN, ""), AddressListBean.class, listener, errorListener);
    }
}
