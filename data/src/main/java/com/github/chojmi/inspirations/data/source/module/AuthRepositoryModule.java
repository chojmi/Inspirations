package com.github.chojmi.inspirations.data.source.module;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.data.source.local.AccessTokenHolder;
import com.github.chojmi.inspirations.data.source.local.LocalAuthDataSource;
import com.github.chojmi.inspirations.data.source.remote.data_source.RemoteAuthDataSource;
import com.github.chojmi.inspirations.data.source.repository.AuthRepository;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class AuthRepositoryModule {
    @Provides
    @Local
    AuthDataSource providLocalAuthDataSource(LocalAuthDataSource remoteAuthDataSource) {
        return remoteAuthDataSource;
    }

    @Provides
    @Remote
    AuthDataSource provideRemoteAuthDataSource(RemoteAuthDataSource remoteAuthDataSource) {
        return remoteAuthDataSource;
    }

    @Singleton
    @Provides
    AuthDataSource provideAuthRepository(AuthRepository authRepository) {
        return authRepository;
    }

    @Provides
    @Local
    AccessTokenHolder getAccessTokenHolder(LocalAuthDataSource localAuthDataSource) {
        return localAuthDataSource;
    }
}
