package com.github.chojmi.inspirations.domain.usecase.galleries;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.common.UseCaseProcessor;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

public class GetGallery implements UseCase<String, List<PhotoEntity>> {

    private final UseCaseProcessor<String, List<PhotoEntity>> processor;

    @Inject
    public GetGallery(@NonNull GalleriesDataSource galleriesDataSource, @NonNull ThreadExecutor threadExecutor,
                      @NonNull PostExecutionThread postExecutionThread) {
        this.processor = new UseCaseProcessor<String, List<PhotoEntity>>(threadExecutor, postExecutionThread) {
            @Override
            public Flowable<List<PhotoEntity>> getUseCaseActionFlowable(String galleryId) {
                return galleriesDataSource.loadGallery(galleryId);
            }
        };
    }

    @Override
    public Flowable<SubmitUiModel<List<PhotoEntity>>> process(String galleryId) {
        return processor.process(galleryId);
    }
}