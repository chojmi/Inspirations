package com.github.chojmi.inspirations.data.source.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.data.source.remote.RestAdapterFactory;
import com.github.chojmi.inspirations.data.source.remote.interceptors.ParsingInterceptor;
import com.github.chojmi.inspirations.data.source.remote.service.GalleriesService;
import com.github.chojmi.inspirations.data.source.remote.service.PeopleService;
import com.github.chojmi.inspirations.data.source.remote.service.PhotosService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducerImpl;
import com.github.chojmi.presentation.data.BuildConfig;
import com.github.chojmi.presentation.data.R;
import com.github.scribejava.apis.FlickrApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static dagger.internal.Preconditions.checkNotNull;

@Remote
@Module
public class RestClientModule {

    private static final HttpLoggingInterceptor.Level LOG_LEVEL = HttpLoggingInterceptor.Level.BASIC;

    private final Context context;

    public RestClientModule(@NonNull Context context) {
        this.context = checkNotNull(context);
    }

    @Provides
    OAuth10aService provideOAuthService() {
        return new ServiceBuilder()
                .apiKey(BuildConfig.API_KEY)
                .apiSecret(BuildConfig.API_SECRET_KEY)
                .build(FlickrApi.instance(FlickrApi.FlickrPerm.WRITE));
    }

    @Provides
    GalleriesService provideGalleryService(Retrofit retrofit) {
        return retrofit.create(GalleriesService.class);
    }

    @Provides
    PeopleService providePeopleService(Retrofit retrofit) {
        return retrofit.create(PeopleService.class);
    }

    @Provides
    PhotosService providePhotosService(Retrofit retrofit) {
        return retrofit.create(PhotosService.class);
    }

    @Provides
    RemoteQueryProducer provideRemoteQueryProducer(RemoteQueryProducerImpl remoteQueryProducer) {
        return remoteQueryProducer;
    }

    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson, String serviceAddress) {
        return new Retrofit.Builder()
                .baseUrl(serviceAddress)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(LOG_LEVEL))
                .addInterceptor(new ParsingInterceptor())
                .followSslRedirects(true)
                .build();
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(RestAdapterFactory.create())
                .setLenient()
                .create();
    }

    @Provides
    String provideServiceAddress() {
        return context.getString(R.string.api_url);
    }
}