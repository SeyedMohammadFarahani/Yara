package com.ansarbank.room;

import android.app.Application;
import android.content.SharedPreferences;

public class MyApplication extends Application {

    private static MyApplication myApplication;
    public static MyApplication getMyApplication() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;

    }

    public void saveSharedPreferences(String MY_PREFS_NAME, String MY_PREFS_TAG, String data) {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(MY_PREFS_TAG, data);
        editor.apply();
    }

    public String getSharedPreferences(String MY_PREFS_NAME, String MY_PREFS_TAG) {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(MY_PREFS_TAG, "");
    }

}
