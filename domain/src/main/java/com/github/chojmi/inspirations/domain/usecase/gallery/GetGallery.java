package com.github.chojmi.inspirations.domain.usecase.gallery;

import com.github.chojmi.inspirations.domain.entity.gallery.PhotoEntity;
import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.repository.GalleryDataSource;
import com.github.chojmi.inspirations.domain.usecase.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

import static dagger.internal.Preconditions.checkNotNull;

public final class GetGallery extends UseCase<List<PhotoEntity>, GetGallery.Params> {

    private final GalleryDataSource galleryDataSource;

    @Inject
    public GetGallery(@NonNull GalleryDataSource galleryDataSource, @NonNull ThreadExecutor threadExecutor,
                      @NonNull PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.galleryDataSource = checkNotNull(galleryDataSource);
    }

    @Override
    public Observable<List<PhotoEntity>> buildUseCaseObservable(@NonNull Params params) {
        return galleryDataSource.loadGallery(checkNotNull(params).galleryId);
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
