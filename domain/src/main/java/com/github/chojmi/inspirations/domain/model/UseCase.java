package com.github.chojmi.inspirations.domain.model;

import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

import static dagger.internal.Preconditions.checkNotNull;

public abstract class UseCase<Input, Output> {

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    public UseCase(@NonNull ThreadExecutor threadExecutor,
                   @NonNull PostExecutionThread postExecutionThread) {
        this.threadExecutor = checkNotNull(threadExecutor);
        this.postExecutionThread = checkNotNull(postExecutionThread);
    }

    protected Flowable<SubmitUiModel<Output>> invokeRequest() {
        return repositoryFlowable()
                .subscribeOn(Schedulers.from(threadExecutor))
                .map(SubmitUiModel::success)
                .startWith(SubmitUiModel.inProgress())
                .onErrorReturn(SubmitUiModel::fail)
                .observeOn(postExecutionThread.getScheduler());
    }

    public abstract Flowable<SubmitUiModel<Output>> processRequest(Input input);

    public abstract Flowable<Output> repositoryFlowable();
}
