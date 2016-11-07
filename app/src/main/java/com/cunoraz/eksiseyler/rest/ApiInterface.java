package com.cunoraz.eksiseyler.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ApiInterface {


    @GET("kategori/{kanal}")
    Call<ResponseBody> getSiteContent(@Path("kanal") String channel);

}
