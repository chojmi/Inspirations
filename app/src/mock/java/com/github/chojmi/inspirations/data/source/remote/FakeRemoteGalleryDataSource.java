package com.github.chojmi.inspirations.data.source.remote;

import android.support.annotation.VisibleForTesting;

import com.github.chojmi.inspirations.data.model.gallery.GalleryResponse;
import com.github.chojmi.inspirations.data.source.GalleryDataSource;

import io.reactivex.Observable;

public class FakeRemoteGalleryDataSource implements GalleryDataSource {

    private GalleryResponse fakeGalleryResponse = GalleryResponse.createEmpty();

    @Override
    public Observable<GalleryResponse> loadGallery(String galleryId) {
        return loadGallery(galleryId, 1);
    }

    @Override
    public Observable<GalleryResponse> loadGallery(String galleryId, int page) {
        return Observable.just(fakeGalleryResponse);
    }

    @VisibleForTesting
    public void setFakeGalleryResponse(GalleryResponse fakeGalleryResponse) {
        this.fakeGalleryResponse = fakeGalleryResponse;
    }
}
