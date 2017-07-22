package com.github.chojmi.inspirations.domain.usecase.photos;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.common.UseCaseProcessor;
import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetPhotoFavs implements UseCase<String, PhotoFavsEntity> {
    private final UseCaseProcessor<String, PhotoFavsEntity> processor;

    @Inject
    GetPhotoFavs(PhotosDataSource photosDataSource, ThreadExecutor threadExecutor,
                 PostExecutionThread postExecutionThread) {
        this.processor = new UseCaseProcessor<String, PhotoFavsEntity>(threadExecutor, postExecutionThread) {
            @Override
            public Observable<PhotoFavsEntity> getUseCaseActionObservable(String photoId) {
                return photosDataSource.loadPhotoFavs(photoId);
            }
        };
    }

    @Override
    public Observable<SubmitUiModel<PhotoFavsEntity>> process(String photoId) {
        return processor.process(photoId);
    }
}
