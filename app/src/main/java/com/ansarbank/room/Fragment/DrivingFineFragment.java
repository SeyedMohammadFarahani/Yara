package com.ansarbank.room.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ansarbank.room.Activity.MainActivity;
import com.ansarbank.room.Database.AppDatabase;
import com.ansarbank.room.Database.Card.Card;
import com.ansarbank.room.R;
import com.ansarbank.room.Util.CNTextWatcher;
import com.ansarbank.room.Util.Const;
import com.ansarbank.room.Util.Utils;
import com.ansarbank.room.widget.Btn;
import com.ansarbank.room.widget.EditText;
import com.ansarbank.room.widget.MyAutoComplete;

import java.util.List;

public class DrivingFineFragment extends BaseFragment {

    Btn drivingConfirme;
    EditText serialNumber3;
    EditText calender;
    EditText ccv2;
    String cardNumber3String;
    String serialNumber3String;
    String calenderString;
    String ccv2String;
    MyAutoComplete cardNumber3;
    List<Card> cards;
    String[] allOfCards;
    boolean success;
    final static int REQUEST_CODE = 18;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.driving_fine_fragment, container, false);
        Const.number = 0;
        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.Title4));

        cards = AppDatabase.getAppDatabase(getActivity()).cardDao().getAllCards();
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getUser() != Const.ID) {
                cards.remove(cards.get(i));
            }
        }
        allOfCards = new String[cards.size()];

        if (cards != null) {
            for (int i = 0; i < cards.size(); i++) {
                allOfCards[i] = cards.get(i).getCardNumber();
            }
        }

        cardNumber3 = view.findViewById(R.id.Cardnumber3);
        serialNumber3 = view.findViewById(R.id.serialNumber3);
        drivingConfirme = view.findViewById(R.id.drivingConfirme);
        calender = view.findViewById(R.id.drivingCalender);
        ccv2 = view.findViewById(R.id.drivingCCV2);

        CNTextWatcher tv = new CNTextWatcher(cardNumber3);
        cardNumber3.addTextChangedListener(tv);

        cardNumber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Utils.cardChecker(s.toString().trim())) {
                    Card card = AppDatabase.getAppDatabase(getActivity()).cardDao().findByCardNumber(s.toString());
                    if (card != null) {
                        ccv2.setText(card.getCcv2());
                        calender.setText(card.getDate());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, allOfCards);
        cardNumber3.setThreshold(4);
        cardNumber3.setAdapter(adapter);
        cardNumber3.setGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK);

        drivingConfirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardNumber3String = cardNumber3.getText().toString().trim();
                serialNumber3String = serialNumber3.getText().toString();
                calenderString = calender.getText().toString();
                ccv2String = ccv2.getText().toString();
                success = true;

                if (cardNumber3String.equals("")) {
                    cardNumber3.setError(getResources().getString(R.string.base2));
                    success = false;
                } else if (!(Utils.cardChecker(cardNumber3String))) {
                    cardNumber3.setError(getResources().getString(R.string.base3));
                    success = false;
                }
                if (serialNumber3String.equals("")) {
                    serialNumber3.setError(getResources().getString(R.string.charge1));
                    success = false;
                } else if (serialNumber3String.length() < 5) {
                    serialNumber3.setError(getResources().getString(R.string.charge2));
                    success = false;
                }
                if (calenderString.equals("")) {
                    calender.setError(getResources().getString(R.string.base5));
                    success = false;
                } else {
                    if (calenderString.charAt(2) != '/') {
                        calender.setError(getResources().getString(R.string.base6));
                        success = false;
                    } else {
                        parts = calenderString.split("/");
                        year = Integer.parseInt(parts[0]);
                        month = Integer.parseInt(parts[1]);
                        if ((year < 98 && year > 10) || (year == 98 && month <= 6)) {
                            calender.setError(getResources().getString(R.string.base7));
                            success = false;
                        } else if (calenderString.length() < 5 || month < 0 || month > 12) {
                            calender.setError(getResources().getString(R.string.base6));
                            success = false;
                        }
                    }
                }
                if (ccv2String.equals("")) {
                    ccv2.setError(getResources().getString(R.string.base8));
                    success = false;
                } else if (ccv2String.length() < 3) {
                    ccv2.setError(getResources().getString(R.string.base9));
                    success = false;
                }
                if (success) {
                    String ussd = " *763*1*1*1" + "*" + cardNumber3String + "*" + serialNumber3String
                            + "*" + ccv2String + "*" + calenderString.replaceAll("/", "") + "#";
                    //Utils.callUssd(getContext(), getActivity(), ussd, REQUEST_CODE);
                }
            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 14: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Const.correct2 = true;
                } else {
                    Const.correct2 = false;
                }
                break;
            }
        }
    }

}
