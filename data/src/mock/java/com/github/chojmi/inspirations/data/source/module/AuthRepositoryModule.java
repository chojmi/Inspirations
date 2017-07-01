package com.github.chojmi.inspirations.data.source.module;

import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.chojmi.inspirations.domain.repository.AuthTestDataSource;
import com.github.scribejava.core.model.OAuth1AccessToken;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;

@Module
public class AuthRepositoryModule {
    @Provides
    AuthDataSource provideAuthDataSource() {
        return new AuthDataSource() {

            @Override
            public Observable<String> getAuthorizationUrl() {
                return null;
            }

            @Override
            public Observable<OAuth1AccessToken> getAccessToken(String oauthVerifier) {
                return null;
            }
        };
    }
}
