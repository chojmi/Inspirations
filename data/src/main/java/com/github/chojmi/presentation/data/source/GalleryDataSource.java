package com.github.chojmi.presentation.data.source;

import com.github.chojmi.presentation.data.model.gallery.Gallery;

import io.reactivex.Observable;

public interface GalleryDataSource {

    Observable<Gallery> loadGallery(String galleryId);

    Observable<Gallery> loadGallery(String galleryId, int page);

}
