package com.ansarbank.room.Util;

import android.text.Editable;
import android.text.TextWatcher;
import com.ansarbank.room.widget.EditText;
import com.ansarbank.room.widget.Text;

public class ValueTextWatcher implements TextWatcher {

    Text text;
    EditText editText;
    boolean flag;

    public ValueTextWatcher(EditText editText) {
        this.editText = editText;
        flag = true;
    }

    public ValueTextWatcher(Text text) {
        this.text = text;
        flag = false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (flag) {
            editText.removeTextChangedListener(this);
            editText.setText(Utils.amountFormater(editText.getText().toString()));
            editText.addTextChangedListener(this);
            editText.setSelection(editText.getText().length());
        } else {
            text.removeTextChangedListener(this);
            text.setText(Utils.amountFormater(text.getText().toString()));
            text.addTextChangedListener(this);
            text.setSelection(text.getText().length());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
