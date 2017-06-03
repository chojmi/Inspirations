package com.github.chojmi.inspirations.domain.usecase.auth;

import com.github.chojmi.inspirations.domain.entity.auth.FrobEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.chojmi.inspirations.domain.usecase.blueprints.BaseSubmitEvent;
import com.github.chojmi.inspirations.domain.usecase.blueprints.BaseSubmitUiModel;
import com.github.chojmi.inspirations.domain.usecase.blueprints.UseCase;
import com.google.auto.value.AutoValue;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static dagger.internal.Preconditions.checkNotNull;

public class GetFrob extends UseCase<GetFrob.SubmitUiModel, GetFrob.SubmitEvent> {

    private final AuthDataSource authDataSource;

    @Inject
    public GetFrob(@NonNull AuthDataSource authDataSource, @NonNull ThreadExecutor threadExecutor,
                   @NonNull PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.authDataSource = checkNotNull(authDataSource);
    }

    @Override
    public Observable<SubmitUiModel> buildUseCaseObservable(Observable<SubmitEvent> inputEvents) {
        return inputEvents.flatMap(event -> authDataSource.getFrob()
                .map(GetFrob.SubmitUiModel::success)
                .onErrorReturn(GetFrob.SubmitUiModel::failure)
                .startWith(GetFrob.SubmitUiModel.inProgress()));
    }

    @AutoValue
    public static abstract class SubmitEvent extends BaseSubmitEvent {
        public static Observable<SubmitEvent> createObservable() {
            return Observable.fromCallable(SubmitEvent::create);
        }

        public static SubmitEvent create() {
            return new AutoValue_GetFrob_SubmitEvent();
        }
    }

    @AutoValue
    public static abstract class SubmitUiModel extends BaseSubmitUiModel {
        static SubmitUiModel inProgress() {
            return new AutoValue_GetFrob_SubmitUiModel(true, false, null, null);
        }

        static SubmitUiModel failure(Throwable t) {
            return new AutoValue_GetFrob_SubmitUiModel(false, false, t, null);
        }

        static SubmitUiModel success(FrobEntity frob) {
            return new AutoValue_GetFrob_SubmitUiModel(false, true, null, frob);
        }

        @Nullable
        public abstract FrobEntity getResult();
    }
}
