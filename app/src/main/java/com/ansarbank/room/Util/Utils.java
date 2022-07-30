package com.ansarbank.room.Util;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.ansarbank.room.Database.AppDatabase;
import com.ansarbank.room.R;
import com.ansarbank.room.widget.Btn;

import java.util.Random;

public class Utils {

    public static void deleteActionDialog(final View view, final String temp, final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.delete_action_dialog);
        //TODO
        final Btn cancel = dialog.findViewById(R.id.cancelActionDialog);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Btn delete = dialog.findViewById(R.id.deleteActionDialog);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AppDatabase.getAppDatabase(context).actionDao().deleteTransAction(AppDatabase.getAppDatabase(context).actionDao().findTransActionByCode(temp));
                view.setVisibility(View.GONE);
                AppDatabase.getAppDatabase(context).actionDao().deleteTransAction(AppDatabase.getAppDatabase(context).actionDao().findTransActionByCode(temp));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static boolean cardChecker(String cardNumber) {
        int total = 0;
        for (int i = 0; i < cardNumber.length(); i++) {
            char ichar = cardNumber.charAt(i);
            int iichar = 0;
            try {
                iichar = Integer.parseInt(ichar + "");
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            int toAdd = iichar * (2 - (i % 2));
            total += (toAdd <= 9) ? toAdd : (toAdd - 9);
        }
        return (total % 10 == 0);
    }

    public static int randomCode() {
        int number = 0;

        while (number < 1000000) {
            Random random = new Random();
            number = (number * 10) + random.nextInt(10);
        }
        return number;
    }

    public static String amountFormater(String amount) {
        if (amount.length() > 0) {

            boolean isminus = amount.startsWith("-");
            amount = amount.replaceAll("[^\\d]", "");
            for (int i = amount.length() - 3; i > 0; i -= 3) {
                amount = amount.substring(0, i) + "," + amount.substring(i);
            }
            if (isminus) {
                amount = amount + "-";
            }
            return amount;
        } else {
            return "";
        }
    }

    public static void callUssd(Context context, Activity activity, String ussd, int REQUEST_CODE) {

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Handler handler = new Handler();
        TelephonyManager.UssdResponseCallback ussdResponseCallback = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ussdResponseCallback = new TelephonyManager.UssdResponseCallback() {
                @Override
                public void onReceiveUssdResponse(TelephonyManager telephonyManager, String request, CharSequence response) {
                    super.onReceiveUssdResponse(telephonyManager, request, response);
                    Log.w("response", String.valueOf(response));
                    // Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onReceiveUssdResponseFailed(TelephonyManager telephonyManager, String request, int failureCode) {
                    super.onReceiveUssdResponseFailed(telephonyManager, request, failureCode);
                    // Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                }
            };
        }
        int req = ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);
        if (req != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 12);
            }
        } else {
            Const.correct2 = true;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tm.sendUssdRequest(ussd, ussdResponseCallback, handler);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(ussd));
            activity.startActivityForResult(intent, REQUEST_CODE);
        }
    }


}
