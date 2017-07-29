package com.github.chojmi.inspirations.data.source.repository;

import android.support.annotation.NonNull;

import com.github.chojmi.inspirations.data.source.Local;
import com.github.chojmi.inspirations.data.source.Remote;
import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.github.chojmi.inspirations.domain.utils.Preconditions.checkNotNull;

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
    public Observable<List<PhotoEntity>> loadGalleryByGalleryId(String galleryId) {
        return loadGalleryByGalleryId(galleryId, 1);
    }

    @Override
    public Observable<List<PhotoEntity>> loadGalleryByGalleryId(String galleryId, int page) {
        return Observable.concat(galleryLocalDataSource.loadGalleryByGalleryId(galleryId, page),
                galleryRemoteDataSource.loadGalleryByGalleryId(galleryId, page))
                .filter(photos -> photos != null).firstElement()
                .toObservable();
    }

    @Override
    public Observable<GalleryEntity> loadGalleryByUserId(String userId) {
        return loadGalleryByUserId(userId, 1);
    }

    @Override
    public Observable<GalleryEntity> loadGalleryByUserId(String userId, int page) {
        return Observable.concat(galleryLocalDataSource.loadGalleryByUserId(userId, page),
                galleryRemoteDataSource.loadGalleryByUserId(userId, page))
                .filter(gallery -> gallery != null).firstElement()
                .toObservable();
    }
}
