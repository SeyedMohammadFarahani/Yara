package com.ansarbank.room.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ansarbank.room.Database.Card.Card;
import com.ansarbank.room.Database.Card.CardDao;
import com.ansarbank.room.Database.Transaction.ActionDao;
import com.ansarbank.room.Database.Transaction.TransAction;
import com.ansarbank.room.Database.User.User;
import com.ansarbank.room.Database.User.UserDao;

@Database(entities = {User.class, Card.class, TransAction.class}, version = 13)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract UserDao userDao();

    public abstract ActionDao actionDao();

    public abstract CardDao cardDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
