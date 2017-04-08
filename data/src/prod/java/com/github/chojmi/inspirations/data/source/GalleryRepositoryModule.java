package com.github.chojmi.inspirations.data.source;

import android.content.Context;

import com.github.chojmi.inspirations.data.source.local.LocalGalleryDataSource;
import com.github.chojmi.inspirations.data.source.remote.RemoteGalleryDataSource;
import com.github.chojmi.inspirations.domain.repository.GalleryDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class GalleryRepositoryModule {

    @Provides
    @Local
    GalleryDataSource provideLocalGalleryDataSource(Context context) {
        return new LocalGalleryDataSource(context);
    }

    @Provides
    @Remote
    GalleryDataSource provideRemoteGalleryDataSource(Context context) {
        return new RemoteGalleryDataSource(context);
    }

    @Provides
    GalleryDataSource provideUserRepository(GalleryRepository galleryRepository) {
        return galleryRepository;
    }
}
