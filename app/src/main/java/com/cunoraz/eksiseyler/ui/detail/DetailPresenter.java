package com.cunoraz.eksiseyler.ui.detail;

import android.util.Log;

import com.cunoraz.eksiseyler.R;
import com.cunoraz.eksiseyler.domain.detail.DetailUsecase;
import com.cunoraz.eksiseyler.model.rest.entity.Post;
import com.cunoraz.eksiseyler.ui.base.BasePresenter;
import com.cunoraz.eksiseyler.util.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andanicalik on 03/05/17.
 */

public class DetailPresenter extends BasePresenter<DetailContract.View> implements DetailContract.Presenter {

    private DetailUsecase mDetailUsecase;
    private String mChannel;
    private Post mPost;
    private String mPageSource;

    @Inject
    public DetailPresenter(DetailUsecase detailUsecase, @Named("channel") String channel, Post post, @Named("html") String pageSoruce) {
        this.mDetailUsecase = detailUsecase;
        this.mChannel = channel;
        this.mPost = post;
        this.mPageSource = pageSoruce;
    }

    @Override
    public void onViewReady() {
        if (mPost != null && mPost.getUrl() != null && !mPost.getUrl().isEmpty()) {

            if (mChannel != null && !mChannel.isEmpty())
                getView().updateToolbarTitle(mChannel);

            //urlden tıklayınca image linki yok o yuzden sorumlulugu ayirdim
            if (mPost.getImg() != null && !mPost.getImg().equals(""))
                getView().loadHeaderImage(mPost.getImg());

            getView().updateWebViewLoadImage(mDetailUsecase.isSavingModeActive());

            if (getView().isConnect()) {
                mDetailUsecase.getContentPureHtml(Utils.getEncodedPostName(mPost.getUrl()))
                        .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        try {
                         /*   Element doc = Jsoup.parse((response.body().string()));
                            Element link = doc.getElementsByClass("content-detail-inner").first();
                            String contentMain = link.outerHtml();*/
                            getView().loadWebViewWithTemplate(response.body().string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("onFailure", "onFailure: "+t.getMessage());
                    }
                });
                //getView().loadWebView(mPost.getUrl());
            } else
                getView().loadWebViewWithTemplate(mPageSource);

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
                getView().saveToInternalStorage();
            } else
                getView().showToastMessage(R.string.favourite_adding_error_text);
        }
    }

}