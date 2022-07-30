package com.ansarbank.room.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ansarbank.room.Activity.MainActivity;
import com.ansarbank.room.Activity.ZBarScannerActivity;
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

import java.util.List;


public class GhabzFragment extends BaseFragment {
    Btn confirme;
    EditText ghabzSerial;
    EditText paySerial;
    EditText serialNumber2;
    EditText calender;
    EditText ccv2;
    Text barCode;
    String paySerialString;
    String ghabzSerialString;
    String cardNumber2String;
    String serialNumber2String;
    String calenderString;
    String ccv2String;
    String ghabzModel;
    String code;
    String temp;
    MyAutoComplete cardNumber2;
    List<Card> cards;
    String[] allOfCards;
    int result;
    final static int REQUEST_CODE = 3;
    boolean success;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Const.number = 0;
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.ghabz_fragment, container, false);
        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.Title5));


        //permission
        int req = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (req != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 11);
        } else {
            Const.correct = true;
        }

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

        ghabzSerial = view.findViewById(R.id.ghabzSerial);
        paySerial = view.findViewById(R.id.paySerial);
        cardNumber2 = view.findViewById(R.id.Cardnumber2);
        serialNumber2 = view.findViewById(R.id.serialNumber2);
        barCode = view.findViewById(R.id.barCode);
        confirme = view.findViewById(R.id.confirmeButton2);
        calender = view.findViewById(R.id.ghabzCalender);
        ccv2 = view.findViewById(R.id.ghabzCCV2);

        cardNumber2.addTextChangedListener(new TextWatcher() {
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

        CNTextWatcher tv = new CNTextWatcher(cardNumber2);
        cardNumber2.addTextChangedListener(tv);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, allOfCards);
        cardNumber2.setThreshold(4);
        cardNumber2.setAdapter(adapter);
        cardNumber2.setGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK);

        barCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Const.correct) {
                    Intent intent = new Intent(getActivity(), ZBarScannerActivity.class);
                    startActivityForResult(intent, Const.REQUEST);
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.cameraError), Toast.LENGTH_SHORT).show();
                }
            }
        });

        confirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paySerialString = paySerial.getText().toString();
                ghabzSerialString = ghabzSerial.getText().toString();
                cardNumber2String = cardNumber2.getText().toString().trim();
                serialNumber2String = serialNumber2.getText().toString();
                calenderString = calender.getText().toString();
                ccv2String = ccv2.getText().toString();
                success = true;

                if (cardNumber2String.equals("")) {
                    cardNumber2.setError(getResources().getString(R.string.base2));
                    success = false;
                } else if (!(Utils.cardChecker(cardNumber2String))) {
                    cardNumber2.setError(getResources().getString(R.string.base3));
                    success = false;
                }
                if (serialNumber2String.equals("")) {
                    serialNumber2.setError(getResources().getString(R.string.charge1));
                    success = false;
                } else if (serialNumber2String.length() < 5) {
                    serialNumber2.setError(getResources().getString(R.string.charge2));
                    success = false;
                }
                if (ghabzSerialString.equals("")) {
                    ghabzSerial.setError(getResources().getString(R.string.driving3));
                    success = false;
                } else if (ghabzSerialString.length() < 6) {
                    ghabzSerial.setError(getResources().getString(R.string.driving4));
                    success = false;
                }
                if (paySerialString.equals("")) {
                    paySerial.setError(getResources().getString(R.string.driving5));
                    success = false;
                } else if (paySerialString.length() < 6) {
                    paySerial.setError(getResources().getString(R.string.driving6));
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
                    int number = paySerialString.length() - 5;
                    temp = paySerialString.substring(0, number);
                    result = Integer.parseInt(temp) * 1000;

                    switch (ghabzSerialString.charAt(11)) {

                        case '1':
                            ghabzModel = getResources().getString(R.string.action7);
                            break;
                        case '2':
                            ghabzModel = getResources().getString(R.string.action8);
                            break;
                        case '3':
                            ghabzModel = getResources().getString(R.string.action9);
                            break;
                        case '4':
                            ghabzModel = getResources().getString(R.string.action11);
                            break;
                        case '5':
                            ghabzModel = getResources().getString(R.string.action12);
                            break;
                        case '6':
                            ghabzModel = getResources().getString(R.string.action13);
                            break;
                        case '8':
                            ghabzModel = getResources().getString(R.string.action14);
                            break;
                        case '9':
                            ghabzModel = getResources().getString(R.string.action2);
                            break;
                    }
                    showGhabzDialog();
                }
            }
        });

        return view;
    }

    private void showGhabzDialog() {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.ghabz_dialog);
        //TODO
        final Text number = dialog.findViewById(R.id.ghabzCard);
        CNTextWatcher tv2 = new CNTextWatcher(number);
        number.addTextChangedListener(tv2);
        number.setText(cardNumber2String);

        final Text value1 = dialog.findViewById(R.id.ghabzValue1);
        value1.setText(ghabzSerialString);

        final Text value2 = dialog.findViewById(R.id.ghabzValue2);
        value2.setText(paySerialString);

        final Text value3 = dialog.findViewById(R.id.ghabzModel);
        value3.setText(ghabzModel);

        final Text value4 = dialog.findViewById(R.id.ghabzPayment);
        ValueTextWatcher valueTextWatcher = new ValueTextWatcher(value4);
        value4.addTextChangedListener(valueTextWatcher);
        value4.setText(result + "");

        Btn cancle = dialog.findViewById(R.id.cancleGhabz);
        cancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Btn confirme = dialog.findViewById(R.id.confirmeGhabz);
        confirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                addTransAction(Utils.randomCode() + "", paySerialString, ghabzSerialString, ghabzModel, result + "");
                String ussd = " *763*4" + "*" + ghabzSerialString + "*" + paySerialString
                        + "*" + cardNumber2String + "*" + serialNumber2String + "*" + ccv2String + "*" + calenderString.replaceAll("/", "") + "#";
               // Utils.callUssd(getContext(), getActivity(), ussd, REQUEST_CODE);
            }
        });
        dialog.show();
    }

    private void addTransAction(String s1, String s2, String s3, String s4, String s5) {
        TransAction transAction = new TransAction(s4, null, null, null, s5, s1, setTime(), setDate(), s2, s3, Const.ID);
        AppDatabase.getAppDatabase(getActivity()).actionDao().insertTransAction(transAction);
        successTransactionDialog(Integer.parseInt(s1));
        // successTransactionDialog(Integer.parseInt(s1));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 11: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Const.correct = true;

                } else {
                    Const.correct = false;
                }
                break;
            }
            case 12: {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (isAdded()) {
            if (resultCode == Activity.RESULT_OK) {
                String scanContent = intent.getStringExtra(Const.SCAN_RESULT);
                if (scanContent.length() >= 26) {
                    String ret_bill = scanContent.substring(0, 13);
                    String ret_pay = scanContent.substring(13);

                    ghabzSerial.setText(ret_bill);
                    paySerial.setText(ret_pay);
                }
            } else if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            }
        }
    }

}
