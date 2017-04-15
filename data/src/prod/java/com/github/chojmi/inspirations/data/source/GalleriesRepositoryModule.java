package com.github.chojmi.inspirations.data.source;

import android.content.Context;

import com.github.chojmi.inspirations.data.source.local.LocalGalleriesDataSource;
import com.github.chojmi.inspirations.data.source.remote.RemoteGalleriesDataSource;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class GalleriesRepositoryModule {

    @Provides
    @Local
    GalleriesDataSource provideLocalGalleriesDataSource(Context context) {
        return new LocalGalleriesDataSource(context);
    }

    @Provides
    @Remote
    GalleriesDataSource provideRemoteGalleriesDataSource(Context context) {
        return new RemoteGalleriesDataSource(context);
    }

    @Provides
    GalleriesDataSource provideGalleriesDataSource(GalleriesRepository galleriesRepository) {
        return galleriesRepository;
    }
}
