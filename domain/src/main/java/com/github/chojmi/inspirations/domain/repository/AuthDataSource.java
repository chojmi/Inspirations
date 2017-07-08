package com.github.chojmi.inspirations.domain.repository;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import io.reactivex.Flowable;
import io.reactivex.annotations.Nullable;

public interface AuthDataSource {
    Flowable<OAuth1RequestToken> getRequestToken();

    Flowable<String> getAuthorizationUrl();

    Flowable<String> getAuthorizationUrl(OAuth1RequestToken requestToken);

    Flowable<OAuth1AccessToken> getAccessToken(OAuth1RequestToken requestToken, String oauthVerifier);

    Flowable<OAuth1AccessToken> getAccessToken(String oauthVerifier);

    void saveRequestToken(@Nullable OAuth1RequestToken requestToken);

    void saveAccessToken(@Nullable OAuth1AccessToken accessToken);
}
