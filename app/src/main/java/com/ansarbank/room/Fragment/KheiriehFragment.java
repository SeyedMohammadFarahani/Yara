package com.ansarbank.room.Fragment;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import com.isapanah.awesomespinner.AwesomeSpinner;

import java.util.ArrayList;
import java.util.List;

public class KheiriehFragment extends BaseFragment {

    private static final int REQUEST_CODE = 17;
    Btn transfer;
    EditText payValue;
    EditText serialNumber3;
    EditText calender;
    EditText ccv2;
    MyAutoComplete startCard;

    AwesomeSpinner box;
    Typeface typeface;

    String payValueString;
    String startCardString;
    String endCardString;
    String serialNumber3String;
    String calenderString;
    String ccv2String;
    String code;
    List<Card> cards;
    String[] allOfCards;
    int pay;
    boolean success;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.kheirieh_fragment, container, false);
        Const.number = 0;

        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.Title10));


        typeface = Typeface.createFromAsset(getActivity().getAssets(), "isans.ttf");

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

        ArrayList<String> boxes = new ArrayList<String>();
        boxes.add("محک");
        boxes.add("کمیته امداد");
        boxes.add("بهزیستی");
        boxes.add("عتبات عالیات");

        // expandableListView = view.findViewById(R.id.expandableListView);
        box = view.findViewById(R.id.operator);
        transfer = view.findViewById(R.id.internetConfirme);
        payValue = view.findViewById(R.id.kheiriehValue);
        serialNumber3 = view.findViewById(R.id.kheiriehSerialNumber);
        startCard = view.findViewById(R.id.kheiriehCardNumber);
        calender = view.findViewById(R.id.kheiriehCalender2);
        ccv2 = view.findViewById(R.id.kheiriehCCV2);

        startCard.addTextChangedListener(new TextWatcher() {
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

        CNTextWatcher tv = new CNTextWatcher(startCard);
        startCard.addTextChangedListener(tv);

        ValueTextWatcher valueTextWatcher = new ValueTextWatcher(payValue);
        payValue.addTextChangedListener(valueTextWatcher);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, allOfCards);
        startCard.setThreshold(4);
        startCard.setAdapter(adapter);
        startCard.setGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK);

        final ArrayAdapter<String> operatorAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item, boxes);
        operatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        box.setAdapter(operatorAdapter);
        box.setHintTextColor(getResources().getColor(R.color.red));
        box.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                //  Toast.makeText(getContext(), itemAtPosition, Toast.LENGTH_SHORT).show();
                box.setSelectedItemHintColor(getResources().getColor(R.color.buttonColor));
                if (position == 0)
                    code = "1";
                if (position == 1)
                    code = "2";
                if (position == 2)
                    code = "3";
                if (position == 3)
                    code = "4";
            }
        });

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // initListeners();
                startCardString = startCard.getText().toString().trim();
                endCardString = box.getSelectedItem();
                serialNumber3String = serialNumber3.getText().toString().trim();
                payValueString = payValue.getText().toString();
                calenderString = calender.getText().toString();
                ccv2String = ccv2.getText().toString();
                success = true;

                if (serialNumber3String.equals("")) {
                    serialNumber3.setError(getResources().getString(R.string.charge1));
                    success = false;
                } else if (serialNumber3String.length() < 5) {
                    serialNumber3.setError(getResources().getString(R.string.charge2));
                    success = false;
                }
                if (startCardString.equals("")) {
                    startCard.setError(getResources().getString(R.string.transfer3));
                    success = false;
                } else if (!(Utils.cardChecker(startCardString))) {
                    startCard.setError(getResources().getString(R.string.base3));
                    success = false;
                }
                if (payValueString.equals("")) {
                    payValue.setError(getResources().getString(R.string.tashilat5));
                    success = false;
                } else {
                    String temp = payValueString.replaceAll(",", "");
                    pay = Integer.parseInt(temp);
                    if (pay < 20000) {
                        payValue.setError(getResources().getString(R.string.transfer4));
                        success = false;
                    }
                    if (pay > 30000000) {
                        payValue.setError(getResources().getString(R.string.transfer5));
                        success = false;
                    }
                }
                if (!box.isSelected()) {
                    box.setSpinnerHint(getResources().getString(R.string.kheirieh_error));
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
                    showTransferDialog();
                   // String ussd = "*763*6*" + code + "#";
                   // Utils.callUssd(getContext(), getActivity(), ussd, REQUEST_CODE);

                }
            }
        });

        return view;
    }

    private void showTransferDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.transfer_dialog);
        //TODO

        final Text start = dialog.findViewById(R.id.startTransfer);
        CNTextWatcher tv2 = new CNTextWatcher(start);
        start.addTextChangedListener(tv2);
        start.setText(startCardString);

        final Text end = dialog.findViewById(R.id.endTransfer);
        end.setText(endCardString);

        final Text value = dialog.findViewById(R.id.transferValue);
        value.setText(payValueString);

        Btn cancle = dialog.findViewById(R.id.cancleTransfer);
        cancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Btn confirme = dialog.findViewById(R.id.confirmeTransfer);
        confirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                addTransAction(startCardString, endCardString, payValueString, Utils.randomCode() + "");

            }
        });
        dialog.show();
    }

    private void addTransAction(String s1, String s2, String s3, String s4) {
        TransAction transAction = new TransAction(getResources().getString(R.string.action5), s1, s2, null, s3, s4, setTime(), setDate(), null, null,Const.ID);
        AppDatabase.getAppDatabase(getActivity()).actionDao().insertTransAction(transAction);
        successTransactionDialog(Integer.parseInt(s4));
    }

}

