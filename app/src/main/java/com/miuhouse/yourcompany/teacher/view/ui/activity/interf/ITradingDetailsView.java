package com.miuhouse.yourcompany.teacher.view.ui.activity.interf;

import com.miuhouse.yourcompany.teacher.model.TradingDetailsBean;
import com.miuhouse.yourcompany.teacher.model.TradingRecordListBean;
import com.miuhouse.yourcompany.teacher.view.ui.base.BaseView;

/**
 * 这个接口封装的方法基本上都跟视图展示有关。
 * Created by kings on 7/1/2016.
 */
public interface ITradingDetailsView extends BaseView{
    void getTradingDetails(TradingDetailsBean tradingDetailsBean);
}
