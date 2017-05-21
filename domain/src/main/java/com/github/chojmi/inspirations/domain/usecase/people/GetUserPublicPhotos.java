package com.github.chojmi.inspirations.domain.usecase.people;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;
import com.github.chojmi.inspirations.domain.usecase.blueprints.BaseSubmitEvent;
import com.github.chojmi.inspirations.domain.usecase.blueprints.BaseSubmitUiModel;
import com.github.chojmi.inspirations.domain.usecase.blueprints.UseCase;
import com.google.auto.value.AutoValue;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static dagger.internal.Preconditions.checkNotNull;

public class GetUserPublicPhotos extends UseCase<GetUserPublicPhotos.SubmitUiModel, GetUserPublicPhotos.SubmitEvent> {

    private final PeopleDataSource peopleDataSource;

    @Inject
    GetUserPublicPhotos(@NonNull PeopleDataSource peopleDataSource, @NonNull ThreadExecutor threadExecutor,
                        @NonNull PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.peopleDataSource = checkNotNull(peopleDataSource);
    }

    @Override
    public Observable<SubmitUiModel> buildUseCaseObservable(Observable<SubmitEvent> inputEvents) {
        return inputEvents.flatMap(event -> peopleDataSource.loadUserPublicPhotos(event.getUserId())
                        .map(SubmitUiModel::success)
                        .onErrorReturn(SubmitUiModel::failure)
                        .startWith(SubmitUiModel.inProgress()));
    }

    @AutoValue
    public static abstract class SubmitEvent extends BaseSubmitEvent {

        public static Observable<SubmitEvent> createObservable(@NonNull String userId) {
            return Observable.fromCallable(() -> create(userId));
        }

        public static SubmitEvent create(@NonNull String userId) {
            return new AutoValue_GetUserPublicPhotos_SubmitEvent(checkNotNull(userId));
        }

        abstract String getUserId();
    }

    @AutoValue
    public static abstract class SubmitUiModel extends BaseSubmitUiModel {
        static SubmitUiModel inProgress() {
            return new AutoValue_GetUserPublicPhotos_SubmitUiModel(true, false, null, null);
        }

        static SubmitUiModel failure(Throwable t) {
            return new AutoValue_GetUserPublicPhotos_SubmitUiModel(false, false, t, null);
        }

        static SubmitUiModel success(List<PhotoEntity> photoEntities) {
            return new AutoValue_GetUserPublicPhotos_SubmitUiModel(false, true, null, photoEntities);
        }

        @Nullable
        public abstract List<PhotoEntity> getResult();
    }
}