package com.github.chojmi.inspirations.data.source.local;

import com.github.chojmi.inspirations.domain.entity.auth.FrobEntity;
import com.github.chojmi.inspirations.domain.entity.auth.TokenEntity;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LocalAuthDataSource implements AuthDataSource {
    private TokenEntity tokenEntity;

    @Inject
    public LocalAuthDataSource() {
    }

    @Override
    public Observable<FrobEntity> getFrob() {
        return Observable.empty();
    }

    @Override
    public Observable<TokenEntity> getToken(String frob) {
        return tokenEntity != null ? Observable.just(tokenEntity) : Observable.empty();
    }

    @Override
    public void onNewTokenEntity(TokenEntity tokenEntity) {
        this.tokenEntity = tokenEntity;
    }
}
