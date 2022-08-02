package com.ansarbank.room.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.ansarbank.room.MyApplication;
import com.ansarbank.room.R;
import com.ansarbank.room.Util.Const;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

     /*   AppDatabase.getAppDatabase(getApplicationContext()).userDao().deleteAllUsers();
        AppDatabase.getAppDatabase(getApplicationContext()).actionDao().deleteAllTransAction();
        AppDatabase.getAppDatabase(getApplicationContext()).cardDao().deleteAllCards();*/

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {

                if (MyApplication.getMyApplication().getSharedPreferences(Const.AppName, Const.Tag).equals("1")) {

                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);

                    SplashActivity.this.finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
            }
        }.start();

    }
}
