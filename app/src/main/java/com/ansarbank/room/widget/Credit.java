package com.ansarbank.room.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class Credit extends AppCompatTextView {
    public Credit(Context context) {
        super(context);
        init(context);
    }

    public Credit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Credit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "credit.ttf");
        setTypeface(typeface);

    }

    public void setSelection(int length) {
    }
}

