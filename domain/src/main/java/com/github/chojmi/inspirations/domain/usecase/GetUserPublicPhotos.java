package com.github.chojmi.inspirations.domain.usecase;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.GalleryDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

import static dagger.internal.Preconditions.checkNotNull;

public class GetUserPublicPhotos extends UseCase<List<PhotoEntity>, GetUserPublicPhotos.Params> {

    private final GalleryDataSource galleryDataSource;

    @Inject
    GetUserPublicPhotos(GalleryDataSource galleryDataSource, ThreadExecutor threadExecutor,
                        PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.galleryDataSource = galleryDataSource;
    }

    @Override
    Observable<List<PhotoEntity>> buildUseCaseObservable(Params params) {
        return galleryDataSource.loadUserPublicPhotos(checkNotNull(params).userId);
    }

    public static final class Params {

        private final String userId;

        private Params(String userId) {
            this.userId = userId;
        }

        public static Params forUserId(String userId) {
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