package com.github.chojmi.inspirations.data.source.remote.module;

import com.github.chojmi.inspirations.data.source.remote.data_source.RemoteAuthDataSource;
import com.github.chojmi.inspirations.data.source.remote.service.AuthService;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class AuthRepositoryModule {
    @Provides
    RemoteAuthDataSource provideRemoteAuthDataSource(AuthService authService, RemoteQueryProducer remoteQueryProducer) {
        return new RemoteAuthDataSource(authService, remoteQueryProducer);
    }
}
