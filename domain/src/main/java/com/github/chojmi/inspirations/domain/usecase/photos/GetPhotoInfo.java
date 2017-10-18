package com.github.chojmi.inspirations.domain.usecase.photos;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.common.UseCaseProcessor;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoInfoEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetPhotoInfo implements UseCase<String, PhotoInfoEntity> {
    private final UseCaseProcessor<String, PhotoInfoEntity> processor;

    @Inject
    GetPhotoInfo(PhotosDataSource photosDataSource, ThreadExecutor threadExecutor,
                 PostExecutionThread postExecutionThread) {
        this.processor = new UseCaseProcessor<String, PhotoInfoEntity>(threadExecutor, postExecutionThread) {
            @Override
            public Observable<PhotoInfoEntity> getUseCaseActionObservable(String photoId) {
                return photosDataSource.loadPhotoInfo(photoId);
            }
        };
    }

    @Override
    public Observable<SubmitUiModel<PhotoInfoEntity>> process(String photoId) {
        return processor.process(photoId);
    }
}

