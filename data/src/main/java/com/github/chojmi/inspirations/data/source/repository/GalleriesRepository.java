package com.github.chojmi.inspirations.data.source.repository;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

import static dagger.internal.Preconditions.checkNotNull;

public class GalleriesRepository implements GalleriesDataSource {

    private final GalleriesDataSource galleryRemoteDataSource;

    private final GalleriesDataSource galleryLocalDataSource;

    @Inject
    GalleriesRepository(@Remote @NonNull GalleriesDataSource galleriesRemoteDataSource,
                        @Local @NonNull GalleriesDataSource galleriesLocalDataSource) {
        this.galleryRemoteDataSource = checkNotNull(galleriesRemoteDataSource);
        this.galleryLocalDataSource = checkNotNull(galleriesLocalDataSource);
    }

    @Override
    public Flowable<List<PhotoEntity>> loadGallery(String galleryId) {
        return loadGallery(galleryId, 1);
    }

    @Override
    public Flowable<List<PhotoEntity>> loadGallery(String galleryId, int page) {
        return Flowable.concat(galleryLocalDataSource.loadGallery(galleryId, page),
                galleryRemoteDataSource.loadGallery(galleryId, page))
                .filter(photos -> photos.size() > 0).firstElement()
                .toFlowable();
    }
}
