package com.cunoraz.eksiseyler.di.content;

import com.cunoraz.eksiseyler.di.PerFragment;
import com.cunoraz.eksiseyler.domain.content.ContentUsecase;
import com.cunoraz.eksiseyler.domain.content.ContentUsecaseImpl;
import com.cunoraz.eksiseyler.model.rest.ApiSource;
import com.cunoraz.eksiseyler.ui.content.ContentContract;
import com.cunoraz.eksiseyler.ui.content.ContentPresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cuneytcarikci on 02/05/2017.
 */

@Module
public class ContentModule {

    private String mChannel;
    private ContentContract.View mView;

    public ContentModule(ContentContract.View view, String channel) {
        this.mView = view;
        this.mChannel = channel;
    }

    @Provides
    @PerFragment
    ContentUsecase provideContentUsecase(ApiSource apiSource) {
        return new ContentUsecaseImpl(apiSource);
    }

    @Provides
    @PerFragment
    ContentContract.View provideView() {
        return mView;
    }

    @Provides
    @PerFragment
    @Named("channel")
    String provideChannel() {
        return mChannel;
    }

    /*@Provides
    @PerFragment
    ContentPresenter provideContentPresenter(ContentUsecase usecase) {
        return new ContentPresenter(usecase, view, channel);
    }*/

}