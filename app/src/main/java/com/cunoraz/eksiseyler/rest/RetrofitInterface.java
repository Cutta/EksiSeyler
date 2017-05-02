package com.cunoraz.eksiseyler.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface RetrofitInterface {

    @GET("{kanal}")
    Call<ResponseBody> getPostList(@Path("kanal") String channel);

}
