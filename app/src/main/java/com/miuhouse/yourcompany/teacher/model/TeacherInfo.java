package com.miuhouse.yourcompany.teacher.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kings on 7/8/2016.
 */
public class TeacherInfo implements Serializable {
    private String teacherId;
    private String tName;
    private String mobile;
    private String token;
    private long tokenTime;
    private double balance; //帐号余额

    private int sex;
    private String college;//院校
    private String profession;//专业
    private int education;//学历
    private int grade;//年级
    private ArrayList<String> pbxType; //订单类型
    private String introduction;//自我介绍
    private String headUrl;//头像
    private ArrayList<String> images;//相册


    public void setId(String id) {
        this.teacherId = teacherId;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenTime(long tokenTime) {
        this.tokenTime = tokenTime;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String gettName() {
        return tName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getToken() {
        return token;
    }

    public long getTokenTime() {
        return tokenTime;
    }

    public double getBalance() {
        return balance;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public ArrayList<String> getPbxType() {
        return pbxType;
    }

    public void setPbxType(ArrayList<String> pbxType) {
        this.pbxType = pbxType;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
