package com.github.chojmi.inspirations.domain.usecase.auth;

import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.AuthDataSource;
import com.github.chojmi.inspirations.domain.usecase.blueprints.BaseSubmitEvent;
import com.github.chojmi.inspirations.domain.usecase.blueprints.BaseSubmitUiModel;
import com.github.chojmi.inspirations.domain.usecase.blueprints.UseCase;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.google.auto.value.AutoValue;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static dagger.internal.Preconditions.checkNotNull;

public class GetAccessToken extends UseCase<GetAccessToken.SubmitUiModel, GetAccessToken.SubmitEvent> {

    private final AuthDataSource authDataSource;

    @Inject
    public GetAccessToken(@NonNull AuthDataSource authDataSource, @NonNull ThreadExecutor threadExecutor,
                          @NonNull PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.authDataSource = checkNotNull(authDataSource);
    }

    @Override
    public Observable<SubmitUiModel> buildUseCaseObservable(Observable<SubmitEvent> inputEvents) {
        return inputEvents.flatMap(event -> authDataSource.getAccessToken(event.getOauthVerifier())
                .map(GetAccessToken.SubmitUiModel::success)
                .onErrorReturn(GetAccessToken.SubmitUiModel::failure)
                .startWith(GetAccessToken.SubmitUiModel.inProgress()));
    }

    @AutoValue
    public static abstract class SubmitEvent extends BaseSubmitEvent {
        public static Observable<SubmitEvent> createObservable() {
            return Observable.fromCallable(() -> create(""));
        }

        public static Observable<SubmitEvent> createObservable(String oauthVerifier) {
            return Observable.fromCallable(() -> create(oauthVerifier));
        }

        public static SubmitEvent create(String oauthVerifier) {
            return new AutoValue_GetAccessToken_SubmitEvent(oauthVerifier);
        }

        abstract String getOauthVerifier();
    }

    @AutoValue
    public static abstract class SubmitUiModel extends BaseSubmitUiModel {
        static SubmitUiModel inProgress() {
            return new AutoValue_GetAccessToken_SubmitUiModel(true, false, null, null);
        }

        static SubmitUiModel failure(Throwable t) {
            return new AutoValue_GetAccessToken_SubmitUiModel(false, false, t, null);
        }

        static SubmitUiModel success(OAuth1AccessToken accessToken) {
            return new AutoValue_GetAccessToken_SubmitUiModel(false, true, null, accessToken);
        }

        @Nullable
        public abstract OAuth1AccessToken getResult();
    }
}
