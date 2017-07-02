package com.github.chojmi.inspirations.domain.model;

import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

import static dagger.internal.Preconditions.checkNotNull;

public abstract class UseCaseProcessor<Input, Output> implements UseCase<Input, Output> {

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    public UseCaseProcessor(@NonNull ThreadExecutor threadExecutor,
                            @NonNull PostExecutionThread postExecutionThread) {
        this.threadExecutor = checkNotNull(threadExecutor);
        this.postExecutionThread = checkNotNull(postExecutionThread);
    }

    @Override
    public Flowable<SubmitUiModel<Output>> process(Input input) {
        return getUseCaseActionFlowable(input)
                .subscribeOn(Schedulers.from(threadExecutor))
                .map(SubmitUiModel::success)
                .startWith(SubmitUiModel.inProgress())
                .onErrorReturn(SubmitUiModel::fail)
                .observeOn(postExecutionThread.getScheduler());
    }

    public abstract Flowable<Output> getUseCaseActionFlowable(Input input);
}
