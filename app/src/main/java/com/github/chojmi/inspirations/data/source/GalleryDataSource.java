package com.github.chojmi.inspirations.data.source;

import com.github.chojmi.inspirations.data.model.gallery.GalleryResponse;

import io.reactivex.Observable;

public interface GalleryDataSource {

    Observable<GalleryResponse> loadGallery(String galleryId);

    Observable<GalleryResponse> loadGallery(String galleryId, int page);

}
