package com.github.chojmi.inspirations.data.source.remote.module;

import com.github.chojmi.inspirations.data.source.remote.data_source.RemoteAuthDataSource;
import com.github.chojmi.inspirations.data.source.remote.service.AuthService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;
import com.github.chojmi.inspirations.data.source.remote.signing.SignatureProvider;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.chojmi.presentation.data.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class AuthRepositoryModule {
    @Provides
    AuthDataSource provideRemoteAuthDataSource(AuthService authService, RemoteQueryProducer remoteQueryProducer) {
        return new RemoteAuthDataSource(authService, remoteQueryProducer);
    }

    @Provides
    SignatureProvider provideSignatureProvider() {
        return new SignatureProvider(BuildConfig.API_SECRET_KEY);
    }
}
