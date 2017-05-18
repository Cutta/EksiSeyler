package com.cunoraz.eksiseyler.model.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by cuneytcarikci on 02/05/2017.
 */

public class ApiSourceImpl implements ApiSource {
    private RetrofitInterface retrofitInterface;

    public ApiSourceImpl(Retrofit retrofit) {
        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    @Override
    public Call<ResponseBody> getPostList(String channel) {
        return retrofitInterface.getPostList(channel);
    }

    @Override
    public Call<ResponseBody> getSearchResult(String query) {
        return retrofitInterface.getSearchResult(query);
    }

    @Override
    public Call<ResponseBody> getContentPureHtml(String encodedPostName) {
        return retrofitInterface.getPostPureHtml(encodedPostName);
    }
}
