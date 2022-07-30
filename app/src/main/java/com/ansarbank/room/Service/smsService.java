package com.ansarbank.room.Service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.ansarbank.room.Activity.ConfirmeActivity;
import com.ansarbank.room.R;

import java.util.Random;

public class smsService extends Service {
    public smsService() {
    }

    IBinder iBinder = new MyBinder();
    int code = randomCode();

    public int getCode() {
        return code;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notfic;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("1", "test1", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
            notfic = new Notification.Builder(getApplicationContext(), "1")
                    .setContentTitle(getResources().getString(R.string.verification))
                    .setContentText(code + "")
                    .setSmallIcon(R.drawable.message)
                    .build();
        } else {
            notfic = new Notification.Builder(getApplicationContext())
                    .setContentTitle(getResources().getString(R.string.verification))
                    .setContentText(code + "")
                    .setSmallIcon(R.drawable.message)
                    .build();
        }

        notfic.flags |= Notification.FLAG_AUTO_CANCEL;
        notfic.defaults |= Notification.DEFAULT_SOUND;
        notfic.defaults |= Notification.DEFAULT_LIGHTS;
        notfic.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notfic);


        stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");

        return iBinder;
    }

    public class MyBinder extends Binder {

        public smsService getService() {
            return smsService.this;
        }
    }

    private int randomCode() {
        int number = 0;

        while (number < 1000) {
            Random random = new Random();
            number = (number * 10) + random.nextInt(10);
        }
        return number;
    }


}
