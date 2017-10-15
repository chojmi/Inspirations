package com.github.chojmi.inspirations.domain.usecase.auth;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.common.UseCaseProcessor;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.AuthTestDataSource;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class GetLoginData implements UseCase<Void, PersonEntity> {

    private final UseCaseProcessor<Void, PersonEntity> processor;

    @Inject
    public GetLoginData(@NonNull AuthTestDataSource authTestDataSource, @NonNull ThreadExecutor threadExecutor,
                        @NonNull PostExecutionThread postExecutionThread) {
        this.processor = new UseCaseProcessor<Void, PersonEntity>(threadExecutor, postExecutionThread) {
            @Override
            public Observable<PersonEntity> getUseCaseActionObservable(Void aVoid) {
                return authTestDataSource.getLoginData();
            }
        };
    }

    public Observable<SubmitUiModel<PersonEntity>> process() {
        return process(null);
    }

    @Override
    public Observable<SubmitUiModel<PersonEntity>> process(Void aVoid) {
        return processor.process(aVoid);
    }
}