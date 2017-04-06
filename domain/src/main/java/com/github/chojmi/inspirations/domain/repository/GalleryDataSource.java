package com.github.chojmi.inspirations.domain.repository;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;

import java.util.List;

import io.reactivex.Observable;

public interface GalleryDataSource {
    Observable<List<PhotoEntity>> loadGallery(String galleryId);

    Observable<List<PhotoEntity>> loadGallery(String galleryId, int page);
}
