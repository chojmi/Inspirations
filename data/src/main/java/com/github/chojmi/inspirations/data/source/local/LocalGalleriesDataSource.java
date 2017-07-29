package com.github.chojmi.inspirations.data.source.local;

import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.repository.GalleriesDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LocalGalleriesDataSource implements GalleriesDataSource {

    @Inject
    public LocalGalleriesDataSource() {
    }

    @Override
    public Observable<List<PhotoEntity>> loadGalleryByGalleryId(String galleryId) {
        return loadGalleryByGalleryId(galleryId, 1);
    }

    @Override
    public Observable<List<PhotoEntity>> loadGalleryByGalleryId(String galleryId, int page) {
        return Observable.empty();
    }

    @Override
    public Observable<GalleryEntity> loadGalleryByUserId(String userId) {
        return loadGalleryByUserId(userId, 1);
    }

    @Override
    public Observable<GalleryEntity> loadGalleryByUserId(String userId, int page) {
        return Observable.empty();
    }
}
