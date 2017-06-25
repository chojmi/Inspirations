package com.github.chojmi.inspirations.data.source.module;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.data.source.local.LocalAuthTestDataSource;
import com.github.chojmi.inspirations.data.source.remote.data_source.RemoteAuthTestDataSource;
import com.github.chojmi.inspirations.data.source.repository.AuthTestRepository;
import com.github.chojmi.inspirations.domain.repository.AuthTestDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class AuthTestRepositoryModule {
    @Provides
    @Local
    AuthTestDataSource provideLocalGalleriesDataSource(LocalAuthTestDataSource localAuthTestDataSource) {
        return localAuthTestDataSource;
    }

    @Provides
    @Remote
    AuthTestDataSource provideRemoteGalleriesDataSource(RemoteAuthTestDataSource remoteAuthTestDataSource) {
        return remoteAuthTestDataSource;
    }

    @Singleton
    @Provides
    AuthTestDataSource provideGalleriesDataSource(AuthTestRepository authTestRepository) {
        return authTestRepository;
    }
}
