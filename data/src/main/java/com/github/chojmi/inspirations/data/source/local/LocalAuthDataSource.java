package com.github.chojmi.inspirations.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;

@Singleton
public class LocalAuthDataSource implements AuthDataSource, AccessTokenHolder {
    private static final String NAME_SHARED_PREF = LocalAuthDataSource.class.getName();
    private static final String ARG_TOKEN = "com.github.chojmi.inspirations.data.source.local.LocalAuthDataSource.TOKEN";
    private static final String ARG_TOKEN_SECRET = "com.github.chojmi.inspirations.data.source.local.LocalAuthDataSource.TOKEN_SECRET";
    private final SharedPreferences sharedPreferences;
    private OAuth1RequestToken requestToken;
    private OAuth1AccessToken accessToken;

    @Inject
    public LocalAuthDataSource(Context context) {
        sharedPreferences = context.getSharedPreferences(NAME_SHARED_PREF, Context.MODE_PRIVATE);
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
        if (accessToken != null) {
            sharedPreferences.edit().putString(ARG_TOKEN, accessToken.getToken()).putString(ARG_TOKEN_SECRET, accessToken.getTokenSecret()).apply();
        }
    }

    @Override
    public OAuth1AccessToken getAccessToken() {
        if (accessToken == null && containsAccessTokenInCache()) {
            accessToken = new OAuth1AccessToken(sharedPreferences.getString(ARG_TOKEN, ""),
                    sharedPreferences.getString(ARG_TOKEN_SECRET, ""));
        }
        return accessToken;
    }

    @Override
    public boolean containsAccessToken() {
        return accessToken != null || containsAccessTokenInCache();
    }

    private boolean containsAccessTokenInCache() {
        return sharedPreferences.contains(ARG_TOKEN) && sharedPreferences.contains(ARG_TOKEN_SECRET);
    }
}
