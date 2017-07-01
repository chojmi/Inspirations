package com.github.chojmi.inspirations.domain.usecase.people;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;
import com.github.chojmi.inspirations.domain.usecase.blueprints.BaseSubmitEvent;
import com.github.chojmi.inspirations.domain.usecase.blueprints.BaseSubmitUiModel;
import com.github.chojmi.inspirations.domain.usecase.blueprints.UseCase;
import com.google.auto.value.AutoValue;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

public class GetUserInfo extends UseCase<GetUserInfo.SubmitUiModel, GetUserInfo.SubmitEvent> {

    private final PeopleDataSource peopleDataSource;

    @Inject
    GetUserInfo(PeopleDataSource peopleDataSource, ThreadExecutor threadExecutor,
                PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.peopleDataSource = checkNotNull(peopleDataSource);
    }

    @Override
    public Observable<SubmitUiModel> buildUseCaseObservable(Observable<SubmitEvent> inputEvents) {
        return inputEvents.flatMap(event -> peopleDataSource.loadPersonInfo(event.getUserId())
                .map(GetUserInfo.SubmitUiModel::success)
                .onErrorReturn(GetUserInfo.SubmitUiModel::failure)
                .startWith(GetUserInfo.SubmitUiModel.inProgress()));
    }

    @AutoValue
    public static abstract class SubmitEvent extends BaseSubmitEvent {
        public static Observable<SubmitEvent> createObservable(@NonNull String userId) {
            return Observable.fromCallable(() -> create(userId));
        }

        public static SubmitEvent create(@NonNull String userId) {
            return new AutoValue_GetUserInfo_SubmitEvent(checkNotNull(userId));
        }

        abstract String getUserId();
    }

    @AutoValue
    public static abstract class SubmitUiModel extends BaseSubmitUiModel {
        static SubmitUiModel inProgress() {
            return new AutoValue_GetUserInfo_SubmitUiModel(true, false, null, null);
        }

        static SubmitUiModel failure(Throwable t) {
            return new AutoValue_GetUserInfo_SubmitUiModel(false, false, t, null);
        }

        static SubmitUiModel success(PersonEntity personEntity) {
            return new AutoValue_GetUserInfo_SubmitUiModel(false, true, null, personEntity);
        }

        @Nullable
        public abstract PersonEntity getResult();
    }
}
