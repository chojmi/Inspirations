package com.github.chojmi.inspirations.data.source.local;

import android.content.Context;

import com.github.chojmi.inspirations.data.model.gallery.GalleryResponse;
import com.github.chojmi.inspirations.data.source.GalleryDataSource;

import io.reactivex.Observable;

public class LocalGalleryDataSource implements GalleryDataSource {

    private Context context;

    public LocalGalleryDataSource(Context context) {
        this.context = context;
    }

    @Override
    public Observable<GalleryResponse> loadGallery(String galleryId) {
        return loadGallery(galleryId, 1);
    }

    @Override
    public Observable<GalleryResponse> loadGallery(String galleryId, int page) {
        return Observable.just(GalleryResponse.createEmpty());
    }
}
