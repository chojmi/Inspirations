package com.github.chojmi.inspirations.data.source;

import android.content.Context;

import com.github.chojmi.inspirations.data.source.local.LocalGalleriesDataSource;
import com.github.chojmi.inspirations.data.source.remote.FakeRemoteGalleriesDataSource;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class GalleriesRepositoryModule {

    @Provides
    @Local
    GalleriesDataSource provideGalleriesLocalDataSource(Context context) {
        return new LocalGalleriesDataSource(context);
    }

    @Provides
    @Remote
    GalleriesDataSource provideGalleriesRemoteDataSource() {
        return new FakeRemoteGalleriesDataSource();
    }

    @Provides
    GalleriesDataSource provideGalleriesRepository(GalleriesRepository galleriesRepository) {
        return galleriesRepository;
    }
}
