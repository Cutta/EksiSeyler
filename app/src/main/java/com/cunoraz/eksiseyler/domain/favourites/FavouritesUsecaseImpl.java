package com.cunoraz.eksiseyler.domain.favourites;

import com.cunoraz.eksiseyler.model.pref.PreferencesHelper;
import com.cunoraz.eksiseyler.model.rest.entity.Post;

import java.util.List;

/**
 * Created by cuneytcarikci on 04/05/2017.
 */

public class FavouritesUsecaseImpl implements FavouritesUsecase {

    private PreferencesHelper mPreferencesHelper;

    public FavouritesUsecaseImpl(PreferencesHelper mPreferencesHelper) {
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

    @Override
    public List<Post> getPostListFromSharedPreference() {
        return mPreferencesHelper.getFavouritePosts();
    }


}
