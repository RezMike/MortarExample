package com.rezmike.mortarexample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import mortar.MortarScope;

public class OneActivity extends AppCompatActivity {

    public static final String ONE_STRING = "ONE_STRING";

    MortarScope mActivityScope;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MortarScope viewScope = mActivityScope.findChild(getViewScopeName());
        if (viewScope == null) {
            viewScope = mActivityScope.buildChild()
                    .withService(OneTextView.TV_STRING, "oneViewScope")
                    .build(getViewScopeName());
        }
        Context viewContext = viewScope.createContext(this);

        //noinspection ResourceType
        String string = (String) this.getSystemService(ONE_STRING);

        setContentView(R.layout.activity_one);

        FrameLayout rootFrame = (FrameLayout) findViewById(R.id.root_frame);

        Button button = (Button) findViewById(R.id.one_button);
        button.setText("Go to TwoActivity");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OneActivity.this, TwoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText(string);

        View tvView = LayoutInflater.from(viewContext).inflate(R.layout.one_text_view, rootFrame, false);
        rootFrame.addView(tvView);
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
                    .withService(ONE_STRING, "OneActivityScope")
                    .build(getScopeName());
        }
        return mActivityScope.hasService(name) ? mActivityScope.getService(name) : super.getSystemService(name);
    }

    private String getScopeName() {
        return OneActivity.class.getName();
    }

    private String getViewScopeName() {
        return "ONE_VIEW_SCOPE";
    }
}
