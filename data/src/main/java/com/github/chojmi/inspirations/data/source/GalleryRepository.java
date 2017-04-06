package com.github.chojmi.inspirations.data.source;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.repository.GalleryDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GalleryRepository implements GalleryDataSource {

    private final GalleryDataSource galleryRemoteDataSource;

    private final GalleryDataSource galleryLocalDataSource;

    @Inject
    GalleryRepository(@Remote GalleryDataSource galleryRemoteDataSource,
                      @Local GalleryDataSource galleryLocalDataSource) {
        this.galleryRemoteDataSource = galleryRemoteDataSource;
        this.galleryLocalDataSource = galleryLocalDataSource;
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
