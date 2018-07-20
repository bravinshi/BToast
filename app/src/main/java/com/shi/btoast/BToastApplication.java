package com.shi.btoast;

import android.app.Application;

public class BToastApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BToast.Config.getInstance()
                .apply(this);
    }
}
