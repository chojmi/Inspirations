package com.github.chojmi.inspirations.domain.usecase.auth;

import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
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

public class GetLoginData extends UseCase<GetLoginData.SubmitUiModel, GetLoginData.SubmitEvent> {

    private final AuthDataSource authDataSource;

    @Inject
    public GetLoginData(@NonNull AuthDataSource authDataSource, @NonNull ThreadExecutor threadExecutor,
                        @NonNull PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.authDataSource = checkNotNull(authDataSource);
    }

    @Override
    public Observable<SubmitUiModel> buildUseCaseObservable(Observable<SubmitEvent> inputEvents) {
        return inputEvents.flatMap(event -> authDataSource.getLoginData()
                .map(GetLoginData.SubmitUiModel::success)
                .onErrorReturn(GetLoginData.SubmitUiModel::failure)
                .startWith(GetLoginData.SubmitUiModel.inProgress()));
    }

    @AutoValue
    public static abstract class SubmitEvent extends BaseSubmitEvent {
        public static Observable<SubmitEvent> createObservable() {
            return Observable.fromCallable(SubmitEvent::create);
        }

        public static SubmitEvent create() {
            return new AutoValue_GetLoginData_SubmitEvent();
        }
    }

    @AutoValue
    public static abstract class SubmitUiModel extends BaseSubmitUiModel {
        static SubmitUiModel inProgress() {
            return new AutoValue_GetLoginData_SubmitUiModel(true, false, null, null);
        }

        static SubmitUiModel failure(Throwable t) {
            return new AutoValue_GetLoginData_SubmitUiModel(false, false, t, null);
        }

        static SubmitUiModel success(UserEntity userEntity) {
            return new AutoValue_GetLoginData_SubmitUiModel(false, true, null, userEntity);
        }

        @Nullable
        public abstract UserEntity getResult();
    }
}