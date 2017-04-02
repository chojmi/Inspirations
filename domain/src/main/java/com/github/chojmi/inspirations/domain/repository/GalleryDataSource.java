package com.github.chojmi.inspirations.domain.repository;

import com.github.chojmi.inspirations.domain.model.Photo;

import java.util.List;

import io.reactivex.Observable;

public interface GalleryDataSource {
    Observable<List<Photo>> loadGallery(String galleryId);

    Observable<List<Photo>> loadGallery(String galleryId, int page);
}
