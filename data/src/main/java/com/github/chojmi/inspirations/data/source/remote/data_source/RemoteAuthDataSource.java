package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.source.remote.service.OAuthService;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.schedulers.Schedulers;

import static dagger.internal.Preconditions.checkNotNull;

public class RemoteAuthDataSource implements AuthDataSource {
    private final OAuthService oAuthService;

    @Inject
    public RemoteAuthDataSource(@NonNull OAuthService oAuthService) {
        this.oAuthService = checkNotNull(oAuthService);
    }

    @Override
    public Flowable<OAuth1RequestToken> getRequestToken() {
        return Flowable.fromCallable(oAuthService::getRequestToken)
                .subscribeOn(Schedulers.newThread());
    }

    @Override
    public Flowable<String> getAuthorizationUrl() {
        return Flowable.error(new Throwable("Needs request token"));
    }

    @Override
    public Flowable<String> getAuthorizationUrl(@NonNull OAuth1RequestToken requestToken) {
        return Flowable.fromCallable(oAuthService::getRequestToken)
                .subscribeOn(Schedulers.newThread())
                .map(oAuth1RequestToken -> oAuthService.getAuthorizationUrl(requestToken));
    }

    @Override
    public Flowable<OAuth1AccessToken> getAccessToken(OAuth1RequestToken requestToken, String oauthVerifier) {
        return Flowable.fromCallable(() -> oAuthService.getAccessToken(requestToken, oauthVerifier));
    }

    @Override
    public Flowable<OAuth1AccessToken> getAccessToken(String oauthVerifier) {
        return Flowable.error(new Throwable("Needs request token"));
    }

    @Override
    public void saveRequestToken(OAuth1RequestToken requestToken) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveAccessToken(@Nullable OAuth1AccessToken accessToken) {
        throw new UnsupportedOperationException();
    }
}
