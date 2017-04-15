package com.github.chojmi.inspirations.domain.usecase.people;

import com.github.chojmi.inspirations.domain.entity.people.PersonEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;
import com.github.chojmi.inspirations.domain.usecase.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

import static dagger.internal.Preconditions.checkNotNull;

public class GetUserInfo extends UseCase<PersonEntity, GetUserInfo.Params> {

    private final PeopleDataSource peopleDataSource;

    @Inject
    GetUserInfo(PeopleDataSource peopleDataSource, ThreadExecutor threadExecutor,
                PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.peopleDataSource = checkNotNull(peopleDataSource);
    }

    @Override
    public Observable<PersonEntity> buildUseCaseObservable(Params params) {
        return peopleDataSource.loadPersonInfo(checkNotNull(params).userId);
    }

    public static final class Params {

        private final String userId;

        private Params(String userId) {
            this.userId = checkNotNull(userId);
        }

        public static GetUserInfo.Params forUser(String userId) {
            return new GetUserInfo.Params(userId);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GetUserInfo.Params params = (GetUserInfo.Params) o;

            return userId != null ? userId.equals(params.userId) : params.userId == null;
        }

        @Override
        public int hashCode() {
            return userId != null ? userId.hashCode() : 0;
        }
    }
}
