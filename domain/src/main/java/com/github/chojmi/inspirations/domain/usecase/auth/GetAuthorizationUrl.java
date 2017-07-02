package com.github.chojmi.inspirations.domain.usecase.auth;

import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.model.SubmitUiModel;
import com.github.chojmi.inspirations.domain.model.UseCase;
import com.github.chojmi.inspirations.domain.model.UseCaseProcessor;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

public class GetAuthorizationUrl implements UseCase<Void, String> {

    private final UseCaseProcessor<Void, String> processor;

    @Inject
    public GetAuthorizationUrl(@NonNull AuthDataSource authDataSource, @NonNull ThreadExecutor threadExecutor,
                               @NonNull PostExecutionThread postExecutionThread) {
        this.processor = new UseCaseProcessor<Void, String>(threadExecutor, postExecutionThread) {
            @Override
            public Flowable<String> getUseCaseActionFlowable(Void aVoid) {
                return authDataSource.getAuthorizationUrl();
            }
        };
    }

    public Flowable<SubmitUiModel<String>> process() {
        return process(null);
    }

    @Override
    public Flowable<SubmitUiModel<String>> process(Void aVoid) {
        return processor.process(aVoid);
    }
}
