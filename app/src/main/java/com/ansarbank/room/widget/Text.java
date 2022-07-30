package com.ansarbank.room.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class Text extends AppCompatTextView {
    public Text(Context context) {
        super(context);
        init(context);
    }

    public Text(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Text(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "isans.ttf");
        setTypeface(typeface);

    }

    public void setSelection(int length) {
    }
}

