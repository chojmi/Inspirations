package com.github.chojmi.inspirations.data.source.remote.module;

import com.github.chojmi.inspirations.data.source.remote.data_source.RemoteAuthDataSource;
import com.github.chojmi.inspirations.data.source.remote.signing.SignatureProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class AuthRepositoryModule {
    @Provides
    RemoteAuthDataSource provideRemoteAuthDataSource() {
        return null;
    }

    @Provides
    SignatureProvider provideSignatureProvider() {
        return new SignatureProvider("fake_key");
    }
}
