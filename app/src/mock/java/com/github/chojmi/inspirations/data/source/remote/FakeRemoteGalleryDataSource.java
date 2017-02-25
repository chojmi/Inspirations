package com.github.chojmi.inspirations.data.source.remote;

import android.support.annotation.VisibleForTesting;

import com.github.chojmi.inspirations.data.model.gallery.Gallery;
import com.github.chojmi.inspirations.data.source.GalleryDataSource;

import io.reactivex.Observable;

public class FakeRemoteGalleryDataSource implements GalleryDataSource {

    private Gallery fakeGalleryResponse = Gallery.createEmpty();

    @Override
    public Observable<Gallery> loadGallery(String galleryId) {
        return loadGallery(galleryId, 1);
    }

    @Override
    public Observable<Gallery> loadGallery(String galleryId, int page) {
        return Observable.just(fakeGalleryResponse);
    }

    @VisibleForTesting
    public void setFakeGalleryResponse(Gallery fakeGalleryResponse) {
        this.fakeGalleryResponse = fakeGalleryResponse;
    }
}
