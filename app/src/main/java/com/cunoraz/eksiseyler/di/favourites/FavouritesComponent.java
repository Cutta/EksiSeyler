package com.cunoraz.eksiseyler.di.favourites;

import com.cunoraz.eksiseyler.di.PerActivity;
import com.cunoraz.eksiseyler.di.app.AppComponent;
import com.cunoraz.eksiseyler.ui.favourites.FavouritesListActivity;

import dagger.Component;

/**
 * Created by cuneytcarikci on 04/05/2017.
 */
@PerActivity
@Component(modules = FavouritesModule.class, dependencies = AppComponent.class)
public interface FavouritesComponent {

    void inject(FavouritesListActivity activity);
}
