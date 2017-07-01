package com.github.chojmi.inspirations.domain.usecase.auth;

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

public class GetAuthorizationUrl extends UseCase<GetAuthorizationUrl.SubmitUiModel, GetAuthorizationUrl.SubmitEvent> {

    private final AuthDataSource authDataSource;

    @Inject
    public GetAuthorizationUrl(@NonNull AuthDataSource authDataSource, @NonNull ThreadExecutor threadExecutor,
                               @NonNull PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.authDataSource = checkNotNull(authDataSource);
    }

    @Override
    public Observable<SubmitUiModel> buildUseCaseObservable(Observable<SubmitEvent> inputEvents) {
        return inputEvents.flatMap(event -> authDataSource.getAuthorizationUrl()
                .map(GetAuthorizationUrl.SubmitUiModel::success)
                .onErrorReturn(GetAuthorizationUrl.SubmitUiModel::failure)
                .startWith(GetAuthorizationUrl.SubmitUiModel.inProgress()));
    }

    @AutoValue
    public static abstract class SubmitEvent extends BaseSubmitEvent {
        public static Observable<SubmitEvent> createObservable() {
            return Observable.fromCallable(SubmitEvent::create);
        }

        public static SubmitEvent create() {
            return new AutoValue_GetAuthorizationUrl_SubmitEvent();
        }
    }

    @AutoValue
    public static abstract class SubmitUiModel extends BaseSubmitUiModel {
        static SubmitUiModel inProgress() {
            return new AutoValue_GetAuthorizationUrl_SubmitUiModel(true, false, null, null);
        }

        static SubmitUiModel failure(Throwable t) {
            return new AutoValue_GetAuthorizationUrl_SubmitUiModel(false, false, t, null);
        }

        static SubmitUiModel success(String authorizationUrl) {
            return new AutoValue_GetAuthorizationUrl_SubmitUiModel(false, true, null, authorizationUrl);
        }

        @Nullable
        public abstract String getResult();
    }
}
