package com.github.chojmi.inspirations.data.source.module;

import android.content.Context;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.data.source.local.LocalPhotosDataSource;
import com.github.chojmi.inspirations.data.source.remote.FakeRemotePhotosDataSource;
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
    PhotosDataSource providePeopleLocalDataSource(Context context) {
        return new LocalPhotosDataSource(context);
    }

    @Provides
    @Remote
    PhotosDataSource providePeopleRemoteDataSource() {
        return new FakeRemotePhotosDataSource();
    }

    @Provides
    PhotosDataSource providePeopleRepository(PhotosRepository photosRepository) {
        return photosRepository;
    }
}
