package com.ansarbank.room.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ansarbank.room.Activity.MainActivity;
import com.ansarbank.room.Database.AppDatabase;
import com.ansarbank.room.Database.Card.Card;
import com.ansarbank.room.Database.Transaction.TransAction;
import com.ansarbank.room.R;
import com.ansarbank.room.Util.CNTextWatcher;
import com.ansarbank.room.Util.Const;
import com.ansarbank.room.Util.Utils;
import com.ansarbank.room.Util.ValueTextWatcher;
import com.ansarbank.room.widget.Btn;
import com.ansarbank.room.widget.EditText;
import com.ansarbank.room.widget.MyAutoComplete;
import com.ansarbank.room.widget.Text;
import com.bumptech.glide.util.Util;

import java.util.List;

public class TashilatFragment extends BaseFragment {
    int pay;
    int month;
    int year;
    Btn tashilatConfirme;
    EditText payValue2;
    EditText serialNumber4;
    EditText tashilatNumber;
    EditText calender;
    EditText ccv2;
    MyAutoComplete startCard2;
    String payValueString;
    String startCardString;
    String tashilatNumberString;
    String serialNumberString;
    String calenderString;
    String ccv2String;
    String[] parts;
    List<Card> cards;
    String[] allOfCards;
    boolean success;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.tashilat_fragment, container, false);
        Const.number = 0;
        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.Title7));
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

        tashilatConfirme = view.findViewById(R.id.tashilatConfirme);
        payValue2 = view.findViewById(R.id.payValue2);
        startCard2 = view.findViewById(R.id.startCard2);
        tashilatNumber = view.findViewById(R.id.tashilatNumber);
        serialNumber4 = view.findViewById(R.id.serialNumber4);
        calender = view.findViewById(R.id.calender);
        ccv2 = view.findViewById(R.id.CCV2);

        startCard2.addTextChangedListener(new TextWatcher() {
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

        //todo
        CNTextWatcher tv = new CNTextWatcher(startCard2);
        startCard2.addTextChangedListener(tv);

        CNTextWatcher tv2 = new CNTextWatcher(tashilatNumber);
        tashilatNumber.addTextChangedListener(tv2);

        ValueTextWatcher valueTextWatcher = new ValueTextWatcher(payValue2);
        payValue2.addTextChangedListener(valueTextWatcher);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, allOfCards);
        startCard2.setThreshold(4);
        startCard2.setAdapter(adapter);
        startCard2.setGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK);

        tashilatConfirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCardString = startCard2.getText().toString().trim();
                tashilatNumberString = tashilatNumber.getText().toString().trim();
                serialNumberString = serialNumber4.getText().toString();
                payValueString = payValue2.getText().toString();
                calenderString = calender.getText().toString();
                ccv2String = ccv2.getText().toString();
                success = true;


                if (tashilatNumberString.equals("")) {
                    tashilatNumber.setError(getResources().getString(R.string.tashilat1));
                    success = false;
                } else if (tashilatNumberString.length() < 16) {
                    tashilatNumber.setError(getResources().getString(R.string.tashilat2));
                    success = false;
                } else if (tashilatNumberString.equals(startCardString)) {
                    tashilatNumber.setError(getResources().getString(R.string.tashilat3));
                    success = false;
                }
                if (serialNumberString.equals("")) {
                    serialNumber4.setError(getResources().getString(R.string.charge1));
                    success = false;
                } else if (serialNumberString.length() < 5) {
                    serialNumber4.setError(getResources().getString(R.string.charge2));
                    success = false;
                }
                if (startCardString.equals("")) {
                    startCard2.setError(getResources().getString(R.string.tashilat4));
                    success = false;
                } else if (!(Utils.cardChecker(startCardString))) {
                    startCard2.setError(getResources().getString(R.string.base3));
                    success = false;
                }
                if (payValueString.equals("")) {
                    payValue2.setError(getResources().getString(R.string.tashilat5));
                    success = false;
                } else {
                    String temp = payValueString.replaceAll(",", "");
                    pay = Integer.parseInt(temp);
                    if (pay < 20000) {
                        payValue2.setError(getResources().getString(R.string.tashilat6));
                        success = false;
                    }
                    if (pay > 30000000) {
                        payValue2.setError(getResources().getString(R.string.transfer5));
                        success = false;
                    }
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
                    showTashilatDialog();
                }
            }
        });
        return view;
    }

    private void showTashilatDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.tashilat_dialog);
        //TODO
        final Text number = dialog.findViewById(R.id.tashilatCard);
        CNTextWatcher tv2 = new CNTextWatcher(number);
        number.addTextChangedListener(tv2);
        number.setText(tashilatNumberString);

        final Text value = dialog.findViewById(R.id.tashilatValue);
        value.setText(payValueString);

        Btn cancle = dialog.findViewById(R.id.cancleTashilate);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Btn confirme = dialog.findViewById(R.id.confirmeTashilat);
        confirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                addTransAction(tashilatNumberString, payValueString, Utils.randomCode() + "");

            }
        });
        dialog.show();
    }

    private void addTransAction(String s1, String s2, String s3) {
        TransAction transAction = new TransAction(getResources().getString(R.string.action4), null, s1, null, s2, s3, setTime(), setDate(), null, null,Const.ID);
        AppDatabase.getAppDatabase(getActivity()).actionDao().insertTransAction(transAction);
        successTransactionDialog(Integer.parseInt(s3));
    }

}