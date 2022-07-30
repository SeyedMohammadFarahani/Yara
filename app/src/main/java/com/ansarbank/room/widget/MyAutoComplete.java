package com.ansarbank.room.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class MyAutoComplete extends AppCompatAutoCompleteTextView {
    public MyAutoComplete(Context context) {
        super(context);
        init(context);
    }

    public MyAutoComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyAutoComplete(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "isans.ttf");
        setTypeface(typeface);

    }

}

