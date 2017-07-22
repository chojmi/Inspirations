package com.github.chojmi.inspirations.domain.usecase.people;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.common.UseCaseProcessor;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class GetUserPublicPhotos implements UseCase<String, List<PhotoEntity>> {

    private final UseCaseProcessor<String, List<PhotoEntity>> processor;

    @Inject
    GetUserPublicPhotos(@NonNull PeopleDataSource peopleDataSource, @NonNull ThreadExecutor threadExecutor,
                        @NonNull PostExecutionThread postExecutionThread) {
        this.processor = new UseCaseProcessor<String, List<PhotoEntity>>(threadExecutor, postExecutionThread) {
            @Override
            public Observable<List<PhotoEntity>> getUseCaseActionObservable(String userId) {
                return peopleDataSource.loadUserPublicPhotos(userId);
            }
        };
    }

    @Override
    public Observable<SubmitUiModel<List<PhotoEntity>>> process(String userId) {
        return processor.process(userId);
    }
}