package com.github.chojmi.inspirations.data.source.remote.module;

import com.github.chojmi.inspirations.data.source.remote.signing.SignatureProvider;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class AuthRepositoryModule {
    @Provides
    AuthDataSource provideRemoteAuthDataSource() {
        return null;
    }

    @Provides
    SignatureProvider provideSignatureProvider() {
        return new SignatureProvider("fake_key");
    }
}
