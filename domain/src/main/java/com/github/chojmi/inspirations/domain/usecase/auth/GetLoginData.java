package com.github.chojmi.inspirations.domain.usecase.auth;


import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.model.SubmitUiModel;
import com.github.chojmi.inspirations.domain.model.UseCase;
import com.github.chojmi.inspirations.domain.repository.AuthTestDataSource;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

import static dagger.internal.Preconditions.checkNotNull;

public class GetLoginData extends UseCase<Void, UserEntity> {

    private final AuthTestDataSource authTestDataSource;

    @Inject
    public GetLoginData(@NonNull AuthTestDataSource authTestDataSource, @NonNull ThreadExecutor threadExecutor,
                        @NonNull PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.authTestDataSource = checkNotNull(authTestDataSource);
    }

    @Override
    public Flowable<SubmitUiModel<UserEntity>> processRequest(Void aVoid) {
        return invokeRequest();
    }

    @Override
    public Flowable<UserEntity> repositoryFlowable() {
        return authTestDataSource.getLoginData();
    }
}