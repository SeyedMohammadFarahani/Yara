package com.ansarbank.room.Activity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ansarbank.room.MyApplication;
import com.ansarbank.room.R;
import com.ansarbank.room.Service.smsService;
import com.ansarbank.room.Util.Const;
import com.ansarbank.room.widget.EditText;
import com.ansarbank.room.widget.Text;
import com.tapadoo.alerter.Alerter;

public class ConfirmeActivity extends AppCompatActivity {
    smsService sms;
    EditText confirmeEditText;
    Text confirmetext;
    Text timer;
    Text again;
    Button ConfirmeButton;
    String phone;
    String getCodeString;
    String result;
    int getCode;
    int time = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirme);

        final Typeface typeface = Typeface.createFromAsset(getAssets(), "isans.ttf");

        phone = MyApplication.getMyApplication().getSharedPreferences(Const.AppName, Const.Phone);

        timer = findViewById(R.id.timer);
        again = findViewById(R.id.again);
        confirmetext = findViewById(R.id.confirmeText);
        confirmeEditText = findViewById(R.id.confirmeEditText);
        ConfirmeButton = findViewById(R.id.confirmeButton);

       /* SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void onMessageReceived(String messageText) {
                Log.e("Text", messageText);
                confirmeEditText.setText(messageText);
                result = messageText;
            }
        });
        sendFakeSms();*/

        final Intent intent3 = new Intent(ConfirmeActivity.this, smsService.class);
        startService(intent3);
        Intent intent2 = new Intent(this, smsService.class);
        bindService(intent2, serviceConnection, Context.BIND_AUTO_CREATE);

        confirmetext.setText(" " + getResources().getString(R.string.code1) + " " + phone + " " + getResources().getString(R.string.code2) + " ");

        ConfirmeButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                getCodeString = confirmeEditText.getText().toString();
                if (getCodeString.equals("")) {
                } else {
                    getCode = Integer.parseInt(confirmeEditText.getText().toString());
                }
                //TODO
                //getCode = confirme();
                if (getCodeString.equals("")) {
                    confirmeEditText.setError(getResources().getString(R.string.enterCode));
                } else if (getCode == confirme()/*result.equals(getCodeString)*/) {
                    MyApplication.getMyApplication().saveSharedPreferences(Const.AppName, Const.Tag, "1");
                    Intent intent1 = new Intent(ConfirmeActivity.this, MainActivity.class);
                    startActivity(intent1);
                    ConfirmeActivity.this.finish();
                } else {
                    Alerter.create(ConfirmeActivity.this)
                            .setText(R.string.error)
                            .setDismissable(true)
                            .setTextTypeface(typeface)
                            .setTextAppearance(14)
                            .setBackgroundColorRes(R.color.pink)
                            .setIcon(R.drawable.error)
                            .show();
                }
            }
        });

        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(" " + getResources().getString(R.string.reSend) + " " + time + getResources().getString(R.string.reSend2) + " ");
                time--;
                if ((millisUntilFinished / 1000) == 2) {
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.cancel(0);
                }
            }

            @Override
            public void onFinish() {
                timer.setText("");
                again.setVisibility(View.VISIBLE);
            }
        }.start();

        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                again.setVisibility(View.GONE);
                startService(intent3);
            }
        });
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            smsService.MyBinder b = (smsService.MyBinder) service;
            sms = b.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            sms = null;
        }
    };

    private int confirme() {
        if (sms != null)
            return sms.getCode();

        return 0;
    }

}