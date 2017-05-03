package com.cunoraz.eksiseyler.di.detail;

import com.cunoraz.eksiseyler.di.PerActivity;
import com.cunoraz.eksiseyler.domain.detail.DetailUsecase;
import com.cunoraz.eksiseyler.domain.detail.DetailUsecaseImpl;
import com.cunoraz.eksiseyler.model.pref.PreferencesHelper;
import com.cunoraz.eksiseyler.model.rest.entity.Post;
import com.cunoraz.eksiseyler.ui.detail.DetailContract;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andanicalik on 03/05/17.
 */
@Module
public class DetailModule {

    private Post mPost;
    private String mChannel;
    private DetailContract.View mView;

    public DetailModule(DetailContract.View view, Post post, String channel) {
        this.mView = view;
        this.mPost = post;
        this.mChannel = channel;
    }

    @PerActivity
    @Provides
    DetailUsecase provideDetailUsecase(PreferencesHelper preferencesHelper) {
        return new DetailUsecaseImpl(preferencesHelper);
    }

    @PerActivity
    @Provides
    Post providePost() {
        return mPost;
    }

    @PerActivity
    @Provides
    @Named("channel")
    String provideChannel() {
        return mChannel;
    }

    @PerActivity
    @Provides
    DetailContract.View provideView() {
        return mView;
    }

}