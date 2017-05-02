package com.cunoraz.eksiseyler.di.main.content;

import com.cunoraz.eksiseyler.domain.content.ContentUsecase;
import com.cunoraz.eksiseyler.domain.content.ContentUsecaseImpl;
import com.cunoraz.eksiseyler.fragment.ContentContract;
import com.cunoraz.eksiseyler.fragment.ContentPresenter;
import com.cunoraz.eksiseyler.fragment.PerFragment;
import com.cunoraz.eksiseyler.rest.ApiSource;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cuneytcarikci on 02/05/2017.
 */

@Module
public class ContentModule {

    ContentContract.View view;
    String channel;

    public ContentModule(ContentContract.View view, String channel) {
        this.view = view;
        this.channel = channel;
    }

    @Provides
    @PerFragment
    ContentUsecase provideContentUsecase(ApiSource apiSource) {
        return new ContentUsecaseImpl(apiSource);
    }

    @Provides
    @PerFragment
    ContentContract.View provideView() {
        return view;
    }

    @Provides
    @PerFragment
    ContentPresenter provideContentPresenter(ContentUsecase usecase) {
        return new ContentPresenter(usecase,view, channel);
    }
}
