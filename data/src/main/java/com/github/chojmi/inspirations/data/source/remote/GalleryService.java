package com.github.chojmi.inspirations.data.source.remote;

import com.github.chojmi.inspirations.data.entity.gallery.GalleryEntityImpl;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

interface GalleryService {

    @GET("services/rest")
    Observable<GalleryEntityImpl> loadGallery(@QueryMap Map<String, String> options);

    @GET("services/rest")
    Observable<GalleryEntityImpl> loadUserPublicPhotos(@QueryMap Map<String, String> options);
}
