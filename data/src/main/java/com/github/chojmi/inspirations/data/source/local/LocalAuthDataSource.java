package com.github.chojmi.inspirations.data.source.local;

import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.scribejava.core.model.OAuth1AccessToken;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LocalAuthDataSource implements AuthDataSource {

    @Inject
    public LocalAuthDataSource() {
    }

    @Override
    public Observable<String> getAuthorizationUrl() {
        return Observable.empty();
    }

    @Override
    public Observable<OAuth1AccessToken> getAccessToken(String oauthVerifier) {
        return Observable.empty();
    }
}
