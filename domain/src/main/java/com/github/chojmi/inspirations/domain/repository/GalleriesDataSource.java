package com.github.chojmi.inspirations.domain.repository;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;

import java.util.List;

import io.reactivex.Flowable;

public interface GalleriesDataSource {
    Flowable<List<PhotoEntity>> loadGallery(String galleryId);

    Flowable<List<PhotoEntity>> loadGallery(String galleryId, int page);
}
