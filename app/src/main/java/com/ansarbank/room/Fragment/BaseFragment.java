package com.ansarbank.room.Fragment;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ansarbank.room.Activity.MainActivity;
import com.ansarbank.room.Database.AppDatabase;
import com.ansarbank.room.Database.Card.Card;
import com.ansarbank.room.R;
import com.ansarbank.room.Util.CNTextWatcher;
import com.ansarbank.room.Util.Const;
import com.ansarbank.room.Util.DateConverter;
import com.ansarbank.room.Util.Utils;
import com.ansarbank.room.callback.ViewUpdate;
import com.ansarbank.room.widget.Btn;
import com.ansarbank.room.widget.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BaseFragment extends Fragment {

    String cardNameString;
    String cardNumberString;
    String cardDateString;
    String cardCCV2String;
    String[] parts;
    int month;
    int year;
    int id;
    boolean success;

    public void successTransactionDialog(int number) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.success_transaction_dialog);
        dialog.setCancelable(false);

        final Text code = dialog.findViewById(R.id.transactionCode);
        code.setText(number + "");

        Btn understand = dialog.findViewById(R.id.understand);
        understand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag, ((MainActivity) getActivity()).getMainFragment()).commit();
            }
        });

        dialog.show();
    }


    public void createCardDialog(final View view1) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.cardmanager_dialog);

        final EditText cardName = dialog.findViewById(R.id.cardName);
        //TODO
        //cardName.setText("محمد");

        final EditText cardNumber = dialog.findViewById(R.id.cardNumber);

        CNTextWatcher tv = new CNTextWatcher(cardNumber);
        cardNumber.addTextChangedListener(tv);
        //TODO
        //cardNumber.setText("6273811128533145");

        final EditText cardDate = dialog.findViewById(R.id.cardDate);
        //TODO
        //cardDate.setText("98/11");

        final EditText cardCCV2 = dialog.findViewById(R.id.cardCCV2);
        //TODO
        //cardCCV2.setText("123");

        Btn cancle = dialog.findViewById(R.id.cancleButton);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Btn create = dialog.findViewById(R.id.createbutton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = Const.ID;
                cardNameString = cardName.getText().toString();
                cardNumberString = cardNumber.getText().toString().trim();
                cardDateString = cardDate.getText().toString();
                cardCCV2String = cardCCV2.getText().toString();

                success = true;

                if (cardNameString.equals("")) {
                    cardName.setError(getResources().getString(R.string.base1));
                    success = false;
                }
                if (cardNumberString.equals("")) {
                    cardNumber.setError(getResources().getString(R.string.base2));
                    success = false;
                } else if (!(Utils.cardChecker(cardNumberString))/*cardNumberString.length() < 16*/) {
                    cardNumber.setError(getResources().getString(R.string.base3));
                    success = false;
                } else if (AppDatabase.getAppDatabase(getActivity()).cardDao().findByCardNumber(cardNumberString) != null) {
                    cardNumber.setError(getResources().getString(R.string.base4));
                    success = false;
                }
                if (cardDateString.equals("")) {
                    cardDate.setError(getResources().getString(R.string.base5));
                    success = false;
                } else {

                    if (cardDateString.charAt(2) != '/') {
                        cardDate.setError(getResources().getString(R.string.base6));
                        success = false;
                    } else {
                        parts = cardDateString.split("/");
                        year = Integer.parseInt(parts[0]);
                        month = Integer.parseInt(parts[1]);
                        if ((year < 98 && year > 10) || (year == 98 && month <= 6)) {
                            cardDate.setError(getResources().getString(R.string.base7));
                            success = false;
                        } else if (cardDateString.length() < 5 || month < 0 || month > 12) {
                            cardDate.setError(getResources().getString(R.string.base6));
                            success = false;
                        }
                    }
                }
                if (cardCCV2String.equals("")) {
                    cardCCV2.setError(getResources().getString(R.string.base8));
                    success = false;
                } else if (cardCCV2String.length() < 3) {
                    cardCCV2.setError(getResources().getString(R.string.base9));
                    success = false;
                }
                if (success) {
                    addCard(cardNameString, cardNumberString, cardCCV2String, cardDateString);
                    Toast.makeText(getContext(), getResources().getString(R.string.base10), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    ViewUpdate viewUpdate = new CardManagerFragment();
                    viewUpdate.update(view1);

                }
            }
        });
        dialog.show();
    }

    private void addCard(String s1, String s2, String s3, String s4) {
        Card card = new Card(s1, s2, s3, s4, Const.ID);
        AppDatabase.getAppDatabase(getActivity()).cardDao().insertCard(card);

    }

    public String setDate() {

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = formatter.format(todayDate);
        String[] date = todayString.split("-");
        int Year = Integer.parseInt(date[0]);
        int Month = Integer.parseInt(date[1]);
        int Day = Integer.parseInt(date[2]);

        DateConverter converter = new DateConverter();
        converter.gregorianToPersian(Year, Month, Day);
        String MyDate = converter.getYear() + "/" + converter.getMonth() + "/" + +converter.getDay();
        return MyDate;
    }

    public String setTime() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return hour + ":" + minute;
    }

}

