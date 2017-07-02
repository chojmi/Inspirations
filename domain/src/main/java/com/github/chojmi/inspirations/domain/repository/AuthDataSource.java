package com.github.chojmi.inspirations.domain.repository;

import com.github.scribejava.core.model.OAuth1AccessToken;

import io.reactivex.Flowable;

public interface AuthDataSource {
    Flowable<String> getAuthorizationUrl();

    Flowable<OAuth1AccessToken> getAccessToken(String oauthVerifier);
}
