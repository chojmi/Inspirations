package com.github.chojmi.inspirations.domain.repository;

import com.github.chojmi.inspirations.domain.entity.auth.FrobEntity;
import com.github.chojmi.inspirations.domain.entity.auth.TokenEntity;

import io.reactivex.Observable;

public interface AuthDataSource {
    Observable<FrobEntity> getFrob();

    Observable<TokenEntity> getToken(String frob);

    void onNewTokenEntity(TokenEntity tokenEntity);
}
