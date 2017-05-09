package com.cunoraz.eksiseyler.ui.main;

import com.cunoraz.eksiseyler.domain.main.MainUsecase;
import com.cunoraz.eksiseyler.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by cuneytcarikci on 03/05/2017.
 */

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter{

    private MainUsecase mMainUsecase;

    @Inject
    public MainPresenter(MainUsecase mainUsecase){
        this.mMainUsecase = mainUsecase;
    }
    @Override
    public void onViewReady() {

    }

    @Override
    public boolean isSavingModeActive() {
        return mMainUsecase.isSavingModeActive();
    }

    @Override
    public void onClickSavingModeMenuItem() {
        boolean isSavingModeActive = mMainUsecase.isSavingModeActive();
        boolean newSavingModeStatus = !isSavingModeActive;
        mMainUsecase.storeSavingModeStatus(newSavingModeStatus);
        getView().updateSavingModeMenuItem(newSavingModeStatus);

        if (newSavingModeStatus)
            getView().showSavingModeActiveDialog();
        else
            getView().showSavingModeNotActiveDialog();
    }

    @Override
    public void handleDeepLink() {
        getView().openDetailFromDeepLink();
    }

    @Override
    public void onClickFavouritesActivity() {
        getView().openFavouritesActivity();
    }

    @Override
    public void onClickFab() {
        getView().openSearchActivity();
    }

}
