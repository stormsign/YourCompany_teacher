package com.miuhouse.yourcompany.teacher.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的评论列表
 * Created by kings on 7/26/2016.
 */
public class MyEvaluate extends BaseBean implements Serializable {

    private List<Evaluate> evaluates = new ArrayList<>();

    public List<Evaluate> getEvaluates() {
        return evaluates;
    }

    public void setEvaluates(List<Evaluate> evaluates) {
        this.evaluates = evaluates;
    }
}
