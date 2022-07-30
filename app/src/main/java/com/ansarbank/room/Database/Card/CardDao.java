package com.ansarbank.room.Database.Card;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ansarbank.room.Database.Card.Card;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CardDao {

    @Query("SELECT * FROM card")
    List<Card> getAllCards();

    @Query("SELECT * FROM card where cardNumber LIKE :number")
    Card findByCardNumber(String number);

    @Query("SELECT * FROM card where cid =:Id")
    Card findCardById(int Id);

    @Query("DELETE FROM card ")
    void deleteAllCards();

    @Query("SELECT * from card")
    List<Card> countCards();

    @Query("UPDATE card SET cardNumber =:cardNumber WHERE cid =:Id")
    void updateCardNumber(int Id, String cardNumber);

    @Query("UPDATE card SET cardName =:cardName WHERE cid =:Id")
    void updateCardName(int Id, String cardName);

    @Query("UPDATE card SET ccv2 =:ccv2 WHERE cid =:Id")
    void updateCardCCV2(int Id, String ccv2);

    @Query("UPDATE card SET date =:date WHERE cid =:Id")
    void updateCardDate(int Id, String date);

    @Insert
    void insertCard(Card card);

    @Delete
    void deleteCard(Card card);
}