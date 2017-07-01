package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.source.remote.service.OAuthService;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class RemoteAuthDataSource implements AuthDataSource {
    private final OAuthService oAuthService;
    private OAuth1RequestToken requestToken;

    @Inject
    public RemoteAuthDataSource(@NonNull OAuthService oAuthService) {
        this.oAuthService = checkNotNull(oAuthService);
    }

    @Override
    public Observable getAuthorizationUrl() {
        return Observable.fromCallable(oAuthService::getRequestToken)
                .subscribeOn(Schedulers.newThread())
                .map(oAuth1RequestToken -> {
                    requestToken = oAuth1RequestToken;
                    return oAuthService.getAuthorizationUrl(oAuth1RequestToken);
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable getAccessToken(String oauthVerifier) {
        OAuth1AccessToken cachedAccessToken = oAuthService.getAccessToken();
        if (cachedAccessToken != null && oauthVerifier.isEmpty()) {
            return Observable.just(cachedAccessToken);
        }
        return Observable.fromCallable(() -> oAuthService.getAccessToken(requestToken, oauthVerifier))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).doOnNext(accessToken -> oAuthService.setAccessToken(accessToken));
    }
}
