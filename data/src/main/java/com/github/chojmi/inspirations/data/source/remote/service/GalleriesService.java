package com.github.chojmi.inspirations.data.source.remote.service;

import com.github.chojmi.inspirations.data.entity.GalleryEntityImpl;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface GalleriesService {

    @GET("services/rest")
    Observable<GalleryEntityImpl> loadGallery(@QueryMap Map<String, String> options);
}
