package com.github.chojmi.inspirations.domain.usecase.auth;

import com.github.chojmi.inspirations.domain.entity.auth.TokenEntity;
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

public class GetToken extends UseCase<GetToken.SubmitUiModel, GetToken.SubmitEvent> {

    private final AuthDataSource authDataSource;

    @Inject
    public GetToken(@NonNull AuthDataSource authDataSource, @NonNull ThreadExecutor threadExecutor,
                    @NonNull PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.authDataSource = checkNotNull(authDataSource);
    }

    @Override
    public Observable<SubmitUiModel> buildUseCaseObservable(Observable<SubmitEvent> inputEvents) {
        return inputEvents.flatMap(event -> authDataSource.getToken(event.getFrob()))
                .map(GetToken.SubmitUiModel::success)
                .onErrorReturn(GetToken.SubmitUiModel::failure)
                .startWith(GetToken.SubmitUiModel.inProgress());
    }

    @AutoValue
    public static abstract class SubmitEvent extends BaseSubmitEvent {
        public static Observable<SubmitEvent> createObservable(@NonNull String frob) {
            return Observable.fromCallable(() -> create(frob));
        }

        public static Observable<SubmitEvent> createCacheObservable() {
            return Observable.fromCallable(() -> create(""));
        }

        public static SubmitEvent create(String frob) {
            return new AutoValue_GetToken_SubmitEvent(frob);
        }

        abstract String getFrob();
    }

    @AutoValue
    public static abstract class SubmitUiModel extends BaseSubmitUiModel {
        static SubmitUiModel inProgress() {
            return new AutoValue_GetToken_SubmitUiModel(true, false, null, null);
        }

        static SubmitUiModel failure(Throwable t) {
            return new AutoValue_GetToken_SubmitUiModel(false, false, t, null);
        }

        static SubmitUiModel success(TokenEntity frob) {
            return new AutoValue_GetToken_SubmitUiModel(false, true, null, frob);
        }

        @Nullable
        public abstract TokenEntity getResult();
    }
}
