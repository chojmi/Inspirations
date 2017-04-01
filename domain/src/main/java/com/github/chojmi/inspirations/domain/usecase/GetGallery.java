package com.github.chojmi.inspirations.domain.usecase;

import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.model.Photo;
import com.github.chojmi.inspirations.domain.repository.GalleryDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

import static dagger.internal.Preconditions.checkNotNull;

public class GetGallery extends UseCase<List<Photo>, GetGallery.Params> {

    private final GalleryDataSource galleryDataSource;

    @Inject
    public GetGallery(GalleryDataSource galleryDataSource, ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.galleryDataSource = galleryDataSource;
    }

    @Override
    Observable<List<Photo>> buildUseCaseObservable(Params params) {
        checkNotNull(params);
        return galleryDataSource.loadGallery(params.galleryId);
    }

    public static final class Params {

        private final String galleryId;

        private Params(String galleryId) {
            this.galleryId = galleryId;
        }

        public static Params forGallery(String galleryId) {
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
