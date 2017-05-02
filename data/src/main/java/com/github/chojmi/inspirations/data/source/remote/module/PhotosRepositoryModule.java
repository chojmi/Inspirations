package com.github.chojmi.inspirations.data.source.remote.module;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.data.source.local.LocalPhotosDataSource;
import com.github.chojmi.inspirations.data.source.remote.data_source.RemotePhotosDataSource;
import com.github.chojmi.inspirations.data.source.repository.PhotosRepository;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class PhotosRepositoryModule {
    @Provides
    @Local
    PhotosDataSource provideLocalPhotosDataSource(LocalPhotosDataSource localPhotosDataSource) {
        return localPhotosDataSource;
    }

    @Provides
    @Remote
    PhotosDataSource provideRemotePhotosDataSource(RemotePhotosDataSource remotePhotosDataSource) {
        return remotePhotosDataSource;
    }

    @Provides
    PhotosDataSource provideGalleryDataSource(PhotosRepository photosRepository) {
        return photosRepository;
    }
}
