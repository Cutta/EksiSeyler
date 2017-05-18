package com.cunoraz.eksiseyler.di.app;

import android.content.Context;

import com.cunoraz.eksiseyler.app.EksiSeylerApp;
import com.cunoraz.eksiseyler.model.pref.PreferencesHelper;
import com.cunoraz.eksiseyler.model.rest.ApiSource;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by cuneytcarikci on 02/05/2017.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    Context context();

    ApiSource apiSource();

    PreferencesHelper preferencesHelper();

    void inject(EksiSeylerApp app);

}