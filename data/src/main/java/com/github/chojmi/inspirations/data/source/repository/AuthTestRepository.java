package com.github.chojmi.inspirations.data.source.repository;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
import com.github.chojmi.inspirations.domain.repository.AuthTestDataSource;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

import static dagger.internal.Preconditions.checkNotNull;

public class AuthTestRepository implements AuthTestDataSource {

    private final AuthTestDataSource localAuthTestDataSource;

    private final AuthTestDataSource remoteAuthTestDataSource;

    @Inject
    AuthTestRepository(@Local @NonNull AuthTestDataSource localAuthTestDataSource,
                       @Remote @NonNull AuthTestDataSource remoteAuthTestDataSource) {
        this.localAuthTestDataSource = checkNotNull(localAuthTestDataSource);
        this.remoteAuthTestDataSource = checkNotNull(remoteAuthTestDataSource);
    }

    @Override
    public Flowable<UserEntity> getLoginData() {
        return Flowable.concat(localAuthTestDataSource.getLoginData(), remoteAuthTestDataSource.getLoginData())
                .firstElement().toFlowable();
    }
}