package com.shi.bravin.app;

import android.app.Application;

import com.bravin.btoast.BToast;

public class BToastApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BToast.Config.getInstance()
//                .setAnimate()
//                .setAnimationDuration()
//                .setAnimationGravity()
//                .setDuration()
//                .setTextColor()
//                .setErrorColor()
//                .setInfoColor()
//                .setSuccessColor()
//                .setWarningColor()
//                .setLayoutGravity()
//                .setLongDurationMillis()
//                .setRadius()
//                .setRelativeGravity()
//                .setSameLength()
//                .setShortDurationMillis()
//                .setShowIcon()
//                .setTextSize()
                .apply(this);
    }
}
