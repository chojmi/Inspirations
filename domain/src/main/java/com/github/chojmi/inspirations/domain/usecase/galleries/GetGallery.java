package com.github.chojmi.inspirations.domain.usecase.galleries;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;
import com.github.chojmi.inspirations.domain.usecase.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

import static dagger.internal.Preconditions.checkNotNull;

public class GetGallery extends UseCase<List<PhotoEntity>, GetGallery.Params> {

    private final GalleriesDataSource galleriesDataSource;

    @Inject
    public GetGallery(@NonNull GalleriesDataSource galleriesDataSource, @NonNull ThreadExecutor threadExecutor,
                      @NonNull PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.galleriesDataSource = checkNotNull(galleriesDataSource);
    }

    @Override
    public Observable<List<PhotoEntity>> buildUseCaseObservable(@NonNull Params params) {
        return galleriesDataSource.loadGallery(checkNotNull(params).galleryId);
    }

    public static final class Params {

        private final String galleryId;

        private Params(@NonNull String galleryId) {
            this.galleryId = checkNotNull(galleryId);
        }

        public static Params forGallery(@NonNull String galleryId) {
            return new Params(galleryId);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Params params = (Params) o;

            return galleryId != null ? galleryId.equals(params.galleryId) : params.galleryId == null;
        }

        @Override
        public int hashCode() {
            return galleryId != null ? galleryId.hashCode() : 0;
        }
    }
}
