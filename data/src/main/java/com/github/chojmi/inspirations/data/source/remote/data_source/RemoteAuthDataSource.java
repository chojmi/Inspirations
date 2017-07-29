package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.source.remote.service.OAuthService;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class RemoteAuthDataSource implements AuthDataSource {
    private final OAuthService oAuthService;

    @Inject
    public RemoteAuthDataSource(@NonNull OAuthService oAuthService) {
        this.oAuthService = checkNotNull(oAuthService);
    }

    @Override
    public Observable<OAuth1RequestToken> getRequestToken() {
        return Observable.fromCallable(oAuthService::getRequestToken);
    }

    @Override
    public Observable<String> getAuthorizationUrl() {
        return Observable.error(new Throwable("Needs request token"));
    }

    @Override
    public Observable<String> getAuthorizationUrl(@NonNull OAuth1RequestToken requestToken) {
        return Observable.fromCallable(oAuthService::getRequestToken)
                .map(oAuth1RequestToken -> oAuthService.getAuthorizationUrl(requestToken));
    }

    @Override
    public Observable<OAuth1AccessToken> getAccessToken(OAuth1RequestToken requestToken, String oauthVerifier) {
        return Observable.fromCallable(() -> oAuthService.getAccessToken(requestToken, oauthVerifier));
    }

    @Override
    public Observable<OAuth1AccessToken> getAccessToken(String oauthVerifier) {
        return Observable.error(new Throwable("Needs request token"));
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
