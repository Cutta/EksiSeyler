package com.cunoraz.eksiseyler.di.favourites;

import com.cunoraz.eksiseyler.di.PerActivity;
import com.cunoraz.eksiseyler.domain.favourites.FavouritesUsecase;
import com.cunoraz.eksiseyler.domain.favourites.FavouritesUsecaseImpl;
import com.cunoraz.eksiseyler.model.pref.PreferencesHelper;
import com.cunoraz.eksiseyler.ui.favourites.FavouritiesContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cuneytcarikci on 04/05/2017.
 */
@Module
public class FavouritesModule {

    private FavouritiesContract.View mView;

    public FavouritesModule(FavouritiesContract.View mView) {
        this.mView = mView;
    }

    @PerActivity
    @Provides
    FavouritesUsecase provideMainUsecase(PreferencesHelper preferencesHelper){
        return new FavouritesUsecaseImpl(preferencesHelper);
    }

    @PerActivity
    @Provides
    FavouritiesContract.View provideView(){return mView;}

}
