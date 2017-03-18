package com.github.chojmi.inspirations.domain.usecase;

import com.github.chojmi.inspirations.domain.executor.PostExecutionThread;
import com.github.chojmi.inspirations.domain.executor.ThreadExecutor;
import com.github.chojmi.inspirations.domain.model.Photo;
import com.github.chojmi.inspirations.domain.repository.GalleryDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

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
    }
}
