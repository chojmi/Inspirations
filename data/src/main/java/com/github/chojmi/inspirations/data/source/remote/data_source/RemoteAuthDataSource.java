package com.github.chojmi.inspirations.data.source.remote.data_source;

import com.github.chojmi.inspirations.data.source.remote.service.AuthService;
import com.github.chojmi.inspirations.data.source.remote.service.OAuthServiceWrapper;
import com.github.chojmi.inspirations.data.source.remote.service.RemoteQueryProducer;
import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

import static dagger.internal.Preconditions.checkNotNull;

public class RemoteAuthDataSource implements AuthDataSource {
    private final OAuthServiceWrapper oAuthService;
    private final AuthService authService;
    private final RemoteQueryProducer remoteQueryProducer;
    private OAuth1RequestToken requestToken;

    @Inject
    public RemoteAuthDataSource(@NonNull AuthService authService,
                                @NonNull RemoteQueryProducer remoteQueryProducer,
                                @NonNull OAuthServiceWrapper oAuthService) {
        this.authService = checkNotNull(authService);
        this.remoteQueryProducer = checkNotNull(remoteQueryProducer);
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

    @Override
    public Observable<UserEntity> getLoginData() {
        return authService.loadLoginData(remoteQueryProducer.produceLoadLoginData())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(loginDataEntity -> (UserEntity) loginDataEntity.getUser());
    }
}
