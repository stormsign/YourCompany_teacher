package com.miuhouse.yourcompany.teacher.model;

import java.io.Serializable;

/**
 * Created by khb on 2016/7/7.
 */
public class OrderEntity implements Serializable {
    String id;
    String parentId;
    String teacherId;
    String tname;       //老师名字
    String userHeader;  //学生头像
    String cname;       //学生名称
    String mobile;      //手机号
    float receiveAmount ;       //老师实收费用
    float amount;       //家长支付费用
    String majorDemand;     //需求大类
    String minorDemand;     //需求小类
    String orderStatus;     //订单状态
    long classBeginTimePromise;     //约定上课时间
    long classBeginTimeActual;      //实际上课时间
    String lesson;      //课时
    String address;     //上课地址
    String description;     //备注
    long distance;      //距离

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getUserHeader() {
        return userHeader;
    }

    public void setUserHeader(String userHeader) {
        this.userHeader = userHeader;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public float getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(float receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getMajorDemand() {
        return majorDemand;
    }

    public void setMajorDemand(String majorDemand) {
        this.majorDemand = majorDemand;
    }

    public String getMinorDemand() {
        return minorDemand;
    }

    public void setMinorDemand(String minorDemand) {
        this.minorDemand = minorDemand;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getClassBeginTimePromise() {
        return classBeginTimePromise;
    }

    public void setClassBeginTimePromise(long classBeginTimePromise) {
        this.classBeginTimePromise = classBeginTimePromise;
    }

    public long getClassBeginTimeActual() {
        return classBeginTimeActual;
    }

    public void setClassBeginTimeActual(long classBeginTimeActual) {
        this.classBeginTimeActual = classBeginTimeActual;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }
}
