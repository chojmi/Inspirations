package com.github.chojmi.inspirations.data.source.repository;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static dagger.internal.Preconditions.checkNotNull;

public class AuthRepository implements AuthDataSource {
    private final AuthDataSource localAuthDataSource;

    private final AuthDataSource remoteAuthDataSource;

    @Inject
    AuthRepository(@Local @NonNull AuthDataSource localAuthDataSource,
                   @Remote @NonNull AuthDataSource remoteAuthDataSource) {
        this.localAuthDataSource = checkNotNull(localAuthDataSource);
        this.remoteAuthDataSource = checkNotNull(remoteAuthDataSource);
    }

    @Override
    public Observable<OAuth1RequestToken> getRequestToken() {
        return Observable.concat(localAuthDataSource.getRequestToken(),
                remoteAuthDataSource.getRequestToken())
                .firstElement()
                .toObservable()
                .doOnNext(localAuthDataSource::saveRequestToken);
    }

    @Override
    public Observable<String> getAuthorizationUrl() {
        return getRequestToken()
                .flatMap(requestToken -> getAuthorizationUrl(requestToken));
    }

    @Override
    public Observable<String> getAuthorizationUrl(OAuth1RequestToken requestToken) {
        return remoteAuthDataSource.getAuthorizationUrl(requestToken);
    }

    @Override
    public Observable<OAuth1AccessToken> getAccessToken(OAuth1RequestToken requestToken, String oauthVerifier) {
        return remoteAuthDataSource.getAccessToken(requestToken, oauthVerifier)
                .doOnNext(localAuthDataSource::saveAccessToken);
    }

    @Override
    public Observable<OAuth1AccessToken> getAccessToken(String oauthVerifier) {
        return Observable.concat(localAuthDataSource.getAccessToken(""),
                getRequestToken().flatMap(requestToken -> getAccessToken(requestToken, oauthVerifier)))
                .firstElement()
                .toObservable();
    }

    @Override
    public void saveRequestToken(@Nullable OAuth1RequestToken requestToken) {
        localAuthDataSource.saveRequestToken(requestToken);
    }

    @Override
    public void saveAccessToken(@Nullable OAuth1AccessToken accessToken) {
        localAuthDataSource.saveAccessToken(accessToken);
    }
}