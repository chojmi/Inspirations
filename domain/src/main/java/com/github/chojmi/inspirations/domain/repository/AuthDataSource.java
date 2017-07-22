package com.github.chojmi.inspirations.domain.repository;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;

public interface AuthDataSource {
    Observable<OAuth1RequestToken> getRequestToken();

    Observable<String> getAuthorizationUrl();

    Observable<String> getAuthorizationUrl(OAuth1RequestToken requestToken);

    Observable<OAuth1AccessToken> getAccessToken(OAuth1RequestToken requestToken, String oauthVerifier);

    Observable<OAuth1AccessToken> getAccessToken(String oauthVerifier);

    void saveRequestToken(@Nullable OAuth1RequestToken requestToken);

    void saveAccessToken(@Nullable OAuth1AccessToken accessToken);
}
