package com.github.chojmi.inspirations.data.source.remote;

import android.support.annotation.VisibleForTesting;

import com.github.chojmi.inspirations.domain.entity.PhotoEntity;
import com.github.chojmi.inspirations.domain.repository.GalleryDataSource;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

public class FakeRemoteGalleryDataSource implements GalleryDataSource {

    private List<PhotoEntity> fakePhotoLists = Collections.emptyList();

    @Override
    public Observable<List<PhotoEntity>> loadGallery(String galleryId) {
        return loadGallery(galleryId, 1);
    }

    @Override
    public Observable<List<PhotoEntity>> loadGallery(String galleryId, int page) {
        return Observable.just(fakePhotoLists);
    }

    @VisibleForTesting
    public void setFakePhotoLists(List<PhotoEntity> fakePhotoLists) {
        this.fakePhotoLists = fakePhotoLists;
    }
}
