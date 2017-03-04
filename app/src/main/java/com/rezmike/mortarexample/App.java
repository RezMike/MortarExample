package com.rezmike.mortarexample;

import android.app.Application;

import mortar.MortarScope;

public class App extends Application {

    public static final String APP_STRING = "APP_STRING";

    private MortarScope mRootScope;
    private MortarScope mRootActivityScope;

    @Override
    public void onCreate() {
        super.onCreate();

        mRootScope = MortarScope.buildRootScope()
                .withService(APP_STRING, "AppScope")
                .build("Root");
        mRootActivityScope = mRootScope.buildChild()
                .withService(RootActivity.ROOT_STRING, "RootScope")
                .build(RootActivity.getRootScopeName());
    }

    @Override
    public Object getSystemService(String name) {
        return mRootScope.hasService(name) ? mRootScope.getService(name) : super.getSystemService(name);
    }
}
