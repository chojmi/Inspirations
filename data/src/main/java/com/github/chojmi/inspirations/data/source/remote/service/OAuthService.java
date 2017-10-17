package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface OAuthService {
    OAuth1RequestToken getRequestToken() throws IOException, InterruptedException, ExecutionException;

    String getAuthorizationUrl(OAuth1RequestToken requestToken);

    OAuth1AccessToken getAccessToken(OAuth1RequestToken requestToken, String oauthVerifier) throws IOException, InterruptedException, ExecutionException;

    boolean containsAccessToken();

    String signGetRequest(String url);

    String signPostRequest(String url);
}
