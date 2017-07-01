package com.github.chojmi.inspirations.data.source.module;

import com.github.chojmi.inspirations.data.source.remote.data_source.RemoteAuthDataSource;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class AuthRepositoryModule {
    @Provides
    @Singleton
    AuthDataSource provideRemoteAuthDataSource(RemoteAuthDataSource remoteAuthDataSource) {
        return remoteAuthDataSource;
    }
}
