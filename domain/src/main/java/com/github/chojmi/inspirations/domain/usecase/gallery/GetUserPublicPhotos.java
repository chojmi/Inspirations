package com.github.chojmi.inspirations.domain.usecase.gallery;

import com.github.chojmi.inspirations.domain.entity.gallery.PhotoEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.PeopleDataSource;
import com.github.chojmi.inspirations.domain.usecase.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

import static dagger.internal.Preconditions.checkNotNull;

public class GetUserPublicPhotos extends UseCase<List<PhotoEntity>, GetUserPublicPhotos.Params> {

    private final PeopleDataSource peopleDataSource;

    @Inject
    GetUserPublicPhotos(@NonNull PeopleDataSource peopleDataSource, @NonNull ThreadExecutor threadExecutor,
                        @NonNull PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.peopleDataSource = checkNotNull(peopleDataSource);
    }

    @Override
    public Observable<List<PhotoEntity>> buildUseCaseObservable(Params params) {
        return peopleDataSource.loadUserPublicPhotos(checkNotNull(params).userId);
    }

    public static final class Params {

        private final String userId;

        private Params(@NonNull String userId) {
            this.userId = checkNotNull(userId);
        }

        public static Params forUserId(@NonNull String userId) {
            return new Params(userId);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Params params = (Params) o;

            return userId != null ? userId.equals(params.userId) : params.userId == null;
        }

        @Override
        public int hashCode() {
            return userId != null ? userId.hashCode() : 0;
        }
    }
}