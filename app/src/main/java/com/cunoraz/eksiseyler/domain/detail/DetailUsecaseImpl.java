package com.cunoraz.eksiseyler.domain.detail;

import com.cunoraz.eksiseyler.model.pref.PreferencesHelper;
import com.cunoraz.eksiseyler.model.rest.ApiSource;
import com.cunoraz.eksiseyler.model.rest.entity.Post;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by andanicalik on 03/05/17.
 */

public class DetailUsecaseImpl implements DetailUsecase {

    private ApiSource mApiSource;
    private PreferencesHelper mPreferencesHelper;


    public DetailUsecaseImpl(ApiSource apiSource, PreferencesHelper preferencesHelper) {
        this.mApiSource = apiSource;
        this.mPreferencesHelper = preferencesHelper;
    }

    @Override
    public boolean isSavingModeActive() {
        return mPreferencesHelper.isSavingModeActive();
    }

    @Override
    public void storeSavingModeStatus(boolean isActive) {
        mPreferencesHelper.storeSavingModeStatus(isActive);
    }

    @Override
    public boolean isOneOfFavouritePosts(Post post) {
        return mPreferencesHelper.isOneOfFavouritePosts(post);
    }

    @Override
    public void addToFavouritePosts(Post post) {
        mPreferencesHelper.addToFavouritePosts(post);
    }

    @Override
    public void removeFromFavouritePosts(Post post) {
        mPreferencesHelper.removeFromFavouritePosts(post);
    }

    @Override
    public Call<ResponseBody> getContentPureHtml(String encodedPostName) {
        return mApiSource.getContentPureHtml(encodedPostName);
    }

}