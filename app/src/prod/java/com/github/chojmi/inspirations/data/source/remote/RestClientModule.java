package com.github.chojmi.inspirations.data.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.R;
import com.github.chojmi.inspirations.data.source.remote.interceptors.ParsingInterceptor;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.ryanharter.auto.value.gson.AutoValueGsonTypeAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class RestClientModule {

    private static final HttpLoggingInterceptor.Level LOG_LEVEL = HttpLoggingInterceptor.Level.BASIC;

    private final Context context;

    RestClientModule(@NonNull Context context) {
        this.context = Preconditions.checkNotNull(context);
    }

    @Provides
    @Singleton
    GalleryService streamService(Retrofit retrofit) {
        return retrofit.create(GalleryService.class);
    }

    @Provides
    @Singleton
    Retrofit retrofit(OkHttpClient okHttpClient, String serviceAddress) {
        return new Retrofit.Builder()
                .baseUrl(serviceAddress)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(LOG_LEVEL))
                .addInterceptor(new ParsingInterceptor())
                .followSslRedirects(true)
                .build();
    }

    @Provides
    @Singleton
    Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(new AutoValueGsonTypeAdapterFactory())
                .setLenient()
                .create();
    }

    @Provides
    @Singleton
    String serviceAddress() {
        return context.getString(R.string.api_url);
    }
}