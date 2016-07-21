package com.miuhouse.yourcompany.teacher.interactor;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.miuhouse.yourcompany.teacher.http.VolleyManager;
import com.miuhouse.yourcompany.teacher.interactor.interf.ISysMsgInteractor;
import com.miuhouse.yourcompany.teacher.listener.OnLoadCallBack;
import com.miuhouse.yourcompany.teacher.model.BaseBean;
import com.miuhouse.yourcompany.teacher.model.SysMsgEntity;
import com.miuhouse.yourcompany.teacher.utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by khb on 2016/7/14.
 */
public class SysMsgInteractor implements ISysMsgInteractor,
        Response.Listener<SysMsgInteractor.SysMsgBean>, Response.ErrorListener {
    private OnLoadCallBack onLoadCallBack;
    public SysMsgInteractor(OnLoadCallBack onLoadCallBack) {
        this.onLoadCallBack = onLoadCallBack;
    }

    @Override
    public void getMsgs(int page) {
        onLoadCallBack.onPreLoad();
        String url = Constants.URL_VALUE + "noticeMsg";
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", "4028b88155c4dd070155c4dd8a340000");
        params.put("page", page);
        params.put("pageSize", 15);
        VolleyManager.getInstance().sendGsonRequest(null, url, params,
                "6eca806dffed65f70f6d50a3b435069b",
                SysMsgBean.class,
                this, this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        onLoadCallBack.onLoadFailed(error.toString());
    }

    @Override
    public void onResponse(SysMsgBean response) {
        onLoadCallBack.onLoadSuccess(response);
    }

    public class SysMsgBean extends BaseBean{
        List<SysMsgEntity> noticeList;

        public List<SysMsgEntity> getNoticeList() {
            return noticeList;
        }

        public void setNoticeList(List<SysMsgEntity> noticeList) {
            this.noticeList = noticeList;
        }
    }
}
