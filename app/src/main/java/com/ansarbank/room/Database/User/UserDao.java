package com.ansarbank.room.Database.User;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.graphics.Bitmap;

import com.ansarbank.room.Database.Card.Card;
import com.ansarbank.room.Database.Converter.BitmapConverter;
import com.ansarbank.room.Database.Converter.DataConverter;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT * FROM user where first_name LIKE  :firstName AND last_name LIKE :lastName")
    User findUserByName(String firstName, String lastName);

    @Query("SELECT * FROM user where phone LIKE :Phone")
    User findUserByPhone(String Phone);

    @Query("SELECT * FROM user where uid =:Id")
    User findUserById(int Id);

    @Query("DELETE FROM user ")
    void deleteAllUsers();


    @Query("UPDATE user SET first_name =:firstName WHERE uid =:Id ")
    void updateFirstName(int Id, String firstName);

    @Query("UPDATE user SET last_name =:lastName WHERE uid =:Id")
    void updateLastName(int Id, String lastName);

    @Query("UPDATE user SET phone =:Phone WHERE uid =:Id")
    void updatePhone(int Id, String Phone);

    @TypeConverters(DataConverter.class)
    @Query("UPDATE user SET list_card =:userCards WHERE uid =:Id")
    void updateCards(int Id, List<Card> userCards);

    @Query("UPDATE user SET image =:profile WHERE uid =:Id")
    void updateProfile(int Id, byte[] profile);

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);
}