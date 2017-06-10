package com.github.chojmi.inspirations.data.source.module;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.data.source.local.LocalAuthDataSource;
import com.github.chojmi.inspirations.data.source.remote.data_source.RemoteAuthDataSource;
import com.github.chojmi.inspirations.data.source.remote.signing.SignatureProvider;
import com.github.chojmi.inspirations.data.source.repository.AuthRepository;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.chojmi.presentation.data.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class AuthRepositoryModule {
    @Provides
    @Local
    AuthDataSource provideLocalGalleryDataSource(LocalAuthDataSource localAuthDataSource) {
        return localAuthDataSource;
    }

    @Provides
    @Remote
    AuthDataSource provideRemoteAuthDataSource(RemoteAuthDataSource remoteAuthDataSource) {
        return remoteAuthDataSource;
    }

    @Singleton
    @Provides
    AuthDataSource provideGalleryDataSource(AuthRepository authRepository) {
        return authRepository;
    }

    @Singleton
    @Provides
    SignatureProvider provideSignatureProvider() {
        return new SignatureProvider(BuildConfig.API_SECRET_KEY);
    }
}
