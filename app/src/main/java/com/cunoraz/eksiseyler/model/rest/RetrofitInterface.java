package com.cunoraz.eksiseyler.model.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface RetrofitInterface {

    @GET("{kanal}")
    Call<ResponseBody> getPostList(@Path("kanal") String channel);


    @POST("derleme/arama/sonuc")
    @FormUrlEncoded
    Call<ResponseBody> getSearchResult(@Field("Query") String Query);

}
