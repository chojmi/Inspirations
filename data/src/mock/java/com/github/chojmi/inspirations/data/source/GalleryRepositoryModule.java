package com.github.chojmi.inspirations.data.source;

import android.content.Context;

import com.github.chojmi.inspirations.data.source.remote.FakeRemoteGalleryDataSource;
import com.github.chojmi.inspirations.data.source.local.LocalGalleryDataSource;
import com.github.chojmi.inspirations.domain.repository.GalleryDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class GalleryRepositoryModule {

    @Provides
    @Local
    GalleryDataSource provideGalleryLocalDataSource(Context context) {
        return new LocalGalleryDataSource(context);
    }

    @Provides
    @Remote
    GalleryDataSource provideGalleryRemoteDataSource() {
        return new FakeRemoteGalleryDataSource();
    }

    @Provides
    GalleryDataSource provideGalleryRepository(GalleryRepository galleryRepository) {
        return galleryRepository;
    }
}
