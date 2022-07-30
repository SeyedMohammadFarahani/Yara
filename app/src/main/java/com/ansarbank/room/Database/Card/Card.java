package com.ansarbank.room.Database.Card;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "card")
public class Card {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int cid;

    @ColumnInfo(name = "cardName")
    private String cardName;

    @ColumnInfo(name = "cardNumber")
    private String cardNumber;

    @ColumnInfo(name = "ccv2")
    private String ccv2;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "user")
    private int user;


    public Card(String cardName, String cardNumber, String ccv2, String date, int user) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.ccv2 = ccv2;
        this.date = date;
        this.user = user;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public void setCid(@NonNull int cid) {
        this.cid = cid;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCcv2(String ccv2) {
        this.ccv2 = ccv2;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @NonNull
    public int getCid() {
        return cid;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCcv2() {
        return ccv2;
    }

    public String getDate() {
        return date;
    }
}
