package com.cunoraz.eksiseyler.app;

/**
 * Created by cuneytcarikci on 20/11/2016.
 */

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.cunoraz.eksiseyler.di.app.AppComponent;
import com.cunoraz.eksiseyler.di.app.AppModule;
import com.cunoraz.eksiseyler.di.app.DaggerAppComponent;
import com.cunoraz.eksiseyler.di.app.NetworkModule;

import io.fabric.sdk.android.Fabric;

public class EksiSeylerApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeAppComponent();
        Fabric.with(this, new Crashlytics());
    }

    private void initializeAppComponent() {

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule()).build();

        appComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }


}