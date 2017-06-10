package com.github.chojmi.inspirations.data.source.module;

import com.github.chojmi.inspirations.data.source.local.LocalAuthDataSource;
import com.github.chojmi.inspirations.data.source.remote.signing.SignatureProvider;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class AuthRepositoryModule {

    @Provides
    AuthDataSource provideGalleryDataSource() {
        return new LocalAuthDataSource();
    }

    @Provides
    SignatureProvider provideSignatureProvider() {
        return new SignatureProvider("fake_key");
    }
}

