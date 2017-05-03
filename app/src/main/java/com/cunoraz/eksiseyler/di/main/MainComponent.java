package com.cunoraz.eksiseyler.di.main;

import com.cunoraz.eksiseyler.di.PerActivity;
import com.cunoraz.eksiseyler.di.app.AppComponent;
import com.cunoraz.eksiseyler.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by cuneytcarikci on 03/05/2017.
 */
@PerActivity
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);

}
