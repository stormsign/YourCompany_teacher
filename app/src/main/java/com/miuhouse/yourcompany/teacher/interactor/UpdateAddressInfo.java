package com.miuhouse.yourcompany.teacher.interactor;

import com.android.volley.Response;
import com.miuhouse.yourcompany.teacher.application.App;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.interactor.interf.IUpdateAddressInteractor;
import com.miuhouse.yourcompany.teacher.model.AddressBean;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kings on 8/4/2016.
 */
public class UpdateAddressInfo implements IUpdateAddressInteractor {
    @Override
    public void addAddress(AddressBean address, Response.Listener listener, Response.ErrorListener errorListener) {

        String urlPath = Constants.URL_VALUE + "addAddress";
        Map<String, Object> map = new HashMap<>();
        map.put("teacherId", App.getInstance().getTeacherId());
        map.put("province", address.getProvince());
        map.put("city", address.getCity());
        map.put("district", address.getDistrict());
        map.put("street", address.getStreet());
        map.put("houseNumber", address.getHouseNumber());
        map.put("lon", address.getLon());
        map.put("lat", address.getLat());
        map.put("isDefault", address.getIsDefault());
        VolleyManager.getInstance().sendGsonRequest(null, urlPath, map, SPUtils.getData(SPUtils.TOKEN, null), BaseBean.class, listener, errorListener);
    }

    //    字段名称	数据类型	说明
//    teacherId	String	老师Id
//    addressInfoId	String
    @Override
    public void delAddress(String addressInfoId, Response.Listener<BaseBean> listener, Response.ErrorListener errorListener) {

        String urlPath = Constants.URL_VALUE + "delAddress";
        Map<String, Object> map = new HashMap<>();
        map.put("teacherId", App.getInstance().getTeacherId());
        map.put("addressInfoId", addressInfoId);
        VolleyManager.getInstance().sendGsonRequest(null, urlPath, map, SPUtils.getData(SPUtils.TOKEN, null), BaseBean.class, listener, errorListener);

    }
}
