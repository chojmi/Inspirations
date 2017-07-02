package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.source.remote.service.OAuthService;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

import static dagger.internal.Preconditions.checkNotNull;

public class RemoteAuthDataSource implements AuthDataSource {
    private final OAuthService oAuthService;
    private OAuth1RequestToken requestToken;

    @Inject
    public RemoteAuthDataSource(@NonNull OAuthService oAuthService) {
        this.oAuthService = checkNotNull(oAuthService);
    }

    @Override
    public Flowable getAuthorizationUrl() {
        return Flowable.fromCallable(oAuthService::getRequestToken)
                .subscribeOn(Schedulers.newThread())
                .map(oAuth1RequestToken -> {
                    requestToken = oAuth1RequestToken;
                    return oAuthService.getAuthorizationUrl(oAuth1RequestToken);
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Flowable getAccessToken(String oauthVerifier) {
        OAuth1AccessToken cachedAccessToken = oAuthService.getAccessToken();
        if (cachedAccessToken != null && oauthVerifier.isEmpty()) {
            return Flowable.just(cachedAccessToken);
        }
        return Flowable.fromCallable(() -> oAuthService.getAccessToken(requestToken, oauthVerifier))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).doOnNext(accessToken -> oAuthService.setAccessToken(accessToken));
    }
}
