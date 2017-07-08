package com.github.chojmi.inspirations.data.source.repository;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import org.reactivestreams.Publisher;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Function;

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
    public Flowable<OAuth1RequestToken> getRequestToken() {
        return Flowable.concat(localAuthDataSource.getRequestToken(),
                remoteAuthDataSource.getRequestToken())
                .firstElement()
                .toFlowable()
                .doOnNext(localAuthDataSource::saveRequestToken);
    }

    @Override
    public Flowable<String> getAuthorizationUrl() {
        return getRequestToken()
                .flatMap(new Function<OAuth1RequestToken, Publisher<String>>() {
                    @Override
                    public Publisher<String> apply(@NonNull OAuth1RequestToken requestToken) throws Exception {
                        return getAuthorizationUrl(requestToken);
                    }
                });
    }

    @Override
    public Flowable<String> getAuthorizationUrl(OAuth1RequestToken requestToken) {
        return remoteAuthDataSource.getAuthorizationUrl(requestToken);
    }

    @Override
    public Flowable<OAuth1AccessToken> getAccessToken(OAuth1RequestToken requestToken, String oauthVerifier) {
        return remoteAuthDataSource.getAccessToken(requestToken, oauthVerifier)
                .doOnNext(localAuthDataSource::saveAccessToken);
    }

    @Override
    public Flowable<OAuth1AccessToken> getAccessToken(String oauthVerifier) {
        return Flowable.concat(localAuthDataSource.getAccessToken(""),
                getRequestToken().flatMap(new Function<OAuth1RequestToken, Publisher<OAuth1AccessToken>>() {
                    @Override
                    public Publisher<OAuth1AccessToken> apply(@NonNull OAuth1RequestToken requestToken) throws Exception {
                        return getAccessToken(requestToken, oauthVerifier);
                    }
                }))
                .firstElement()
                .toFlowable();
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