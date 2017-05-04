package com.cunoraz.eksiseyler.ui.detail;

import android.support.annotation.StringRes;

import com.cunoraz.eksiseyler.ui.base.BaseView;

/**
 * Created by andanicalik on 03/05/17.
 */

public class DetailContract {

    public interface View extends BaseView {

        void updateWebViewLoadImage(boolean isSavingModeActive);

        void updateToolbarTitle(String title);

        void loadWebView(String url);

        void loadHeaderImage(String url);

        void showSavingModeActiveDialog();

        void showSavingModeNotActiveDialog();

        void updateSavingModeMenuItem(boolean isActive);

        void showAddedToFavourites();

        void showRemovedFromFavourites();

        void updateFavouriteMenuItem(boolean isOneOfFavouritePosts);

        void showToastMessage(@StringRes int message);

        void showSnackbarMessage(String message);

        void finishActivity();

    }

    interface Presenter {

        void onViewReady();

        boolean isSavingModeActive();

        boolean isOneOfFavouritePosts();

        void onClickSavingModeMenuItem();

        void onClickAddRemoveFavourite();

    }

}