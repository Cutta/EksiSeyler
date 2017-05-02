package com.cunoraz.eksiseyler.domain.content;

import com.cunoraz.eksiseyler.rest.ApiSource;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by cuneytcarikci on 02/05/2017.
 */

public class ContentUsecaseImpl implements ContentUsecase{

    private ApiSource apiSource;

    public ContentUsecaseImpl(ApiSource apiSource) {
        this.apiSource = apiSource;
    }

    @Override
    public Call<ResponseBody> getPostList(String channel) {
        return apiSource.getPostList(channel);
    }
}
