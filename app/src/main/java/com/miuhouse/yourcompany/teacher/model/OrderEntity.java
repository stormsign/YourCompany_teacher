package com.miuhouse.yourcompany.teacher.model;

import java.io.Serializable;

/**
 * Created by khb on 2016/7/7.
 */
public class OrderEntity implements Serializable {
    String id;
    String parentId;
    String teacherId;
    String tname;
    String userHeader;
    String cname;
    String mobile;
    float receiveAmount ;
    float amount;
    String majorDemand;
    String minorDemand;
    String orderStatus;
    long classBeginTimePromise;
    long classBeginTimeActual;
    String lesson;
    String address;
    String description;
    long distance;

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
