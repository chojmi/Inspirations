package com.github.chojmi.inspirations.domain.usecase.photos;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.common.UseCaseProcessor;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoCommentsEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetPhotoComments implements UseCase<String, PhotoCommentsEntity> {
    private final UseCaseProcessor<String, PhotoCommentsEntity> processor;

    @Inject
    GetPhotoComments(PhotosDataSource photosDataSource, ThreadExecutor threadExecutor,
                     PostExecutionThread postExecutionThread) {
        this.processor = new UseCaseProcessor<String, PhotoCommentsEntity>(threadExecutor, postExecutionThread) {
            @Override
            public Observable<PhotoCommentsEntity> getUseCaseActionObservable(String photoId) {
                return photosDataSource.loadPhotoComments(photoId);
            }
        };
    }

    @Override
    public Observable<SubmitUiModel<PhotoCommentsEntity>> process(String photoId) {
        return processor.process(photoId);
    }
}
