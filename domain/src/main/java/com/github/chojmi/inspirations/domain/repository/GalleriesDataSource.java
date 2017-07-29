package com.github.chojmi.inspirations.domain.repository;

import com.github.chojmi.inspirations.domain.entity.GalleryEntity;
import com.github.chojmi.inspirations.domain.entity.PhotoEntity;

import java.util.List;

import io.reactivex.Observable;

public interface GalleriesDataSource {
    Observable<List<PhotoEntity>> loadGalleryByGalleryId(String galleryId);

    Observable<List<PhotoEntity>> loadGalleryByGalleryId(String galleryId, int page);

    Observable<GalleryEntity> loadGalleryByUserId(String userId);

    Observable<GalleryEntity> loadGalleryByUserId(String userId, int page);
}
