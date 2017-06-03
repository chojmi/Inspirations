package com.github.chojmi.inspirations.data.source.repository;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.domain.entity.auth.FrobEntity;
import com.github.chojmi.inspirations.domain.entity.auth.TokenEntity;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

import static dagger.internal.Preconditions.checkNotNull;

public class AuthRepository implements AuthDataSource {
    private final AuthDataSource authRemoteDataSource;

    private final AuthDataSource authLocalDataSource;

    @Inject
    AuthRepository(@Remote @NonNull AuthDataSource authRemoteDataSource,
                   @Local @NonNull AuthDataSource authLocalDataSource) {
        this.authRemoteDataSource = checkNotNull(authRemoteDataSource);
        this.authLocalDataSource = checkNotNull(authLocalDataSource);
    }

    @Override
    public Observable<FrobEntity> getFrob() {
        return Observable.concat(authLocalDataSource.getFrob(),
                authRemoteDataSource.getFrob()).firstElement().toObservable();
    }

    @Override
    public Observable<TokenEntity> getToken(String frob) {
        if (frob.isEmpty()) {
            return authLocalDataSource.getToken(frob);
        }
        return Observable.concat(authLocalDataSource.getToken(frob),
                authRemoteDataSource.getToken(frob))
                .firstElement()
                .doOnSuccess(authLocalDataSource::onNewTokenEntity).toObservable();
    }

    @Override
    public void onNewTokenEntity(TokenEntity tokenEntity) {

    }
}
