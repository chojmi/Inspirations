package com.github.chojmi.inspirations.data.source.local;

import com.github.scribejava.core.model.OAuth1AccessToken;

public interface AccessTokenHolder {
    OAuth1AccessToken getAccessToken();

    boolean containsAccessToken();
}
