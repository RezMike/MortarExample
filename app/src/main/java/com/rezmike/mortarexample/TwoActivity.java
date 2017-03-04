package com.rezmike.mortarexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mortar.MortarScope;

public class TwoActivity extends AppCompatActivity {

    public static final String TWO_STRING = "TWO_STRING";

    MortarScope mActivityScope;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //noinspection ResourceType
        String string = (String) this.getSystemService(TWO_STRING);

        setContentView(R.layout.activity_two);

        Button button = (Button) findViewById(R.id.two_button);
        button.setText("Go to OneActivity");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TwoActivity.this, OneActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText(string);
    }

    @Override
    protected void onDestroy() {
        if (isFinishing()) {
            MortarScope activityScope = MortarScope.findChild(getApplicationContext(), getScopeName());
            if (activityScope != null) activityScope.destroy();
        }
        super.onDestroy();
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        mActivityScope = MortarScope.findChild(getApplicationContext(), getScopeName());
        if (mActivityScope == null) {
            mActivityScope = MortarScope.buildChild(getApplicationContext())
                    .withService(TWO_STRING, "TwoActivityScope")
                    .build(getScopeName());
        }
        return mActivityScope.hasService(name) ? mActivityScope.getService(name) : super.getSystemService(name);
    }

    private String getScopeName() {
        return TwoActivity.class.getName();
    }
}
