package com.github.chojmi.inspirations.domain.usecase.people;

import com.github.chojmi.inspirations.domain.common.SubmitUiModel;
import com.github.chojmi.inspirations.domain.common.UseCase;
import com.github.chojmi.inspirations.domain.common.UseCaseProcessor;
import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetUserInfo implements UseCase<String, PersonEntity> {

    private final UseCaseProcessor<String, PersonEntity> processor;

    @Inject
    GetUserInfo(PeopleDataSource peopleDataSource, ThreadExecutor threadExecutor,
                PostExecutionThread postExecutionThread) {
        this.processor = new UseCaseProcessor<String, PersonEntity>(threadExecutor, postExecutionThread) {
            @Override
            public Observable<PersonEntity> getUseCaseActionObservable(String userId) {
                return peopleDataSource.loadPersonInfo(userId);
            }
        };
    }

    @Override
    public Observable<SubmitUiModel<PersonEntity>> process(String userId) {
        return processor.process(userId);
    }
}
