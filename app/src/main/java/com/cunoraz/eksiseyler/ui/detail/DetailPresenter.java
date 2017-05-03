package com.cunoraz.eksiseyler.ui.detail;

import com.cunoraz.eksiseyler.domain.detail.DetailUsecase;
import com.cunoraz.eksiseyler.model.rest.entity.Post;
import com.cunoraz.eksiseyler.ui.base.BasePresenter;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by andanicalik on 03/05/17.
 */

public class DetailPresenter extends BasePresenter<DetailContract.View> implements DetailContract.Presenter {

    private DetailUsecase mDetailUsecase;
    private String mChannel;
    private Post mPost;

    @Inject
    public DetailPresenter(DetailUsecase detailUsecase, @Named("channel") String channel, Post post) {
        this.mDetailUsecase = detailUsecase;
        this.mChannel = channel;
        this.mPost = post;
    }

    @Override
    public void onViewReady() {
        if (mPost != null && mPost.getUrl() != null && !mPost.getUrl().isEmpty()) {

            if (mChannel != null && !mChannel.isEmpty()) {
                getView().updateToolbarTitle(mChannel);
                getView().loadHeaderImage(mPost.getImg());
            }

            getView().updateWebViewLoadImage(mDetailUsecase.isSavingModeActive());
            getView().loadWebView(mPost.getUrl());

        } else
            getView().finishActivity();
    }

    @Override
    public boolean isSavingModeActive() {
        return mDetailUsecase.isSavingModeActive();
    }

    @Override
    public boolean isOneOfFavouritePosts() {
        return mDetailUsecase.isOneOfFavouritePosts(mPost);
    }

    @Override
    public void onClickSavingModeMenuItem() {
        boolean isSavingModeActive = mDetailUsecase.isSavingModeActive();
        boolean newSavingModeStatus = !isSavingModeActive;
        mDetailUsecase.storeSavingModeStatus(newSavingModeStatus);
        getView().updateSavingModeMenuItem(newSavingModeStatus);
        getView().updateWebViewLoadImage(newSavingModeStatus);

        if (newSavingModeStatus)
            getView().showSavingModeActiveDialog();
        else
            getView().showSavingModeNotActiveDialog();
    }

    @Override
    public void onClickAddRemoveFavourite() {
        boolean isOneOfFavouritePosts = mDetailUsecase.isOneOfFavouritePosts(mPost);

        if (isOneOfFavouritePosts) {
            mDetailUsecase.removeFromFavouritePosts(mPost);
            getView().updateFavouriteMenuItem(false);
            getView().showRemovedFromFavourites();
        } else {
            if (mPost.getImg() != null && !mPost.getImg().isEmpty()) {
                mDetailUsecase.addToFavouritePosts(mPost);
                getView().updateFavouriteMenuItem(true);
                getView().showAddedToFavourites();
            } else
                getView().showToastMessage("Favorilere eklenemedi, lütfen daha sonra tekrar deneyin!");
        }
    }

}