package com.github.chojmi.inspirations.data.source.local;

import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.annotations.Nullable;

@Singleton
public class LocalAuthDataSource implements AuthDataSource, AccessTokenHolder {
    private OAuth1RequestToken requestToken;
    private OAuth1AccessToken accessToken;

    @Inject
    public LocalAuthDataSource() {
    }

    @Override
    public Flowable<OAuth1RequestToken> getRequestToken() {
        return requestToken != null ? Flowable.just(requestToken) : Flowable.empty();
    }

    @Override
    public Flowable<String> getAuthorizationUrl() {
        return Flowable.empty();
    }

    @Override
    public Flowable<String> getAuthorizationUrl(OAuth1RequestToken requestToken) {
        return Flowable.empty();
    }

    @Override
    public Flowable<OAuth1AccessToken> getAccessToken(OAuth1RequestToken requestToken, String oauthVerifier) {
        return accessToken != null ? Flowable.just(accessToken) : Flowable.empty();
    }

    @Override
    public Flowable<OAuth1AccessToken> getAccessToken(String oauthVerifier) {
        return accessToken != null ? Flowable.just(accessToken) : Flowable.empty();
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
