package com.miuhouse.yourcompany.teacher.model;

import java.io.Serializable;

/**
 * Created by kings on 7/21/2016.
 */
public class TradingDetailsBean extends BaseBean implements Serializable {

    private RecordBean record;

    public RecordBean getRecord() {
        return record;
    }

    public void setRecord(RecordBean record) {
        this.record = record;
    }
}
