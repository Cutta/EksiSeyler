package com.cunoraz.eksiseyler.di.search;

import com.cunoraz.eksiseyler.di.PerActivity;
import com.cunoraz.eksiseyler.domain.search.SearchUseCase;
import com.cunoraz.eksiseyler.domain.search.SearchUseCaseImpl;
import com.cunoraz.eksiseyler.model.rest.ApiSource;
import com.cunoraz.eksiseyler.ui.search.SearchContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cuneytcarikci on 08/05/2017.
 */

@Module
public class SearchModule {

    private SearchContract.View mView;

    public SearchModule(SearchContract.View mView) {
        this.mView = mView;
    }

    @PerActivity
    @Provides
    SearchUseCase provideSearchUsecase(ApiSource apiSource) {
        return new SearchUseCaseImpl(apiSource);
    }

    @PerActivity
    @Provides
    SearchContract.View provideView() {
        return mView;
    }
}
