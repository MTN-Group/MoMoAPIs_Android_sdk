package com.momo.sdk;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class MomoApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    public static Context getAppContext() {
        return mContext;
    }

    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

    }

}