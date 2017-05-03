package com.cunoraz.eksiseyler.domain.main;

import com.cunoraz.eksiseyler.model.pref.PreferencesHelper;

/**
 * Created by cuneytcarikci on 03/05/2017.
 */

public class MainUseCaseImpl implements MainUsecase {

    private PreferencesHelper mPreferencesHelper;

    public MainUseCaseImpl(PreferencesHelper mPreferencesHelper) {
        this.mPreferencesHelper = mPreferencesHelper;
    }

    @Override
    public boolean isSavingModeActive() {
        return mPreferencesHelper.isSavingModeActive();
    }

    @Override
    public void storeSavingModeStatus(boolean willDisplayImages) {
        mPreferencesHelper.storeSavingModeStatus(willDisplayImages);
    }
}
