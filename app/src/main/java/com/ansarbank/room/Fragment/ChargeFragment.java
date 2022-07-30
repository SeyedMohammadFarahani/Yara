package com.ansarbank.room.Fragment;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.List;

public class ChargeFragment extends BaseFragment implements View.OnTouchListener {
    int value;
    Btn button1;
    Btn button2;
    Btn button4;
    Btn button5;
    Btn button6;
    Btn confirmeButton;
    MyAutoComplete cardNumber;
    List<Card> cards;
    String[] allOfCards;
    EditText mobileNumber;
    EditText serialNumber;
    String phone;
    String ussd;
    String code;
    String cardNumberString;
    String serialNumberString;
    ArrayList<Btn> buttons;
    final static int REQUEST_CODE = 7;
    boolean success;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.charge_fragment, container, false);
        Const.number = 0;
        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.Title2));

        final Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "isans.ttf");

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

        button1 = view.findViewById(R.id.chargeButton1);
        button1.setOnTouchListener(this);
        button2 = view.findViewById(R.id.chargeButton2);
        button2.setOnTouchListener(this);
        button4 = view.findViewById(R.id.chargeButton4);
        button4.setOnTouchListener(this);
        button5 = view.findViewById(R.id.chargeButton5);
        button5.setOnTouchListener(this);
        button6 = view.findViewById(R.id.chargeButton6);
        button6.setOnTouchListener(this);

        mobileNumber = view.findViewById(R.id.mobileNumber);
        mobileNumber.setText(((MainActivity) getActivity()).getPhone());
        cardNumber = view.findViewById(R.id.cardNember);
        serialNumber = view.findViewById(R.id.serialNumber);

        CNTextWatcher tv = new CNTextWatcher(cardNumber);
        cardNumber.addTextChangedListener(tv);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, allOfCards);
        cardNumber.setThreshold(4);
        cardNumber.setAdapter(adapter);
        cardNumber.setGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK);

        buttons = new ArrayList<>();
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);

        confirmeButton = view.findViewById(R.id.confirmeButton);
        confirmeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = mobileNumber.getText().toString();
                cardNumberString = cardNumber.getText().toString().trim();
                serialNumberString = serialNumber.getText().toString();
                success = true;

                if (phone.equals("")) {
                    mobileNumber.setError(getResources().getString(R.string.register1));
                    success = false;
                } else if (phone.length() < 13 || phone.charAt(0) != '+' || phone.charAt(1) != '9' || phone.charAt(2) != '8' || phone.charAt(3) != '9') {
                    mobileNumber.setError(getResources().getString(R.string.register3));
                    success = false;
                }
                if (cardNumberString.equals("")) {
                    cardNumber.setError(getResources().getString(R.string.base2));
                    success = false;
                } else if (!(Utils.cardChecker(cardNumberString))) {
                    cardNumber.setError(getResources().getString(R.string.base3));
                    success = false;
                }
                if (serialNumberString.equals("")) {
                    serialNumber.setError(getResources().getString(R.string.charge1));
                    success = false;
                } else if (serialNumberString.length() < 5) {
                    serialNumber.setError(getResources().getString(R.string.charge2));
                    success = false;
                }
                if (value == 0) {
                    Alerter.create(getActivity())
                            .setText(getResources().getString(R.string.charg_error))
                            .setTextTypeface(typeface)
                            .setTextAppearance(14)
                            .setBackgroundColorRes(R.color.pink)
                            .setIcon(R.drawable.error)
                            .show();
                    success = false;
                }
                if (success) {
                    showChargeDialog();
                }
            }
        });

        return view;
    }

    private void showChargeDialog() {
        phone = phone.substring(1, 13);
        ussd = "*780*2*1*" + phone.replaceAll("98", "0") + "*" + code + "*" + cardNumberString + "*" + serialNumberString + "#";
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.charge_dialog);
        //TODO
        final Text number = dialog.findViewById(R.id.chargeCard);
        CNTextWatcher tv2 = new CNTextWatcher(number);
        number.addTextChangedListener(tv2);
        number.setText(cardNumberString);

        final Text valueText = dialog.findViewById(R.id.chargeValue);
        ValueTextWatcher valueTextWatcher = new ValueTextWatcher(valueText);
        valueText.addTextChangedListener(valueTextWatcher);
        valueText.setText(value + "");

        final Text phoneText = dialog.findViewById(R.id.chargePhone);
        phoneText.setText(phone);

        Btn cancle = dialog.findViewById(R.id.cancleCharge);
        cancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Btn confirme = dialog.findViewById(R.id.confirmeCharge);
        confirme.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                addTransAction(phone, value + "", Utils.randomCode() + "");
               // Utils.callUssd(getContext(), getActivity(), ussd, REQUEST_CODE);

            }
        });
        dialog.show();
    }

    private void addTransAction(String s1, String s2, String s3) {

        TransAction transAction = new TransAction(getResources().getString(R.string.action1), null, null, s1, s2, s3, setTime(), setDate(), null, null, Const.ID);
        AppDatabase.getAppDatabase(getActivity()).actionDao().insertTransAction(transAction);
        successTransactionDialog(Integer.parseInt(s3));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.chargeButton1:
                setBackgroundButton(button1, buttons);
                value = 10000;
                code = "1";
                break;
            case R.id.chargeButton2:
                setBackgroundButton(button2, buttons);
                value = 20000;
                code = "2";
                break;
            case R.id.chargeButton4:
                setBackgroundButton(button4, buttons);
                value = 50000;
                code = "3";
                break;
            case R.id.chargeButton5:
                setBackgroundButton(button5, buttons);
                value = 100000;
                code = "4";
                break;
            case R.id.chargeButton6:
                setBackgroundButton(button6, buttons);
                value = 200000;
                code = "5";
                break;
        }
        return true;
    }

    public void setBackgroundButton(Btn btn, ArrayList<Btn> btns) {
        btn.setPressed(true);
        btn.setTextColor(getResources().getColor(R.color.white));
        btns.remove(btn);
        for (Btn button : btns) {
            button.setPressed(false);
            button.setTextColor(getResources().getColor(R.color.buttonTextColor));
        }
        btns.add(btn);
    }


}
