package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.inspirations.data.source.local.AccessTokenHolder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import io.reactivex.annotations.NonNull;

import static dagger.internal.Preconditions.checkNotNull;

public class OAuthServiceImpl implements OAuthService {
    private final OAuth10aService oAuthService;
    private final AccessTokenHolder accessTokenHolder;

    public OAuthServiceImpl(@NonNull OAuth10aService oAuthService, @NonNull AccessTokenHolder accessTokenHolder) {
        this.oAuthService = checkNotNull(oAuthService);
        this.accessTokenHolder = checkNotNull(accessTokenHolder);
    }

    @Override
    public final OAuth1RequestToken getRequestToken() throws IOException, InterruptedException, ExecutionException {
        return oAuthService.getRequestToken();
    }

    @Override
    public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
        return oAuthService.getAuthorizationUrl(requestToken);
    }

    @Override
    public final OAuth1AccessToken getAccessToken(OAuth1RequestToken requestToken, String oauthVerifier)
            throws IOException, InterruptedException, ExecutionException {
        return oAuthService.getAccessToken(requestToken, oauthVerifier);
    }

    @Override
    public boolean containsAccessToken() {
        return accessTokenHolder.containsAccessToken();
    }

    @Override
    public String signRequest(String url) {
        final StringBuilder builder = new StringBuilder(url);
        OAuthRequest request = new OAuthRequest(Verb.GET, url);
        oAuthService.signRequest(accessTokenHolder.getAccessToken(), request);
        request.getOauthParameters().forEach((s, s2) -> builder.append("&").append(s).append("=").append(s2));
        return builder.toString();
    }
}
