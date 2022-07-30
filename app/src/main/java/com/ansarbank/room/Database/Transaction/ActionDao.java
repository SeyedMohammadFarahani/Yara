package com.ansarbank.room.Database.Transaction;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ActionDao {

    @Query("SELECT * FROM `TransAction`")
    List<TransAction> getAllTransAction();

    @Query("SELECT * FROM `TransAction` where TransActionCode =:code")
    TransAction findTransActionByCode(String code);

    @Query("DELETE FROM `TransAction` ")
    void deleteAllTransAction();

    @Insert
    void insertTransAction(TransAction transaction);

    @Delete
    void deleteTransAction(TransAction transaction);


}