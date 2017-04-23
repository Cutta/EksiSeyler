package com.cunoraz.eksiseyler.app;

/**
 * Created by cuneytcarikci on 20/11/2016.
 */

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.cunoraz.eksiseyler.utility.AppSettings;

import io.fabric.sdk.android.Fabric;

public class MyApplication extends Application {

    private static MyApplication instance;

    private AppSettings appSettings;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fabric.with(this, new Crashlytics());
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public synchronized AppSettings getSharedPrefManager() {
        if (appSettings == null)
            appSettings = AppSettings.getInstance();

        return appSettings;
    }
}