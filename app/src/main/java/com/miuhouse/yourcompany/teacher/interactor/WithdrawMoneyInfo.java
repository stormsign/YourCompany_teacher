package com.miuhouse.yourcompany.teacher.interactor;

import com.android.volley.Response;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.interactor.interf.IWithdrawMoney;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.SPUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kings on 7/21/2016.
 */
public class WithdrawMoneyInfo implements IWithdrawMoney {
    @Override
    public void getWithdrawMoney(String teacherId, String account, String accountName, BigDecimal amount, String accountType, Response.Listener listener, Response.ErrorListener errorListener) {
        String urlPath = Constants.URL_VALUE + "withdraw";
        Map<String, Object> map = new HashMap<>();
        map.put("teacherId", "4028b88155c4dd070155c4dd8a340000");
        map.put("account", account);
        map.put("accountName", accountName);
        map.put("amount", amount);
        map.put("accountType", accountType);
        VolleyManager.getInstance().sendGsonRequest(null, urlPath, map, SPUtils.getData(SPUtils.TOKEN, null), BaseBean.class, listener, errorListener);
    }
}
