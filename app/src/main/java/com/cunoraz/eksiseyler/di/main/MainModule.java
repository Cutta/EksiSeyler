package com.cunoraz.eksiseyler.di.main;

import com.cunoraz.eksiseyler.di.PerActivity;
import com.cunoraz.eksiseyler.domain.main.MainUseCaseImpl;
import com.cunoraz.eksiseyler.domain.main.MainUsecase;
import com.cunoraz.eksiseyler.model.pref.PreferencesHelper;
import com.cunoraz.eksiseyler.ui.main.MainContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cuneytcarikci on 03/05/2017.
 */

@Module
public class MainModule {

    private MainContract.View mView;

    public MainModule(MainContract.View mView) {
        this.mView = mView;
    }

    @PerActivity
    @Provides
    MainUsecase provideMainUsecase(PreferencesHelper preferencesHelper){
        return new MainUseCaseImpl(preferencesHelper);
    }

    @PerActivity
    @Provides
    MainContract.View provideView(){return mView;}
}
