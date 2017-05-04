package com.cunoraz.eksiseyler.ui.favourites;

import com.cunoraz.eksiseyler.domain.favourites.FavouritesUsecase;
import com.cunoraz.eksiseyler.model.rest.entity.Post;
import com.cunoraz.eksiseyler.ui.base.BasePresenter;
import com.cunoraz.eksiseyler.ui.content.PostAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by cuneytcarikci on 04/05/2017.
 */

public class FavouritePresenter extends BasePresenter<FavouritiesContract.View> implements FavouritiesContract.Presenter {

    private FavouritesUsecase mFavouritesUsecase;

    @Inject
    public FavouritePresenter(FavouritesUsecase favouritesUsecase) {
        this.mFavouritesUsecase = favouritesUsecase;
    }

    @Override
    public void onViewReady() {

        ArrayList<Post> posts = (ArrayList<Post>) mFavouritesUsecase.getPostListFromSharedPreference();
        getView().loadPosts(posts);

    }

    @Override
    public void onResume() {
        ArrayList<Post> posts = (ArrayList<Post>) mFavouritesUsecase.getPostListFromSharedPreference();
        getView().loadPosts(posts);
    }

    @Override
    public void onClickPost(PostAdapter.PostViewHolder viewHolder, Post post) {
        getView().openDetail(viewHolder, post);
    }

    @Override
    public boolean isSavingModeActive() {
        return mFavouritesUsecase.isSavingModeActive();
    }

    @Override
    public void onClickSavingModeMenuItem() {
        boolean isSavingModeActive = mFavouritesUsecase.isSavingModeActive();
        boolean newSavingModeStatus = !isSavingModeActive;
        mFavouritesUsecase.storeSavingModeStatus(newSavingModeStatus);
        getView().updateSavingModeMenuItem(newSavingModeStatus);

        if (newSavingModeStatus)
            getView().showSavingModeActiveDialog();
        else
            getView().showSavingModeNotActiveDialog();
    }
}
