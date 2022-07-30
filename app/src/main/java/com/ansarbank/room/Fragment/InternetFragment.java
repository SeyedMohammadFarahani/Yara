package com.ansarbank.room.Fragment;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.ansarbank.room.Activity.MainActivity;
import com.ansarbank.room.Adapter.HamrahAvvalAdapter;
import com.ansarbank.room.Adapter.IrancellAdapter;
import com.ansarbank.room.Adapter.RightelAdapter;
import com.ansarbank.room.Database.AppDatabase;
import com.ansarbank.room.Database.Card.Card;
import com.ansarbank.room.Database.Transaction.TransAction;
import com.ansarbank.room.R;
import com.ansarbank.room.Util.CNTextWatcher;
import com.ansarbank.room.Util.Const;
import com.ansarbank.room.Util.Utils;
import com.ansarbank.room.widget.Btn;
import com.ansarbank.room.widget.EditText;
import com.ansarbank.room.widget.MyAutoComplete;
import com.ansarbank.room.widget.Text;
import com.isapanah.awesomespinner.AwesomeSpinner;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InternetFragment extends BaseFragment {

    //irancell
    ExpandableListView ListView;
    IrancellAdapter irancellAdapter;
    HamrahAvvalAdapter hamrahAvvalAdapter;
    RightelAdapter rightelAdapter;
    List<String> ListDataGroup;
    HashMap<String, List<String>> ListDataChild;
    Typeface typeface;

    Btn internetConfirme;
    EditText mobileNumber;
    EditText SerialNumber;
    MyAutoComplete CardNumber;
    AwesomeSpinner operator;
    AwesomeSpinner chargeTime;
    AwesomeSpinner chargeModel;
    List<Card> cards;
    String[] allOfCards;
    String phone;
    String cardNumberString;
    String serialNumberString;
    String operatorString;
    String group;
    String child;
    boolean success;
    boolean irancell;
    boolean hamrahAvval;
    boolean rightel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.internet_fragment, container, false);

        Const.number = 0;
        child = null;
        group = null;
        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.Title9));

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

        ArrayList<String> Operators = new ArrayList<String>();
        Operators.add("ایرانسل");
        Operators.add("همراه اول");
        Operators.add("رایتل");

        ListView = view.findViewById(R.id.irancell);
        CardNumber = view.findViewById(R.id.CardNumber);
        internetConfirme = view.findViewById(R.id.internetConfirme);
        mobileNumber = view.findViewById(R.id.Mobile);
        mobileNumber.setText(((MainActivity) getActivity()).getPhone());
        SerialNumber = view.findViewById(R.id.SerialNumber);
        operator = view.findViewById(R.id.operator);
        chargeModel = view.findViewById(R.id.chargeModel);
        chargeTime = view.findViewById(R.id.chargeTime);

        CNTextWatcher tv = new CNTextWatcher(CardNumber);
        CardNumber.addTextChangedListener(tv);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, allOfCards);
        CardNumber.setThreshold(4);
        CardNumber.setAdapter(adapter);
        CardNumber.setGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK);

        final ArrayAdapter<String> operatorAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item, Operators);
        operatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        operator.setAdapter(operatorAdapter);
        operator.setHintTextColor(getResources().getColor(R.color.red));
        operator.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                //  Toast.makeText(getContext(), itemAtPosition, Toast.LENGTH_SHORT).show();
                if (position == 0)  {
                    rightel = false;
                    hamrahAvval = false;
                    irancell = true;
                    initListeners();
                    initObjects();
                    initListData();
                    ListView.setVisibility(View.VISIBLE);
                    operator.setSelectedItemHintColor(getResources().getColor(R.color.yellow));
                }
                if (position == 1) {
                    rightel = false;
                    irancell = false;
                    hamrahAvval = true;
                    initObjects();
                    initListData();
                    initListeners();
                    ListView.setVisibility(View.VISIBLE);
                    operator.setSelectedItemHintColor(getResources().getColor(R.color.hamrah));
                }
                if (position == 2) {
                    irancell = false;
                    hamrahAvval = false;
                    rightel = true;
                    initObjects();
                    initListData();
                    initListeners();
                    ListView.setVisibility(View.VISIBLE);
                    operator.setSelectedItemHintColor(getResources().getColor(R.color.rightel));
                }
            }
        });

        internetConfirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = mobileNumber.getText().toString();
                cardNumberString = CardNumber.getText().toString().trim();
                serialNumberString = SerialNumber.getText().toString();
                operatorString = operator.getSelectedItem();
                int operatorCode = operator.getSelectedItemPosition();
                success = true;

                if (cardNumberString.equals("")) {
                    CardNumber.setError(getResources().getString(R.string.base2));
                    success = false;
                } else if (!(Utils.cardChecker(cardNumberString))) {
                    CardNumber.setError(getResources().getString(R.string.base3));
                    success = false;
                }
                if (serialNumberString.equals("")) {
                    SerialNumber.setError(getResources().getString(R.string.charge1));
                    success = false;
                } else if (serialNumberString.length() < 5) {
                    SerialNumber.setError(getResources().getString(R.string.charge2));
                    success = false;
                }
                if (!operator.isSelected()) {
                    operator.setSpinnerHint(getResources().getString(R.string.operatorError));
                    success = false;
                }

                if (phone.equals("")) {
                    mobileNumber.setError(getResources().getString(R.string.register1));
                    success = false;
                } else if (phone.length() < 13 || phone.charAt(0) != '+' || phone.charAt(1) != '9' || phone.charAt(2) != '8' || phone.charAt(3) != '9') {
                    mobileNumber.setError(getResources().getString(R.string.register3));
                    success = false;
                }
               /* if ((operatorCode == 0) && phone.charAt(4) != '3') {
                    mobileNumber.setError(getResources().getString(R.string.register5));
                    success = false;
                }
                if ((operatorCode == 0) && phone.charAt(4) != '0') {
                    mobileNumber.setError(getResources().getString(R.string.register5));
                    success = false;
                }
                if ((operatorCode == 1) && phone.charAt(4) != '1') {
                    mobileNumber.setError(getResources().getString(R.string.register5));
                    success = false;
                }
                if ((operatorCode == 1) && phone.charAt(4) != '9') {
                    mobileNumber.setError(getResources().getString(R.string.register5));
                    success = false;
                }
                if ((operatorCode == 2) && phone.charAt(4) != '2') {
                    mobileNumber.setError(getResources().getString(R.string.register5));
                    success = false;
                }*/

                if (child == null) {
                    Alerter.create(getActivity())
                            .setText(getResources().getString(R.string.internet_error))
                            .setTextTypeface(typeface)
                            .setTextAppearance(18)
                            .setBackgroundColorRes(R.color.pink)
                            .setIcon(R.drawable.error)
                            .show();
                    success = false;
                }

                if (success) {
                    showInternetDialog();
                }

            }
        });

        initListeners();
        initObjects();
        initListData();

        return view;
    }

    private void showInternetDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.internet_dialog);
        //TODO
        final Text number = dialog.findViewById(R.id.internetCard);
        CNTextWatcher tv2 = new CNTextWatcher(number);
        number.addTextChangedListener(tv2);
        number.setText(cardNumberString);

        final Text timeText = dialog.findViewById(R.id.internetTime);
        timeText.setText(operatorString + " - " + group);

        final Text valueText = dialog.findViewById(R.id.internetValue);
        valueText.setText(child);

        final Text phoneText = dialog.findViewById(R.id.internetPhone);
        phoneText.setText(phone);

        Btn cancle = dialog.findViewById(R.id.cancleInternet);
        cancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Btn confirme = dialog.findViewById(R.id.confirmeInternet);
        confirme.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                addTransAction(phone, child + "", Utils.randomCode() + "");
                // String ussd = "*763*8#";
                //Utils.callUssd(getContext(), getActivity(), ussd, REQUEST_CODE);

            }
        });
        dialog.show();
    }

    private void addTransAction(String s1, String s2, String s3) {

        TransAction transAction = new TransAction(getResources().getString(R.string.action6) + " (" + operatorString + ") ", null, null, s1, s2, s3, setTime(), setDate(), null, null, Const.ID);
        AppDatabase.getAppDatabase(getActivity()).actionDao().insertTransAction(transAction);
        successTransactionDialog(Integer.parseInt(s3));
    }

    private void initListeners() {

        ListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                group = ListDataGroup.get(groupPosition);
                child = ListDataChild.get(
                        ListDataGroup.get(groupPosition)).get(
                        childPosition);
                Toast.makeText(getContext(), group + " : " + child, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void initObjects() {

        // initializing the list of groups
        ListDataGroup = new ArrayList<>();

        // initializing the list of child
        ListDataChild = new HashMap<>();

        // initializing the adapter object
        irancellAdapter = new IrancellAdapter(getContext(), ListDataGroup, ListDataChild, typeface);
        hamrahAvvalAdapter = new HamrahAvvalAdapter(getContext(), ListDataGroup, ListDataChild, typeface);
        rightelAdapter = new RightelAdapter(getContext(), ListDataGroup, ListDataChild, typeface);

        // setting list adapter
        if (irancell) {
            ListView.setAdapter(irancellAdapter);
        }
        if (hamrahAvval) {
            ListView.setAdapter(hamrahAvvalAdapter);
        }
        if (rightel) {
            ListView.setAdapter(rightelAdapter);
        }

    }

    private void initListData() {

        if (irancell) {
            // Adding group data
            ListDataGroup.add(getString(R.string.hourlyIrancell));
            ListDataGroup.add(getString(R.string.oneDayIrancell));
            ListDataGroup.add(getString(R.string.threeDayIrancell));
            ListDataGroup.add(getString(R.string.oneWeekIrancell));
            ListDataGroup.add(getString(R.string.fifteenIrancell));
            ListDataGroup.add(getString(R.string.thirtyIrancell));
            ListDataGroup.add(getString(R.string.sixtyIrancell));
            ListDataGroup.add(getString(R.string.ninetyIrancell));
            ListDataGroup.add(getString(R.string.oneHundredAndTwentyIrancell));
            ListDataGroup.add(getString(R.string.oneHundredAndEightyIrancell));
            ListDataGroup.add(getString(R.string.oneYearIrancell));

            // array of strings
            String[] array;

            List<String> list1 = new ArrayList<>();
            array = getResources().getStringArray(R.array.HourlyIrancell);
            for (String item : array) {
                list1.add(item);
            }
            List<String> list2 = new ArrayList<>();
            array = getResources().getStringArray(R.array.OneDayIrancell);
            for (String item : array) {
                list2.add(item);
            }
            List<String> list3 = new ArrayList<>();
            array = getResources().getStringArray(R.array.ThreeDayIrancell);
            for (String item : array) {
                list3.add(item);
            }
            List<String> list4 = new ArrayList<>();
            array = getResources().getStringArray(R.array.OneWeekIrancell);
            for (String item : array) {
                list4.add(item);
            }
            List<String> list5 = new ArrayList<>();
            array = getResources().getStringArray(R.array.FifteenIrancell);
            for (String item : array) {
                list5.add(item);
            }
            List<String> list6 = new ArrayList<>();
            array = getResources().getStringArray(R.array.ThirtyIrancell);
            for (String item : array) {
                list6.add(item);
            }
            List<String> list7 = new ArrayList<>();
            array = getResources().getStringArray(R.array.SixtyIrancell);
            for (String item : array) {
                list7.add(item);
            }
            List<String> list8 = new ArrayList<>();
            array = getResources().getStringArray(R.array.NinetyIrancell);
            for (String item : array) {
                list8.add(item);
            }
            List<String> list9 = new ArrayList<>();
            array = getResources().getStringArray(R.array.OneHundredAndTwentyIrancell);
            for (String item : array) {
                list9.add(item);
            }
            List<String> list10 = new ArrayList<>();
            array = getResources().getStringArray(R.array.OneHundredAndEightyIrancell);
            for (String item : array) {
                list10.add(item);
            }
            List<String> list11 = new ArrayList<>();
            array = getResources().getStringArray(R.array.OneYearIrancell);
            for (String item : array) {
                list11.add(item);
            }
            // Adding child data
            ListDataChild.put(ListDataGroup.get(0), list1);
            ListDataChild.put(ListDataGroup.get(1), list2);
            ListDataChild.put(ListDataGroup.get(2), list3);
            ListDataChild.put(ListDataGroup.get(3), list4);
            ListDataChild.put(ListDataGroup.get(4), list5);
            ListDataChild.put(ListDataGroup.get(5), list6);
            ListDataChild.put(ListDataGroup.get(6), list7);
            ListDataChild.put(ListDataGroup.get(7), list8);
            ListDataChild.put(ListDataGroup.get(8), list9);
            ListDataChild.put(ListDataGroup.get(9), list10);
            ListDataChild.put(ListDataGroup.get(10), list11);

            // notify the adapter
            irancellAdapter.notifyDataSetChanged();
        }
        if (hamrahAvval) {
           /* ListDataChild = null;
            ListDataGroup = null;*/

            ListDataGroup.add(getString(R.string.oneDayIrancell));
            ListDataGroup.add(getString(R.string.oneWeekIrancell));
            ListDataGroup.add(getString(R.string.thirtyIrancell));
            ListDataGroup.add(getString(R.string.sixtyIrancell));
            ListDataGroup.add(getString(R.string.ninetyIrancell));
            ListDataGroup.add(getString(R.string.oneHundredAndTwentyIrancell));
            ListDataGroup.add(getString(R.string.oneHundredAndEightyIrancell));
            ListDataGroup.add(getString(R.string.oneYearIrancell));

            String[] array;

            List<String> list1 = new ArrayList<>();
            array = getResources().getStringArray(R.array.OneDayHamrah);
            for (String item : array) {
                list1.add(item);
            }
            List<String> list2 = new ArrayList<>();
            array = getResources().getStringArray(R.array.OneWeekHamrah);
            for (String item : array) {
                list2.add(item);
            }
            List<String> list3 = new ArrayList<>();
            array = getResources().getStringArray(R.array.ThirtyHamrah);
            for (String item : array) {
                list3.add(item);
            }
            List<String> list4 = new ArrayList<>();
            array = getResources().getStringArray(R.array.SixtyHamrah);
            for (String item : array) {
                list4.add(item);
            }
            List<String> list5 = new ArrayList<>();
            array = getResources().getStringArray(R.array.NinetyHamrah);
            for (String item : array) {
                list5.add(item);
            }
            List<String> list6 = new ArrayList<>();
            array = getResources().getStringArray(R.array.OneHundredAndTwentyHamrah);
            for (String item : array) {
                list6.add(item);
            }

            List<String> list7 = new ArrayList<>();
            array = getResources().getStringArray(R.array.OneHundredAndEightyHamrah);
            for (String item : array) {
                list7.add(item);
            }
            List<String> list8 = new ArrayList<>();
            array = getResources().getStringArray(R.array.OneYearHamrah);
            for (String item : array) {
                list8.add(item);
            }
            // Adding child data
            ListDataChild.put(ListDataGroup.get(0), list1);
            ListDataChild.put(ListDataGroup.get(1), list2);
            ListDataChild.put(ListDataGroup.get(2), list3);
            ListDataChild.put(ListDataGroup.get(3), list4);
            ListDataChild.put(ListDataGroup.get(4), list5);
            ListDataChild.put(ListDataGroup.get(5), list6);
            ListDataChild.put(ListDataGroup.get(6), list7);
            ListDataChild.put(ListDataGroup.get(7), list8);

            // notify the adapter
            hamrahAvvalAdapter.notifyDataSetChanged();
        }

        if (rightel) {

            ListDataGroup.add(getString(R.string.hourlyIrancell));
            ListDataGroup.add(getString(R.string.oneDayIrancell));
            ListDataGroup.add(getString(R.string.threeDayIrancell));
            ListDataGroup.add(getString(R.string.oneWeekIrancell));
            ListDataGroup.add(getString(R.string.fifteenIrancell));
            ListDataGroup.add(getString(R.string.thirtyIrancell));
            ListDataGroup.add(getString(R.string.ninetyIrancell));
            ListDataGroup.add(getString(R.string.oneHundredAndEightyIrancell));
            ListDataGroup.add(getString(R.string.oneYearIrancell));

            // array of strings
            String[] array;

            List<String> list1 = new ArrayList<>();
            array = getResources().getStringArray(R.array.HourlyRightel);
            for (String item : array) {
                list1.add(item);
            }
            List<String> list2 = new ArrayList<>();
            array = getResources().getStringArray(R.array.OneDayRightel);
            for (String item : array) {
                list2.add(item);
            }
            List<String> list3 = new ArrayList<>();
            array = getResources().getStringArray(R.array.ThreeDayRightel);
            for (String item : array) {
                list3.add(item);
            }
            List<String> list4 = new ArrayList<>();
            array = getResources().getStringArray(R.array.OneWeekRightel);
            for (String item : array) {
                list4.add(item);
            }
            List<String> list5 = new ArrayList<>();
            array = getResources().getStringArray(R.array.FifteenRightel);
            for (String item : array) {
                list5.add(item);
            }
            List<String> list6 = new ArrayList<>();
            array = getResources().getStringArray(R.array.ThirtyRightel);
            for (String item : array) {
                list6.add(item);
            }

            List<String> list8 = new ArrayList<>();
            array = getResources().getStringArray(R.array.NinetyRightel);
            for (String item : array) {
                list8.add(item);
            }

            List<String> list10 = new ArrayList<>();
            array = getResources().getStringArray(R.array.OneHundredAndEightyRightel);
            for (String item : array) {
                list10.add(item);
            }
            List<String> list11 = new ArrayList<>();
            array = getResources().getStringArray(R.array.OneYearRightel);
            for (String item : array) {
                list11.add(item);
            }
            // Adding child data
            ListDataChild.put(ListDataGroup.get(0), list1);
            ListDataChild.put(ListDataGroup.get(1), list2);
            ListDataChild.put(ListDataGroup.get(2), list3);
            ListDataChild.put(ListDataGroup.get(3), list4);
            ListDataChild.put(ListDataGroup.get(4), list5);
            ListDataChild.put(ListDataGroup.get(5), list6);
            ListDataChild.put(ListDataGroup.get(6), list8);
            ListDataChild.put(ListDataGroup.get(7), list10);
            ListDataChild.put(ListDataGroup.get(8), list11);

            // notify the adapter
            rightelAdapter.notifyDataSetChanged();

        }
    }

}

