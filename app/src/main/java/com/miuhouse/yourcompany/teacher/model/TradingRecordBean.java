package com.miuhouse.yourcompany.teacher.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by kings on 7/20/2016.
 */
public class TradingRecordBean extends BaseBean implements Serializable {

//    字段名称	数据类型	说明
//    private String id	;
//    teacherId	String
//    orderId	String
//    changeType	String	变动类型 "1";// 订单完成账户变动
//    "2";// 提现到账变动
//    recordNo	String	流水号
//    account	String	提现账号
//    accountName	String 	提现人姓名
//    amount	BigDecimal	金额
//    Status	String	"1";// 提现申请
//    "2";// 审核提现申请
//    "3";// 正式发起提现
//    "4";// 提现成功
//    "5";// 提现失败
//    applyTime	Date	提现申请时间
//    checkStatus	String	审核是否通过
//    checkTime	Date	审核时间
//    bankTime	Date
//    feedbackTime	Date	账号变动时间
//    feedbackRemark	String	备注

    private String id;
    private String teacherId;
    private String orderId;
    private String changeType;
    private String recordNo;
    private String account;
    private String accountName;
    private BigDecimal amount;
    private String Status;
    private long applyTime;
    private String checkStatus;
    //    private long applyTime;
    private long feedbackTime; //帐号变动时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public long getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(long applyTime) {
        this.applyTime = applyTime;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public long getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(long feedbackTime) {
        this.feedbackTime = feedbackTime;
    }
}
