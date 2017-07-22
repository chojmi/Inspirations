package com.github.chojmi.inspirations.data.source.local;

import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;

@Singleton
public class LocalAuthDataSource implements AuthDataSource, AccessTokenHolder {
    private OAuth1RequestToken requestToken;
    private OAuth1AccessToken accessToken;

    @Inject
    public LocalAuthDataSource() {
    }

    @Override
    public Observable<OAuth1RequestToken> getRequestToken() {
        return requestToken != null ? Observable.just(requestToken) : Observable.empty();
    }

    @Override
    public Observable<String> getAuthorizationUrl() {
        return Observable.empty();
    }

    @Override
    public Observable<String> getAuthorizationUrl(OAuth1RequestToken requestToken) {
        return Observable.empty();
    }

    @Override
    public Observable<OAuth1AccessToken> getAccessToken(OAuth1RequestToken requestToken, String oauthVerifier) {
        return accessToken != null ? Observable.just(accessToken) : Observable.empty();
    }

    @Override
    public Observable<OAuth1AccessToken> getAccessToken(String oauthVerifier) {
        return accessToken != null ? Observable.just(accessToken) : Observable.empty();
    }

    @Override
    public void saveRequestToken(@Nullable OAuth1RequestToken requestToken) {
        this.requestToken = requestToken;
    }

    @Override
    public void saveAccessToken(@Nullable OAuth1AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public OAuth1AccessToken getAccessToken() {
        return accessToken;
    }

    @Override
    public boolean containsAccessToken() {
        return accessToken != null;
    }
}
