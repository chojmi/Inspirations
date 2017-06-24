package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static dagger.internal.Preconditions.checkNotNull;

public class OAuthServiceWrapper {
    private final OAuth10aService oAuthService;
    private OAuth1AccessToken accessToken;

    public OAuthServiceWrapper(@NonNull OAuth10aService oAuthService) {
        this.oAuthService = checkNotNull(oAuthService);
    }

    public final OAuth1RequestToken getRequestToken() throws IOException, InterruptedException, ExecutionException {
        return oAuthService.getRequestToken();
    }

    public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
        return oAuthService.getAuthorizationUrl(requestToken);
    }

    public final OAuth1AccessToken getAccessToken(OAuth1RequestToken requestToken, String oauthVerifier)
            throws IOException, InterruptedException, ExecutionException {
        return oAuthService.getAccessToken(requestToken, oauthVerifier);
    }

    @Nullable
    public OAuth1AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(@Nullable OAuth1AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public boolean containsAccessToken() {
        return accessToken != null;
    }

    public String signRequest(String url) {
        final StringBuilder builder = new StringBuilder(url);
        OAuthRequest request = new OAuthRequest(Verb.GET, url);
        oAuthService.signRequest(accessToken, request);
        request.getOauthParameters().forEach((s, s2) -> builder.append("&").append(s).append("=").append(s2));
        return builder.toString();
    }
}
