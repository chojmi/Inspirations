package com.github.chojmi.inspirations.domain.usecase.auth;

import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.model.SubmitUiModel;
import com.github.chojmi.inspirations.domain.model.UseCase;
import com.github.chojmi.inspirations.domain.model.UseCaseProcessor;
import com.github.chojmi.inspirations.domain.repository.AuthTestDataSource;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

public class GetLoginData implements UseCase<Void, UserEntity> {

    private final UseCaseProcessor<Void, UserEntity> processor;

    @Inject
    public GetLoginData(@NonNull AuthTestDataSource authTestDataSource, @NonNull ThreadExecutor threadExecutor,
                        @NonNull PostExecutionThread postExecutionThread) {
        this.processor = new UseCaseProcessor<Void, UserEntity>(threadExecutor, postExecutionThread) {
            @Override
            public Flowable<UserEntity> getUseCaseActionFlowable(Void aVoid) {
                return authTestDataSource.getLoginData();
            }
        };
    }

    public Flowable<SubmitUiModel<UserEntity>> process() {
        return process(null);
    }

    @Override
    public Flowable<SubmitUiModel<UserEntity>> process(Void aVoid) {
        return processor.process(aVoid);
    }
}