package com.github.chojmi.inspirations.domain.usecase.galleries;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.common.UseCaseProcessor;
import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class GetGalleryByUserId implements UseCase<String, GalleryEntity> {
    private final UseCaseProcessor<String, GalleryEntity> processor;

    @Inject
    public GetGalleryByUserId(@NonNull GalleriesDataSource galleriesDataSource, @NonNull ThreadExecutor threadExecutor,
                              @NonNull PostExecutionThread postExecutionThread) {
        this.processor = new UseCaseProcessor<String, GalleryEntity>(threadExecutor, postExecutionThread) {
            @Override
            public Observable<GalleryEntity> getUseCaseActionObservable(String userId) {
                return galleriesDataSource.loadGalleryByUserId(userId);
            }
        };
    }

    @Override
    public Observable<SubmitUiModel<GalleryEntity>> process(String userId) {
        return processor.process(userId);
    }
}
