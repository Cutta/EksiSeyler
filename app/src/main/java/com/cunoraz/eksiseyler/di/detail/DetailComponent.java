package com.cunoraz.eksiseyler.di.detail;

import com.cunoraz.eksiseyler.di.PerActivity;
import com.cunoraz.eksiseyler.di.app.AppComponent;
import com.cunoraz.eksiseyler.ui.detail.DetailActivity;

import dagger.Component;

/**
 * Created by andanicalik on 03/05/17.
 */
@PerActivity
@Component(modules = DetailModule.class, dependencies = AppComponent.class)
public interface DetailComponent {

    void inject(DetailActivity detailActivity);

}