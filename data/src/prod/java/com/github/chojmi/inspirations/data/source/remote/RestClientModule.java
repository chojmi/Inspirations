package com.github.chojmi.inspirations.data.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.data.source.remote.interceptors.ParsingInterceptor;
import com.github.chojmi.presentation.data.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.internal.Preconditions;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
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
                .registerTypeAdapterFactory(RestAdapterFactory.create())
                .setLenient()
                .create();
    }

    @Provides
    @Singleton
    String serviceAddress() {
        return context.getString(R.string.api_url);
    }
}