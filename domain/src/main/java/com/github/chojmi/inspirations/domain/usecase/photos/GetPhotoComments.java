package com.github.chojmi.inspirations.domain.usecase.photos;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;
import com.github.chojmi.inspirations.domain.usecase.blueprints.BaseSubmitEvent;
import com.github.chojmi.inspirations.domain.usecase.blueprints.BaseSubmitUiModel;
import com.github.chojmi.inspirations.domain.usecase.blueprints.UseCase;
import com.google.auto.value.AutoValue;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static dagger.internal.Preconditions.checkNotNull;

public class GetPhotoComments extends UseCase<GetPhotoComments.SubmitUiModel, GetPhotoComments.SubmitEvent> {
    private final PhotosDataSource photosDataSource;

    @Inject
    GetPhotoComments(PhotosDataSource PhotosDataSource, ThreadExecutor threadExecutor,
                     PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.photosDataSource = checkNotNull(PhotosDataSource);
    }

    @Override
    public Observable<SubmitUiModel> buildUseCaseObservable(Observable<SubmitEvent> inputEvents) {
        return inputEvents.flatMap(event -> photosDataSource.loadPhotoComments(event.getPhotoId())
                .map(GetPhotoComments.SubmitUiModel::success)
                .onErrorReturn(GetPhotoComments.SubmitUiModel::failure)
                .startWith(GetPhotoComments.SubmitUiModel.inProgress()));
    }

    @AutoValue
    public static abstract class SubmitEvent extends BaseSubmitEvent {
        public static Observable<SubmitEvent> createObservable(@NonNull String photoId) {
            return Observable.fromCallable(() -> create(photoId));
        }

        public static SubmitEvent create(@NonNull String photoId) {
            return new AutoValue_GetPhotoComments_SubmitEvent(checkNotNull(photoId));
        }

        abstract String getPhotoId();
    }

    @AutoValue
    public static abstract class SubmitUiModel extends BaseSubmitUiModel {
        static SubmitUiModel inProgress() {
            return new AutoValue_GetPhotoComments_SubmitUiModel(true, false, null, null);
        }

        static SubmitUiModel failure(Throwable t) {
            return new AutoValue_GetPhotoComments_SubmitUiModel(false, false, t, null);
        }

        static SubmitUiModel success(PhotoCommentsEntity photoCommentsEntity) {
            return new AutoValue_GetPhotoComments_SubmitUiModel(false, true, null, photoCommentsEntity);
        }

        @Nullable
        public abstract PhotoCommentsEntity getResult();
    }
}
