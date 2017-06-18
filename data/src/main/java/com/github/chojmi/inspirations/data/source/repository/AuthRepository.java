package com.github.chojmi.inspirations.data.source.repository;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.scribejava.core.model.OAuth1AccessToken;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

import static dagger.internal.Preconditions.checkNotNull;

public class AuthRepository implements AuthDataSource {
    private final AuthDataSource authRemoteDataSource;

    private final AuthDataSource authLocalDataSource;

    @Inject
    AuthRepository(@Remote @NonNull AuthDataSource authRemoteDataSource,
                   @Local @NonNull AuthDataSource authLocalDataSource) {
        this.authRemoteDataSource = checkNotNull(authRemoteDataSource);
        this.authLocalDataSource = checkNotNull(authLocalDataSource);
    }

    @Override
    public Observable<String> getAuthorizationUrl() {
        return Observable.concat(authLocalDataSource.getAuthorizationUrl(),
                authRemoteDataSource.getAuthorizationUrl()).firstElement().toObservable();
    }

    @Override
    public Observable<OAuth1AccessToken> getAccessToken(String oauthVerifier) {
        return Observable.concat(authLocalDataSource.getAccessToken(oauthVerifier),
                authRemoteDataSource.getAccessToken(oauthVerifier)).firstElement().toObservable();
    }
}
