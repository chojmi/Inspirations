package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class FakeOAuthService implements OAuthService {
    @Override
    public OAuth1RequestToken getRequestToken() throws IOException, InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
        return null;
    }

    @Override
    public OAuth1AccessToken getAccessToken(OAuth1RequestToken requestToken, String oauthVerifier) throws IOException, InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public boolean containsAccessToken() {
        return false;
    }

    @Override
    public String signGetRequest(String url) {
        return null;
    }

    @Override
    public String signPostRequest(String url) {
        return null;
    }
}
