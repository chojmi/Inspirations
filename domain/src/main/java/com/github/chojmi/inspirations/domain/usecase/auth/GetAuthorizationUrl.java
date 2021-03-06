package com.github.chojmi.inspirations.domain.usecase.auth;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.common.UseCaseProcessor;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class GetAuthorizationUrl implements UseCase<Void, String> {

    private final UseCaseProcessor<Void, String> processor;

    @Inject
    public GetAuthorizationUrl(@NonNull AuthDataSource authDataSource, @NonNull ThreadExecutor threadExecutor,
                               @NonNull PostExecutionThread postExecutionThread) {
        this.processor = new UseCaseProcessor<Void, String>(threadExecutor, postExecutionThread) {
            @Override
            public Observable<String> getUseCaseActionObservable(Void aVoid) {
                return authDataSource.getAuthorizationUrl();
            }
        };
    }

    public Observable<SubmitUiModel<String>> process() {
        return process(null);
    }

    @Override
    public Observable<SubmitUiModel<String>> process(Void aVoid) {
        return processor.process(aVoid);
    }
}
