package com.github.chojmi.inspirations.data.source.remote;

import com.github.chojmi.inspirations.data.model.gallery.GalleryResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface GalleryService {

    @GET("services/rest")
    Observable<GalleryResponse> loadGallery(@QueryMap Map<String, String> options);
}
