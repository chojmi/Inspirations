package com.github.chojmi.inspirations.data.source;

import android.content.Context;

import com.github.chojmi.inspirations.data.source.local.LocalGalleryDataSource;
import com.github.chojmi.inspirations.data.source.remote.RemoteGalleryDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class GalleryRepositoryModule {

    @Singleton
    @Provides
    @Local
    GalleryDataSource provideLocalGalleryDataSource(Context context) {
        return new LocalGalleryDataSource(context);
    }

    @Singleton
    @Provides
    @Remote
    GalleryDataSource provideRemoteGalleryDataSource(Context context) {
        return new RemoteGalleryDataSource(context);
    }
}
