package com.miuhouse.yourcompany.teacher.model;

import com.amap.api.services.help.Tip;

/**
 * Created by kings on 8/5/2016.
 */
public class MyTip extends Tip {
    @Override
    public String toString() {
        return  this.getAddress();
    }
}
