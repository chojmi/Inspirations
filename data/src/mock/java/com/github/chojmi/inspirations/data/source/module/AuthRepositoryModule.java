package com.github.chojmi.inspirations.data.source.module;

import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.scribejava.core.model.OAuth1AccessToken;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Flowable;

@Module
public class AuthRepositoryModule {
    @Provides
    AuthDataSource provideAuthDataSource() {
        return new AuthDataSource() {

            @Override
            public Flowable<String> getAuthorizationUrl() {
                return Flowable.empty();
            }

            @Override
            public Flowable<OAuth1AccessToken> getAccessToken(String oauthVerifier) {
                return Flowable.empty();
            }
        };
    }
}
