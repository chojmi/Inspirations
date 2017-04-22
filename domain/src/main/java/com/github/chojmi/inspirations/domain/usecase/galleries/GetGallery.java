package com.github.chojmi.inspirations.domain.usecase.galleries;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;
import com.github.chojmi.inspirations.domain.usecase.BaseSubmitEvent;
import com.github.chojmi.inspirations.domain.usecase.BaseSubmitUiModel;
import com.github.chojmi.inspirations.domain.usecase.UseCase;
import com.google.auto.value.AutoValue;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static dagger.internal.Preconditions.checkNotNull;

public class GetGallery extends UseCase<GetGallery.SubmitUiModel, GetGallery.SubmitEvent> {

    private final GalleriesDataSource galleriesDataSource;

    @Inject
    public GetGallery(@NonNull GalleriesDataSource galleriesDataSource, @NonNull ThreadExecutor threadExecutor,
                      @NonNull PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.galleriesDataSource = checkNotNull(galleriesDataSource);
    }

    @Override
    public Observable<SubmitUiModel> buildUseCaseObservable(Observable<SubmitEvent> inputEvents) {
        return inputEvents.flatMap(event -> galleriesDataSource.loadGallery(event.getGalleryId())
                .map(GetGallery.SubmitUiModel::success)
                .onErrorReturn(GetGallery.SubmitUiModel::failure)
                .startWith(GetGallery.SubmitUiModel.inProgress()));
    }

    @AutoValue
    public static abstract class SubmitEvent extends BaseSubmitEvent {
        public static SubmitEvent create(@NonNull String galleryId) {
            return new AutoValue_GetGallery_SubmitEvent(checkNotNull(galleryId));
        }

        abstract String getGalleryId();
    }

    @AutoValue
    public static abstract class SubmitUiModel extends BaseSubmitUiModel {
        static SubmitUiModel inProgress() {
            return new AutoValue_GetGallery_SubmitUiModel(true, false, null, null);
        }

        static SubmitUiModel failure(Throwable t) {
            return new AutoValue_GetGallery_SubmitUiModel(false, false, t, null);
        }

        static SubmitUiModel success(List<PhotoEntity> photoEntities) {
            return new AutoValue_GetGallery_SubmitUiModel(false, true, null, photoEntities);
        }

        @Nullable
        public abstract List<PhotoEntity> getResult();
    }
}