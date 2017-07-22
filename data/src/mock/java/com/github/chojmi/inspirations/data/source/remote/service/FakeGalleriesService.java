package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.inspirations.data.entity.GalleryEntityImpl;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;

public class FakeGalleriesService implements GalleriesService {
    @Override
    public Observable<GalleryEntityImpl> loadGallery(@QueryMap Map<String, String> options) {
        return Observable.empty();
    }
}
