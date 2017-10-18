package com.github.chojmi.inspirations.domain.common;

import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;

import io.reactivex.Observable;
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
    public Observable<SubmitUiModel<Output>> process(Input input) {
        return getUseCaseActionObservable(input)
                .retryWhen(errors -> errors.zipWith(Observable.range(1, 3), (n, i) -> i))
                .subscribeOn(Schedulers.from(threadExecutor))
                .map(SubmitUiModel::success)
                .startWith(SubmitUiModel.inProgress())
                .onErrorReturn(SubmitUiModel::fail)
                .observeOn(postExecutionThread.getScheduler());
    }

    public abstract Observable<Output> getUseCaseActionObservable(Input input);
}
