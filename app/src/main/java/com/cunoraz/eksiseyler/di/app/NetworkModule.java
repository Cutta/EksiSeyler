package com.cunoraz.eksiseyler.di.app;

import com.cunoraz.eksiseyler.rest.ApiSource;
import com.cunoraz.eksiseyler.rest.ApiSourceImpl;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by cuneytcarikci on 02/05/2017.
 */

@Module
public class NetworkModule {
    private static final String BASE_URL = "https://seyler.eksisozluk.com/";

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ApiSource provideApiSource(Retrofit retrofit) {
        return new ApiSourceImpl(retrofit);
    }
}
