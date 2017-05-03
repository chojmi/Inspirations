package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.inspirations.data.entity.FakeGalleryEntityImpl;
import com.github.chojmi.inspirations.data.entity.GalleryEntityImpl;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;

public final class FakeGalleriesService implements GalleriesService {
    private final FakeGalleryEntityImpl fakeGalleryEntity;
    private final Map<String, String> loadGalleryInfoQuery;

    public FakeGalleriesService(Map<String, String> loadGalleryInfoQuery) {
        this.loadGalleryInfoQuery = loadGalleryInfoQuery;
        this.fakeGalleryEntity = new FakeGalleryEntityImpl();
    }

    @Override
    public Observable<GalleryEntityImpl> loadGallery(@QueryMap Map<String, String> options) {
        if (loadGalleryInfoQuery.equals(options)) {
            return Observable.just(fakeGalleryEntity);
        } else {
            return Observable.error(new Throwable("Wrong args"));
        }
    }

    public FakeGalleryEntityImpl getFakeGalleryEntity() {
        return fakeGalleryEntity;
    }
}
