package com.miuhouse.yourcompany.teacher.model;

import java.io.Serializable;

/**
 * Created by kings on 7/22/2016.
 */
public class PayAccountBean implements Serializable {

//    字段名称	数据类型	说明
//    id	String
//    userId	String	老师Id或家长Id
//    accountType	String	账号类型
//    accountNo	String	账号
//    accountName	String	真实姓名
//    isDefault	String	是否默认
//    status	String	状态（目前没用）
//    createTime	Date
//    modifyTime	Date

    private String id;
    private String userId;
    private String accountType;
    private String accountNo;
    private String accountName;
    private String isDefault;
    private String status;
    private long createTime;
    private long modifyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }
}
