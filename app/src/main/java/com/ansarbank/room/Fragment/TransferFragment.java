package com.ansarbank.room.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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

public class TransferFragment extends BaseFragment {

    //placeholder
    Text goTo;
    CardManagerFragment cardManagerFragment;
    ConstraintLayout transferPlaceholder;

    //main
    Btn transfer;
    EditText payValue;
    EditText serialNumber3;
    EditText calender;
    EditText ccv2;
    MyAutoComplete endCard;
    MyAutoComplete startCard;
    String payValueString;
    String calenderString;
    String ccv2String;
    String startCardString;
    String endCardString;
    String serialNumber3String;
    List<Card> cards;
    String[] allOfCards;
    final static int REQUEST_CODE = 32;
    int pay;
    boolean success;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.transfer_fragment, container, false);
        Const.number = 0;
        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.Title8));

        cardManagerFragment = new CardManagerFragment();

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
        transfer = view.findViewById(R.id.transferButton);
        payValue = view.findViewById(R.id.payValue);
        startCard = view.findViewById(R.id.startCard);
        endCard = view.findViewById(R.id.endCard);
        serialNumber3 = view.findViewById(R.id.serialNumber3);
        goTo = view.findViewById(R.id.goTo);
        transferPlaceholder = view.findViewById(R.id.transferPlaceholder);
        calender = view.findViewById(R.id.transferCalender);
        ccv2 = view.findViewById(R.id.transferCCV2);

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

        CNTextWatcher tv2 = new CNTextWatcher(endCard);
        endCard.addTextChangedListener(tv2);

        ValueTextWatcher valueTextWatcher = new ValueTextWatcher(payValue);
        payValue.addTextChangedListener(valueTextWatcher);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, allOfCards);
        startCard.setThreshold(4);
        startCard.setAdapter(adapter);
        startCard.setGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK);
        endCard.setAdapter(adapter);
        endCard.setGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK);


        if (AppDatabase.getAppDatabase(getActivity().getApplicationContext()).cardDao().getAllCards().size() == 0) {
            transferPlaceholder.setVisibility(View.VISIBLE);
        }

        goTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag, cardManagerFragment).commit();
            }
        });
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCardString = startCard.getText().toString().trim();
                endCardString = endCard.getText().toString();
                serialNumber3String = serialNumber3.getText().toString().trim();
                payValueString = payValue.getText().toString();
                calenderString = calender.getText().toString();
                ccv2String = ccv2.getText().toString();
                success = true;

                if (endCardString.equals("")) {
                    endCard.setError(getResources().getString(R.string.transfer1));
                    success = false;
                } else if (!(Utils.cardChecker(endCardString))) {
                    endCard.setError(getResources().getString(R.string.base3));
                    success = false;
                } else if (endCardString.equals(startCardString)) {
                    endCard.setError(getResources().getString(R.string.transfer2));
                    success = false;
                }
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
        CNTextWatcher tv3 = new CNTextWatcher(end);
        end.addTextChangedListener(tv3);
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
                String ussd = " *763*2*1" + "*" + startCardString + "*" + serialNumber3String
                        + "*" + ccv2String + "*" + calenderString.replaceAll("/", "") + "*" + endCardString + "*" + payValueString.replaceAll(",", "") + "#";
                //Utils.callUssd(getContext(), getActivity(), ussd, REQUEST_CODE);
            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (isAdded()) {
            if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                Log.w("abcdef", String.valueOf(intent.getData()));
            }
        }
    }

    private void addTransAction(String s1, String s2, String s3, String s4) {
        TransAction transAction = new TransAction(getResources().getString(R.string.action5), s1, s2, null, s3, s4, setTime(), setDate(), null, null, Const.ID);
        AppDatabase.getAppDatabase(getActivity()).actionDao().insertTransAction(transAction);
        successTransactionDialog(Integer.parseInt(s4));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 15: {
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
