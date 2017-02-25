package com.github.chojmi.inspirations.data.source;

import android.content.Context;

import com.github.chojmi.inspirations.data.source.local.LocalGalleryDataSource;
import com.github.chojmi.inspirations.data.source.remote.FakeRemoteGalleryDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class GalleryRepositoryModule {

    @Singleton
    @Provides
    @Local
    GalleryDataSource provideStreamLocalDataSource(Context context) {
        return new LocalGalleryDataSource(context);
    }

    @Singleton
    @Provides
    @Remote
    GalleryDataSource provideStreamRemoteDataSource() {
        return new FakeRemoteGalleryDataSource();
    }
}
