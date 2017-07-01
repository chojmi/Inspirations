package com.github.chojmi.inspirations.domain.usecase.auth;

import com.github.chojmi.inspirations.domain.entity.people.UserEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.AuthTestDataSource;
import com.github.chojmi.inspirations.domain.usecase.blueprints.BaseSubmitEvent;
import com.github.chojmi.inspirations.domain.usecase.blueprints.BaseSubmitUiModel;
import com.github.chojmi.inspirations.domain.usecase.blueprints.UseCase;
import com.google.auto.value.AutoValue;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class GetLoginData extends UseCase<GetLoginData.SubmitUiModel, GetLoginData.SubmitEvent> {

    private final AuthTestDataSource authTestDataSource;

    @Inject
    public GetLoginData(@NonNull AuthTestDataSource authTestDataSource, @NonNull ThreadExecutor threadExecutor,
                        @NonNull PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.authTestDataSource = checkNotNull(authTestDataSource);
    }

    @Override
    public Observable<SubmitUiModel> buildUseCaseObservable(Observable<SubmitEvent> inputEvents) {
        return inputEvents.flatMap(event -> authTestDataSource.getLoginData()
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