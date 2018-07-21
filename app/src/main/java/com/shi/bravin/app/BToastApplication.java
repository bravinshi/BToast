package com.shi.bravin.app;

import android.app.Application;

import com.bravin.btoast.BToast;

public class BToastApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BToast.Config.getInstance()
                .apply(this);
    }
}
