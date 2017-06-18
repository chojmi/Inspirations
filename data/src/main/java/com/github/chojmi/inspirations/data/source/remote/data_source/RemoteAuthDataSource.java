package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

import static dagger.internal.Preconditions.checkNotNull;

public class RemoteAuthDataSource implements AuthDataSource {
    private OAuth10aService oAuthService;
    private OAuth1RequestToken requestToken;
    private OAuth1AccessToken accessToken;

    @Inject
    public RemoteAuthDataSource(@NonNull OAuth10aService oAuthService) {
        this.oAuthService = checkNotNull(oAuthService);
    }

    @Override
    public Observable getAuthorizationUrl() {
        return Observable.fromCallable(() -> oAuthService.getRequestToken())
                .subscribeOn(Schedulers.newThread())
                .map(oAuth1RequestToken -> {
                    requestToken = oAuth1RequestToken;
                    return oAuthService.getAuthorizationUrl(oAuth1RequestToken);
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable getAccessToken(String oauthVerifier) {
        if (accessToken != null && oauthVerifier.isEmpty()) {
            return Observable.just(accessToken);
        }
        return Observable.fromCallable(() -> oAuthService.getAccessToken(requestToken, oauthVerifier))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).doOnNext(oAuth1AccessToken -> accessToken = oAuth1AccessToken);
    }
}
