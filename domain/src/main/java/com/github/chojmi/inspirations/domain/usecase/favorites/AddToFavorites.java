package com.github.chojmi.inspirations.domain.usecase.favorites;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.common.UseCaseProcessor;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.FavoritesDataSource;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AddToFavorites implements UseCase<String, Void> {
    private final UseCaseProcessor<String, Void> processor;

    @Inject
    AddToFavorites(FavoritesDataSource favoritesDataSource, ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        this.processor = new UseCaseProcessor<String, Void>(threadExecutor, postExecutionThread) {
            @Override
            public Observable<Void> getUseCaseActionObservable(String photoId) {
                return favoritesDataSource.add(photoId);
            }
        };
    }

    @Override
    public Observable<SubmitUiModel<Void>> process(String photoId) {
        return processor.process(photoId);
    }
}

