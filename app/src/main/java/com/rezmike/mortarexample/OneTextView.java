package com.rezmike.mortarexample;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class OneTextView extends TextView {

    public static final String TV_STRING = "TV_STRING";

    private String mString;

    public OneTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //noinspection ResourceType
        mString = (String) context.getSystemService(TV_STRING);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setText(mString);
    }
}
