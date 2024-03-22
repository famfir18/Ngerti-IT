package com.app.ngertiit;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.onesignal.Continue;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

import timber.log.Timber;

public abstract class BaseApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    // NOTE: Replace the below with your own ONESIGNAL_APP_ID
    private static final String ONESIGNAL_APP_ID = "b595c9ff-db01-4633-8416-76b30da34f27";

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


    }
}
