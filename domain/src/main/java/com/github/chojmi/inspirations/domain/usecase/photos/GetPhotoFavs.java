package com.github.chojmi.inspirations.domain.usecase.photos;

import com.github.chojmi.inspirations.domain.entity.photos.PhotoFavsEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.model.SubmitUiModel;
import com.github.chojmi.inspirations.domain.model.UseCase;
import com.github.chojmi.inspirations.domain.model.UseCaseProcessor;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class GetPhotoFavs implements UseCase<String, PhotoFavsEntity> {
    private final UseCaseProcessor<String, PhotoFavsEntity> processor;

    @Inject
    GetPhotoFavs(PhotosDataSource photosDataSource, ThreadExecutor threadExecutor,
                 PostExecutionThread postExecutionThread) {
        this.processor = new UseCaseProcessor<String, PhotoFavsEntity>(threadExecutor, postExecutionThread) {
            @Override
            public Flowable<PhotoFavsEntity> getUseCaseActionFlowable(String photoId) {
                return photosDataSource.loadPhotoFavs(photoId);
            }
        };
    }

    @Override
    public Flowable<SubmitUiModel<PhotoFavsEntity>> process(String photoId) {
        return processor.process(photoId);
    }
}
