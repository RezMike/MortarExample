package com.rezmike.mortarexample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import mortar.MortarScope;

public class RootActivity extends AppCompatActivity {

    public static final String ROOT_STRING = "ROOT_STRING";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MortarScope activityScope = MortarScope.findChild(getApplicationContext(), getRootScopeName());
        MortarScope viewScope = activityScope.findChild(getViewScopeName());
        if (viewScope == null) {
            viewScope = activityScope.buildChild()
                    .withService(OneTextView.TV_STRING, "oneViewScope")
                    .build(getViewScopeName());
        }
        Context viewContext = viewScope.createContext(this);

        //noinspection ResourceType
        String string = (String) this.getSystemService(ROOT_STRING);

        setContentView(R.layout.activity_root);

        FrameLayout rootFrame = (FrameLayout) findViewById(R.id.root_frame);
        View tvView = LayoutInflater.from(viewContext).inflate(R.layout.one_text_view, rootFrame, false);
        rootFrame.addView(tvView);
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        MortarScope activityScope = MortarScope.findChild(getApplicationContext(), getRootScopeName());
        return activityScope.hasService(name) ? activityScope.getService(name) : super.getSystemService(name);
    }

    public static String getRootScopeName() {
        return RootActivity.class.getName();
    }

    private String getViewScopeName() {
        return "ONE_VIEW_SCOPE";
    }
}
