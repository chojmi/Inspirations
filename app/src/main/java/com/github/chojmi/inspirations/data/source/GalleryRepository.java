package com.github.chojmi.inspirations.data.source;

import com.github.chojmi.inspirations.data.model.gallery.GalleryResponse;

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
    public Observable<GalleryResponse> loadGallery(String galleryId) {
        return loadGallery(galleryId, 1);
    }

    @Override
    public Observable<GalleryResponse> loadGallery(String galleryId, int page) {
//        return Observable.concat(galleryLocalDataSource.loadGallery(galleryId, page),
//                galleryRemoteDataSource.loadGallery(galleryId, page)).filter(gallery -> gallery.getPhotos().getTotal() > 0).firstElement().toObservable();
        return galleryRemoteDataSource.loadGallery(galleryId, page);
    }
}
