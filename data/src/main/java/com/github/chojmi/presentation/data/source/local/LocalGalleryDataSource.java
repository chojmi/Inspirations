package com.github.chojmi.presentation.data.source.local;

import android.content.Context;

import com.github.chojmi.presentation.data.model.gallery.Gallery;
import com.github.chojmi.presentation.data.source.GalleryDataSource;

import io.reactivex.Observable;

public class LocalGalleryDataSource implements GalleryDataSource {

    private Context context;

    public LocalGalleryDataSource(Context context) {
        this.context = context;
    }

    @Override
    public Observable<Gallery> loadGallery(String galleryId) {
        return loadGallery(galleryId, 1);
    }

    @Override
    public Observable<Gallery> loadGallery(String galleryId, int page) {
        return Observable.just(Gallery.createEmpty());
    }
}
