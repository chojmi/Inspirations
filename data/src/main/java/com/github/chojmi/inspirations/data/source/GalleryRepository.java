package com.github.chojmi.inspirations.data.source;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.domain.entity.gallery.PhotoEntity;
import com.github.chojmi.inspirations.domain.repository.GalleryDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

import static dagger.internal.Preconditions.checkNotNull;

public final class GalleryRepository implements GalleryDataSource {

    private final GalleryDataSource galleryRemoteDataSource;

    private final GalleryDataSource galleryLocalDataSource;

    @Inject
    GalleryRepository(@Remote @NonNull GalleryDataSource galleryRemoteDataSource,
                      @Local @NonNull GalleryDataSource galleryLocalDataSource) {
        this.galleryRemoteDataSource = checkNotNull(galleryRemoteDataSource);
        this.galleryLocalDataSource = checkNotNull(galleryLocalDataSource);
    }

    @Override
    public Observable<List<PhotoEntity>> loadGallery(String galleryId) {
        return loadGallery(galleryId, 1);
    }

    @Override
    public Observable<List<PhotoEntity>> loadGallery(String galleryId, int page) {
        return Observable.concat(galleryLocalDataSource.loadGallery(galleryId, page),
                galleryRemoteDataSource.loadGallery(galleryId, page)).filter(photos -> photos.size() > 0).firstElement().toObservable();
    }
}
