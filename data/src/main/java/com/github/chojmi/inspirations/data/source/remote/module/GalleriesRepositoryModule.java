package com.github.chojmi.inspirations.data.source.remote.module;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.data.source.local.LocalGalleriesDataSource;
import com.github.chojmi.inspirations.data.source.remote.data_source.RemoteGalleriesDataSource;
import com.github.chojmi.inspirations.data.source.repository.GalleriesRepository;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class GalleriesRepositoryModule {

    @Provides
    @Local
    GalleriesDataSource provideLocalGalleriesDataSource(LocalGalleriesDataSource localGalleriesDataSource) {
        return localGalleriesDataSource;
    }

    @Provides
    @Remote
    GalleriesDataSource provideRemoteGalleriesDataSource(RemoteGalleriesDataSource remoteGalleriesDataSource) {
        return remoteGalleriesDataSource;
    }

    @Singleton
    @Provides
    GalleriesDataSource provideGalleriesDataSource(GalleriesRepository galleriesRepository) {
        return galleriesRepository;
    }
}
