package com.miuhouse.yourcompany.teacher.model;

import java.io.Serializable;

/**
 * 我的评论
 * Created by kings on 7/26/2016.
 */
public class Evaluate implements Serializable {

    private String parentId;
    private String majorDemand; //类型
    private long evaluateTime;  //
    private String evaluateRank;
    private String evaluateContent;
    private String parentName;
    private String parentHeadUrl;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getMajorDemand() {
        return majorDemand;
    }

    public void setMajorDemand(String majorDemand) {
        this.majorDemand = majorDemand;
    }

    public long getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(long evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public String getEvaluateRank() {
        return evaluateRank;
    }

    public void setEvaluateRank(String evaluateRank) {
        this.evaluateRank = evaluateRank;
    }

    public String getEvaluateContent() {
        return evaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentHeadUrl() {
        return parentHeadUrl;
    }

    public void setParentHeadUrl(String parentHeadUrl) {
        this.parentHeadUrl = parentHeadUrl;
    }
}
