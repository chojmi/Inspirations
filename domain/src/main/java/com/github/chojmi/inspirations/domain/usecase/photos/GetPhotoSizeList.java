package com.github.chojmi.inspirations.domain.usecase.photos;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.common.UseCaseProcessor;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoSizeListEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetPhotoSizeList implements UseCase<String, PhotoSizeListEntity> {
    private final UseCaseProcessor<String, PhotoSizeListEntity> processor;

    @Inject
    GetPhotoSizeList(PhotosDataSource photosDataSource, ThreadExecutor threadExecutor,
                     PostExecutionThread postExecutionThread) {
        this.processor = new UseCaseProcessor<String, PhotoSizeListEntity>(threadExecutor, postExecutionThread) {
            @Override
            public Observable<PhotoSizeListEntity> getUseCaseActionObservable(String photoId) {
                return photosDataSource.loadPhotoSizes(photoId);
            }
        };
    }

    @Override
    public Observable<SubmitUiModel<PhotoSizeListEntity>> process(String photoId) {
        return processor.process(photoId);
    }
}
