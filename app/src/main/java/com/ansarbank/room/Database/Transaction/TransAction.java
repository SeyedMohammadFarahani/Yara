package com.ansarbank.room.Database.Transaction;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "transaction")
public class TransAction {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int tid;

    @ColumnInfo(name = "TransactionName")
    private String TransactionName;

    @ColumnInfo(name = "StartNumber")
    private String StartNumber;

    @ColumnInfo(name = "EndNumber")
    private String EndNumber;

    @ColumnInfo(name = "TransActionPhone")
    private String TransActionPhone;

    @ColumnInfo(name = "TransActionValue")
    private String TransActionValue;

    @ColumnInfo(name = "TransActionCode")
    private String TransActionCode;

    @ColumnInfo(name = "TransActionTime")
    private String TransActionTime;

    @ColumnInfo(name = "TransActionDate")
    private String TransActionDate;

    @ColumnInfo(name = "PaySerialTransAction")
    private String PaySerialTransAction;

    @ColumnInfo(name = "GhabzSerialTransAction")
    private String GhabzSerialTransAction;

    @ColumnInfo(name = "userId")
    private int userId;

    public TransAction(String TransactionName, String StartNumber, String EndNumber, String TransActionPhone, String TransActionValue, String TransActionCode, String TransActionTime, String TransActionDate, String PaySerialTransAction, String GhabzSerialTransAction, int userId) {
        this.TransactionName = TransactionName;
        this.StartNumber = StartNumber;
        this.EndNumber = EndNumber;
        this.TransActionPhone = TransActionPhone;
        this.TransActionValue = TransActionValue;
        this.TransActionCode = TransActionCode;
        this.TransActionTime = TransActionTime;
        this.TransActionDate = TransActionDate;
        this.PaySerialTransAction = PaySerialTransAction;
        this.GhabzSerialTransAction = GhabzSerialTransAction;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setPaySerialTransAction(String paySerialTransAction) {
        PaySerialTransAction = paySerialTransAction;
    }

    public void setGhabzSerialTransAction(String ghabzSerialTransAction) {
        GhabzSerialTransAction = ghabzSerialTransAction;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public void setTransactionName(String transactionName) {
        TransactionName = transactionName;
    }

    public void setStartNumber(String startNumber) {
        StartNumber = startNumber;
    }

    public void setTransActionDate(String transActionDate) {
        TransActionDate = transActionDate;
    }

    public void setEndNumber(String endNumber) {
        EndNumber = endNumber;
    }

    public void setTransActionPhone(String transActionPhone) {
        TransActionPhone = transActionPhone;
    }

    public void setTransActionValue(String transActionValue) {
        TransActionValue = transActionValue;
    }

    public void setTransActionCode(String transActionCode) {
        TransActionCode = transActionCode;
    }

    public void setTransActionTime(String transActionTime) {
        TransActionTime = transActionTime;
    }

    public void setPaySerialTransActionTime(String paySerialTransActionTime) {
        PaySerialTransAction = paySerialTransActionTime;
    }

    public void setGhabzSerialTransActionTime(String ghabzSerialTransActionTime) {
        GhabzSerialTransAction = ghabzSerialTransActionTime;
    }

    public int getTid() {
        return tid;
    }

    public String getTransactionName() {
        return TransactionName;
    }

    public String getStartNumber() {
        return StartNumber;
    }

    public String getEndNumber() {
        return EndNumber;
    }

    public String getTransActionPhone() {
        return TransActionPhone;
    }

    public String getTransActionValue() {
        return TransActionValue;
    }

    public String getTransActionCode() {
        return TransActionCode;
    }

    public String getTransActionTime() {
        return TransActionTime;
    }

    public String getPaySerialTransAction() {
        return PaySerialTransAction;
    }

    public String getGhabzSerialTransAction() {
        return GhabzSerialTransAction;
    }

    public String getTransActionDate() {
        return TransActionDate;
    }

}
