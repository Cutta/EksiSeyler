package com.cunoraz.eksiseyler.domain.search;

import com.cunoraz.eksiseyler.model.rest.ApiSource;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by cuneytcarikci on 08/05/2017.
 */

public class SearchUseCaseImpl implements SearchUseCase {

    private ApiSource apiSource;

    public SearchUseCaseImpl(ApiSource apiSource) {
        this.apiSource = apiSource;
    }

    @Override
    public Call<ResponseBody> getSearchResultList(String channel) {
        return apiSource.getSearchResult(channel);
    }
}
