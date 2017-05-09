package com.cunoraz.eksiseyler.model.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by cuneytcarikci on 02/05/2017.
 */

public interface ApiSource {

    Call<ResponseBody> getPostList(String channel);

    Call<ResponseBody> getSearchResult(String query);
}
