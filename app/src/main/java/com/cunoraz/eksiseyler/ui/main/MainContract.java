package com.cunoraz.eksiseyler.ui.main;

import com.cunoraz.eksiseyler.ui.base.BaseView;

/**
 * Created by cuneytcarikci on 03/05/2017.
 */

public class MainContract {

    public interface View extends BaseView {

        void showSavingModeActiveDialog();

        void showSavingModeNotActiveDialog();

        void updateSavingModeMenuItem(boolean isActive);

        void openDetailFromDeepLink();

        void openFavouritesActivity();

    }

    interface Presenter {

        void onViewReady();

        boolean isSavingModeActive();

        void onClickSavingModeMenuItem();

        void handleDeepLink();

        void onClickFavouritesActivity();
    }

}
