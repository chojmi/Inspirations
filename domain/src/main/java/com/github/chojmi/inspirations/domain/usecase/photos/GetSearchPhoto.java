package com.github.chojmi.inspirations.domain.usecase.photos;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.common.UseCaseProcessor;
import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.PhotosDataSource;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetSearchPhoto implements UseCase<String, GalleryEntity> {
    private final UseCaseProcessor<String, GalleryEntity> processor;

    @Inject
    GetSearchPhoto(PhotosDataSource photosDataSource, ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        this.processor = new UseCaseProcessor<String, GalleryEntity>(threadExecutor, postExecutionThread) {
            @Override
            public Observable<GalleryEntity> getUseCaseActionObservable(String text) {
                return photosDataSource.loadSearchPhoto(text);
            }
        };
    }

    @Override
    public Observable<SubmitUiModel<GalleryEntity>> process(String text) {
        return processor.process(text);
    }
}